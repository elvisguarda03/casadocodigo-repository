package br.com.casadocodigo.loja.infra;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;

import javax.servlet.http.Part;

public class FileSaver {
	//Uppercase - CTRL + SHIFT + X
	public final static String SERVER_PATH = "C:\\casadocodigo\\";

	public String write(Part file, String path) {
		String relativePath = path + "/" + file.getSubmittedFileName();
		try {
			file.write(SERVER_PATH + relativePath);
			
			return relativePath;
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
	
	public static void transfer(Path source, OutputStream outputStream) {
		try {
			FileInputStream inputStream = new FileInputStream(source.toFile());
			
			try (ReadableByteChannel inputChannel = Channels.newChannel(inputStream); 
					WritableByteChannel outputChannel = Channels.newChannel(outputStream)) {
				
				ByteBuffer buffer = ByteBuffer.allocate(1024 * 10);
				
				while (inputChannel.read(buffer) != -1) {
					buffer.flip();
					outputChannel.write(buffer);
					buffer.clear();
				}
			} catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException(fnfe);
		}
	}
}