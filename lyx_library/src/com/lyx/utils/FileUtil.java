package com.lyx.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
	public static void WriteFile(InputStream stream, String fileName) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(fileName);
		byte[] buff = new byte[1024];
		int readLen;
		do {
			readLen = stream.read(buff);
			if (readLen > 0)
				fos.write(buff, 0, readLen);
		} while (readLen > 0);
		
		fos.close();
	}
}
