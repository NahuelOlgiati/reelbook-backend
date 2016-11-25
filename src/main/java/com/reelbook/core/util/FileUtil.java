package com.reelbook.core.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.commons.codec.binary.Base64;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

public final class FileUtil
{
	public static final Map<String, String> getBase64Map(MultipartFormDataInput input, String uploadName, Integer bufferSize) throws IOException
	{
		Map<String, String> bytes = new HashMap<String, String>();
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get(uploadName);

		for (InputPart inputPart : inputParts)
		{
			MultivaluedMap<String, String> header = inputPart.getHeaders();
			String fileName = getFileName(header);
			InputStream inputStream = inputPart.getBody(InputStream.class, null);
			byte[] stream = readStream(inputStream, bufferSize);
			bytes.put(fileName, Base64.encodeBase64String(stream));
		}
		return bytes;
	}

	public static final void upload(MultipartFormDataInput input, String uploadName, String uploadFilePath, Integer bufferSize) throws IOException
	{

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get(uploadName);

		for (InputPart inputPart : inputParts)
		{
			MultivaluedMap<String, String> header = inputPart.getHeaders();
			String fileName = getFileName(header);
			InputStream inputStream = inputPart.getBody(InputStream.class, null);
			byte[] bytes = readStream(inputStream, bufferSize);
			String fullFileName = uploadFilePath + fileName;
			writeFile(bytes, fullFileName);
		}
	}

	public static final String getFileName(MultivaluedMap<String, String> header)
	{

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition)
		{
			if ((filename.trim().startsWith("filename")))
			{

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	public static final void writeFile(byte[] content, String filename) throws IOException
	{
		File file = new File(filename);

		if (!file.exists())
		{
			file.createNewFile();
		}

		FileOutputStream fop = new FileOutputStream(file);

		fop.write(content);
		fop.flush();
		fop.close();
	}

	public static final byte[] readStream(InputStream input, Integer bufferSize) throws IOException
	{
		byte[] buffer = new byte[bufferSize];
		int bytesRead;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		while ((bytesRead = input.read(buffer)) != -1)
		{
			output.write(buffer, 0, bytesRead);
		}
		return output.toByteArray();
	}
}