package feed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import utils.JSONParser;

public class FeedParser {

    // Este método ya está implementado y probado con un unit test, no es necesario modificarlo
    // Este método es el que se encarga de parsear el contenido de un XML y devolver una lista de artículos
    public static List<Article> parseXML(String xmlData) {

        List<Article> articles = new ArrayList<>();

        try {
            // Crea un DocumentBuilder para parsear el XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(xmlData)));

            // Obtiene todos los elementos "item"
            NodeList nList = doc.getElementsByTagName("item");

            // Itera sobre cada elemento "item"
            for (int i = 0; i < nList.getLength(); i++) {
                // Obtiene el nodo actual
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    // Convierte el nodo a un Element
                    Element element = (Element) nNode;

                    // Obtiene los nodos "title", "description", "link" y "pubDate"
                    Node titleNode = element.getElementsByTagName("title").item(0);
                    Node descriptionNode = element.getElementsByTagName("description").item(0);
                    Node linkNode = element.getElementsByTagName("link").item(0);
                    Node pubDateNode = element.getElementsByTagName("pubDate").item(0);

                    String title = titleNode != null ? titleNode.getTextContent() : "";
                    String description = descriptionNode != null ? descriptionNode.getTextContent() : "";
                    String link = linkNode != null ? linkNode.getTextContent() : "";
                    String pubDate = pubDateNode != null ? pubDateNode.getTextContent() : "";

                    // Crea un nuevo artículo y lo añade a la lista de artículos
                    articles.add(new Article(title, description, link, pubDate));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return articles;
    }

    // Este método ya está implementado y probado con un unit test, no es necesario modificarlo
    // Este método es el que se encarga de leer el archivo feeds.json y parsear cada feed
    public static List<Article> parseXMLFromURL() throws MalformedURLException, IOException, Exception {
        // Lee el archivo feeds.json
        String feedContent = new String(Files.readAllBytes(Paths.get("src/data/feeds.json")));

        // Convierte el contenido del archivo a un objeto JSONArray
        JSONArray feeds = new JSONArray(feedContent);

        List<Article> allArticles = new ArrayList<>();

        // Itera sobre cada objeto en el JSONArray
        for (int i = 0; i < feeds.length(); i++) {
            // Obtiene el objeto JSONObject actual
            JSONObject feed = feeds.getJSONObject(i);

            // Obtiene la URL del feed
            String urlFromFeed = feed.getString("url");

            // Obtiene el contenido de la url del feed en formato XML como un String
            String xmlData = fetchFeed(urlFromFeed);

            // Parseamos el contenido de la url
            List<Article> articles = parseXML(xmlData);

            // Aquí podemos hacer algo con los artículos, como imprimirlos
            for (Article article : articles) {
                article.printArticle();
            }

            // Agrega los artículos a la lista de todos los artículos
            allArticles.addAll(articles);
        }
        return allArticles;
    }

    // Este método ya vino implementada
    // Este método es el que se encarga de hacer la solicitud HTTP a la URL del feed
    public static String fetchFeed(String feedURL) throws MalformedURLException, IOException, Exception {

        HttpURLConnection connection = getHttpURLConnection(feedURL);

        int status = connection.getResponseCode();
        if (status != 200) {
            throw new Exception("HTTP error code: " + status);
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();
            return content.toString();
        }
    }

    // Este método realiza la conexión a la URL del feed
    private static HttpURLConnection getHttpURLConnection(String feedURL) throws IOException {
        URL url = new URL(feedURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        //connection.setRequestProperty("Content-Type", "application/json");   No sé por qué estaba esto

        // TODO: Cambiar el user-agent al nombre de su grupo. done
        // Si todos los grupos usan el mismo user-agent, el servidor puede bloquear las solicitudes.
        connection.setRequestProperty("User-Agent", "lab_paradigmas_group_33");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        return connection;
    }
}
