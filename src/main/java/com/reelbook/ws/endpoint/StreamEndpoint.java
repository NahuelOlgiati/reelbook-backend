package com.reelbook.ws.endpoint;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import com.reelbook.model.Video;
import com.reelbook.service.manager.local.PGLargeObjectManagerLocal;
import com.reelbook.service.manager.local.VideoManagerLocal;

@Stateful
@ServerEndpoint(value = "/stream", encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
public class StreamEndpoint
{
	@SuppressWarnings("unused")
	private static final int ONE_ROW = 2048;

//	private static final int NUMBER_OF_ROWS = 1024;
	 private static final int NUMBER_OF_ROWS = 2048;

	@EJB
	private VideoManagerLocal videoML;
	@EJB
	private PGLargeObjectManagerLocal pgLargeObjectML;

	private static final Logger log = Logger.getLogger("StreamEndpoint");

	private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void onOpen(Session session)
	{
		try
		{
			Video video = videoML.get(1l);
			Long count = pgLargeObjectML.getCount(video.getoID());
			Integer totalSegments = BigDecimal.valueOf(count).divide(BigDecimal.valueOf(NUMBER_OF_ROWS), 0, RoundingMode.UP).intValue();
			session.getBasicRemote().sendObject(getMessage("totalSegments", totalSegments.toString()));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (EncodeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info(session.getId() + " has connected");
	}

	@OnMessage
	public void onMessage(Message message, Session session)
	{
		log.info(session.getId() + " new message");
		log.info(message.toString());

		try
		{
			String keyWQ = message.getJson().get("key").toString();
			String key = keyWQ.substring(1, keyWQ.length() - 1);
			String value = message.getJson().get("value").toString();
			if ("page".equals(key))
			{
				Integer page = Integer.valueOf(value.toString());
				Video video = videoML.get(1l);
				List<Object> pgLargeObjecList = pgLargeObjectML.getList(video.getoID(), NUMBER_OF_ROWS, page);
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				for (Object pgLargeObject : pgLargeObjecList)
				{
					byte[] data = (byte[]) pgLargeObject;
					outStream.write(data, 0, data.length);
				}
				ByteBuffer byteData = ByteBuffer.wrap(outStream.toByteArray());
				session.getBasicRemote().sendBinary(byteData);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@OnClose
	public void onClose(Session session)
	{
		// sessions.remove(session);
		//
		// for (Session s : sessions) {
		// try {
		// s.getBasicRemote().sendObject(getMessage("data", "User has disconnected"));
		// } catch (IOException | EncodeException ex) {
		// ex.printStackTrace();
		// }
		// }
		log.info("User disconnected");
	}

	@OnError
	public void onError(Session session, Throwable t)
	{
		log.info(t.getMessage());
	}

	private static Message getMessage(String key, String value)
	{
		return new Message(Json.createObjectBuilder().add("key", key).add("value", value).build());
	}

	public void onChunk(ByteBuffer buffer)
	{
		for (Session session : sessions)
		{
			try
			{
				session.getBasicRemote().sendBinary(buffer);
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}
}