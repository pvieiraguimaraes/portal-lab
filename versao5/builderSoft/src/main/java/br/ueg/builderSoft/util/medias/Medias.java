package br.ueg.builderSoft.util.medias;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Medias {

	public static Boolean writeInputStreamToDisk(String absoluteFilePath, InputStream media) throws FileNotFoundException, IOException {
		File file = new File(absoluteFilePath.substring(0, absoluteFilePath.lastIndexOf(File.separator)));
		
		if (!file.exists()) {
			boolean ok = file.mkdirs();
			if (!ok)
				return false;
		}
	
		OutputStream outputStream = new FileOutputStream(absoluteFilePath);
	
		// OutputStream outputStream = new FileOutputStream("C:\\log\\" +
		// media.getName());
	
		java.io.InputStream inputStream = media;
		byte[] buffer = new byte[1024];
		for (int count; (count = inputStream.read(buffer)) != -1;) {
			outputStream.write(buffer, 0, count);
		}
		outputStream.flush();
		outputStream.close();
		inputStream.close();
		return true;
	}
	
}
