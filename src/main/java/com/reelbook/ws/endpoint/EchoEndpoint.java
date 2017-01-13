package com.reelbook.ws.endpoint;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.Basic;
import javax.persistence.Id;
import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;
import com.reelbook.model.PGLargeObject;
import com.reelbook.model.Video;
import com.reelbook.service.manager.local.PGLargeObjectManagerLocal;
import com.reelbook.service.manager.local.VideoManagerLocal;

@Stateful
@ServerEndpoint("/echo")
public class EchoEndpoint
{
	@EJB
	private VideoManagerLocal videoML;
	@EJB
	private PGLargeObjectManagerLocal pgLargeObjectML;
	
	@OnMessage
	public ByteBuffer echo(String message) throws IOException
	{
		// return message;
		InputStream is = null;
		ByteBuffer returnVideo = null;
		try
		{
//			is = new FileInputStream("/home/tallion.com.ar/nolgiati/Desktop/9ba2dc39-0aa4-40c5-bfbb-a84af7825364.mp4");
			returnVideo = readToByteBuffer(is);
//			is.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnVideo;
	}

	private ByteBuffer readToByteBuffer(InputStream inStream) throws IOException
	{
		Video video = videoML.get(1l);
		List<Object[]> pgLargeObjecList = pgLargeObjectML.getList(video.getoID());
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		int num = 0;
		for (Object[] pgLargeObject : pgLargeObjecList)
		{
			BigInteger object = (BigInteger)pgLargeObject[0];
			Integer object1 = (Integer)pgLargeObject[1];
			byte[] object2 = (byte[])pgLargeObject[2];
			outStream.write(object2, 0, object2.length);
			num = num + 1;
			if (num >= 2000)
			{
				break;
			}
		}
		ByteBuffer byteData = ByteBuffer.wrap(outStream.toByteArray());
		return byteData;
	}
	
//	static ByteBuffer readToByteBuffer(InputStream inStream) throws IOException
//	{
//		int bufferSize = 0x80000;// ~500K
//		byte[] buffer = new byte[bufferSize];
//		ByteArrayOutputStream outStream = new ByteArrayOutputStream(bufferSize);
//		int read;
//		while (true)
//		{
//			read = inStream.read(buffer);
//			if (read == -1)
//				break;
//			outStream.write(buffer, 0, read);
//		}
//		ByteBuffer byteData = ByteBuffer.wrap(outStream.toByteArray());
//		return byteData;
//		// ByteBuffer myVideo= new ByteBuffer();
//	}
}