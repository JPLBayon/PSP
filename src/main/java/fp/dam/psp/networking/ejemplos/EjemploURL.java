package fp.dam.psp.networking.ejemplos;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class EjemploURL { 

    public static void main(String[] args) throws URISyntaxException { 

        URL url; 
        try { 
            System.out.println("Constructor simple para una URL:"); 
            url = new URI("http://docs.oracle.com/").toURL(); 
            Visualizar(url); 
            System.out.println("Otro constructor simple para una URL:"); 
            url = new URI("http://www.example.com/action.php?name=john&age=24").toURL();
            Visualizar(url);
            url = new URI("http","www.example.com", "/action.php", "name=john&age=24", null).toURL();
            Visualizar(url);
            
//            System.out.println("Constructor para protocolo + URL + directorio:"); 
//            url = new URL("http", "docs.oracle.com", "/javase/7"); 
//            Visualizar(url); 
//            System.out.println("Constructor para protocolo + URL + puerto + directorio:"); 
//            url = new URL("http", "docs.oracle.com", 80, "/javase/7"); 
//            Visualizar(url); 
//            System.out.println("Constructor para un objeto URL y un directorio:"); 
//            URL urlBase = new URL("http://docs.oracle.com/"); 
//            url = new URL(urlBase, "/javase/7/docs/api/java/net/URL.html"); 
//            Visualizar(url); 
        } catch (MalformedURLException e) { 
            e.printStackTrace(); 
        } 
    } 

    private static void Visualizar(URL url) { 
        System.out.println("\tURL completa: " + url.toString()); 
        System.out.println("\tgetProtocol(): " + url.getProtocol()); 
        System.out.println("\tgetHost(): " + url.getHost()); 
        System.out.println("\tgetPort(): " + url.getPort()); 
        System.out.println("\tgetFile(): " + url.getFile()); 
        System.out.println("\tgetUserlnfo(): " + url.getUserInfo()); 
        System.out.println("\tgetPath(): " + url.getPath()); 
        System.out.println("\tgetAuthority(): " + url.getAuthority()); 
        System.out.println("\tgetQuery(): " + url.getQuery()); 
        System.out.println("=================================================="); 
    } 

     

} 