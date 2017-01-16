package com.reelbook.ws.endpoint;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
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

import com.reelbook.core.util.B2MathUtil;
import com.reelbook.core.util.MathUtil;
import com.reelbook.model.Video;
import com.reelbook.service.manager.local.PGLargeObjectManagerLocal;
import com.reelbook.service.manager.local.VideoManagerLocal;

@Stateful
@ServerEndpoint(value = "/stream", encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class })
public class StreamEndpoint
{
	private static final int _BUFFER = 2000;
	@EJB
	private VideoManagerLocal videoML;
	@EJB
	private PGLargeObjectManagerLocal pgLargeObjectML;
	
	private static final Logger log = Logger.getLogger("StreamEndpoint");

	private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void onOpen(Session session) {
		Video video = videoML.get(1l);
		Long count = pgLargeObjectML.getCount(video.getoID());
		Integer totalSegments = BigDecimal.valueOf(count).divide(BigDecimal.valueOf(_BUFFER), 0, RoundingMode.UP).intValue();
		try {
			session.getBasicRemote().sendObject(getMessage("totalSegments", totalSegments.toString()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		sessions.add(session);
//
//		for (Session s : sessions) {
//			try {
//				s.getBasicRemote().sendObject(getMessage("data", "User has connected"));
//			} catch (IOException | EncodeException ex) {	
//				ex.printStackTrace();
//			}
//		}
		log.info(session.getId() + " has connected");
	}

	@OnMessage
	public void onMessage(Message message, Session session) {
		log.info(session.getId() + " new message");
		log.info(message.toString());
//		for (Session s : sessions) {
//			try {
//				s.getBasicRemote().sendObject(message);
//			} catch (IOException | EncodeException ex) {
//				ex.printStackTrace();
//			}
//		}
		
		Video video = videoML.get(1l);
		List<Object[]> pgLargeObjecList = pgLargeObjectML.getList(video.getoID());
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		int num = 0;
		for (Object[] pgLargeObject : pgLargeObjecList)
		{
//			BigInteger object = (BigInteger)pgLargeObject[0];
//			Integer object1 = (Integer)pgLargeObject[1];
			byte[] object2 = (byte[])pgLargeObject[2];
			outStream.write(object2, 0, object2.length);
			num = num + 1;
			if (num >= _BUFFER)
			{
				break;
			}
		}
		ByteBuffer byteData = ByteBuffer.wrap(outStream.toByteArray());
		try {
			session.getBasicRemote().sendBinary(byteData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@OnClose
	public void onClose(Session session) {
//		sessions.remove(session);
//
//		for (Session s : sessions) {
//			try {
//				s.getBasicRemote().sendObject(getMessage("data", "User has disconnected"));
//			} catch (IOException | EncodeException ex) {
//				ex.printStackTrace();
//			}
//		}
		log.info("User disconnected");
	}

	@OnError
	public void onError(Session session, Throwable t) {
		log.info(t.getMessage());
	}

	private static Message getMessage(String key, String value) {
		return new Message(Json.createObjectBuilder().add("key", key).add("value", value).build());
	}
	
	public void onChunk(ByteBuffer buffer) {
		for (Session session : sessions) {
			try {
				session.getBasicRemote().sendBinary(buffer);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}