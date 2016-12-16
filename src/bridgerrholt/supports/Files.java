package bridgerrholt.supports;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.Paths;

public class Files {
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = java.nio.file.Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static String readFile(String path) throws IOException {
		return readFile(path, Charset.defaultCharset());
	}
}
