package fp.dam.psp.networking.ejemplos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

public class EjermploHttpURLConnection {

	static int n = 1;
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		Pattern urlPattern = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");
		URL url = new URI("https://www.educastur.es").toURL();
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		try (Scanner s = new Scanner(con.getInputStream())) {
			while (s.findWithinHorizon("<img .*?src=\"(.*?)\"", 0) != null) {
				String path = s.match().group(1);
				download(new URI(urlPattern.matcher(path).matches() ? path : url.toString() + path).toURL());
			}
		}
	}
	
	static void download(URL url) throws IOException {
		String path = System.getProperty("user.home") + "/Imágenes/educastur/" + FilenameUtils.getName(url.getPath());
		File file = new File(path);
		if (file.exists())
			file = new File(FilenameUtils.getFullPath(path) + FilenameUtils.getBaseName(path) + (n++) + "." + FilenameUtils.getExtension(path));
		System.out.printf("Descargando: %s\n", url.toString());
		System.out.printf("         en: %s\n", file.toString());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		System.out.printf("       tipo: %s\n", con.getContentType());
		try (
			BufferedInputStream in = new BufferedInputStream(con.getInputStream());
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		){
			out.write(in.readAllBytes());
		}
	}	
}