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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import utils.JSONParser;

public class FeedParser {

    // Esta función ya está implementada y probada con un unit test, no es necesario modificarla (en teoría)
    public static List<Article> parseXML(String xmlData) {

        List<Article> articles = new ArrayList<>();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(xmlData)));

            NodeList nList = doc.getElementsByTagName("item");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String title = eElement.getElementsByTagName("title").item(0).getTextContent();
                    String description = eElement.getElementsByTagName("description").item(0).getTextContent();
                    String link = eElement.getElementsByTagName("link").item(0).getTextContent();
                    String pubDate = eElement.getElementsByTagName("pubDate").item(0).getTextContent();

                    articles.add(new Article(title, description, link, pubDate));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Esto lo usé para probar que los artículos se estaban parseando correctamente
        /*for (Article article : articles) {
            article.printArticle();
        }*/

        return articles;
    }

    // Falta probar si esta función funciona correctamente
    // Esta funcion deberia leer el archivo feeds.json, obtener las urls de los feeds y obtener los articulos de cada url
    public static List<Article> parseXMLFromURL() throws MalformedURLException, IOException, Exception {
        // Leer el archivo feeds.json
        String feedContent = new String(Files.readAllBytes(Paths.get("src/data/feeds.json")));
        //String feedContent = String.valueOf(JSONParser.parseJsonFeedsData("src/data/feeds.json"));

        // Convertir el contenido del archivo a un objeto JSONArray
        JSONArray feeds = new JSONArray(feedContent);

        List<Article> allArticles = new ArrayList<>();

        // Iterar sobre cada objeto en el JSONArray
        for (int i = 0; i < feeds.length(); i++) {
            // Obtener el objeto JSONObject actual
            JSONObject feed = feeds.getJSONObject(i);

            // Obtener la URL del feed
            String urlFromFeed = feed.getString("url");

            // Obtenemos el contenido de la url
            String xmlData = fetchFeed(urlFromFeed);

            // Parseamos el contenido de la url
            List<Article> articles = parseXML(xmlData);

            // Aquí puedes hacer algo con los artículos, como imprimirlos
            for (Article article : articles) {
                article.printArticle();
            }

            allArticles.addAll(articles);
        }
        return allArticles;
    }

    // Esta función ya vino implementada
    public static String fetchFeed(String feedURL) throws MalformedURLException, IOException, Exception {

        URL url = new URL(feedURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        // TODO: Cambiar el user-agent al nombre de su grupo. done
        // Si todos los grupos usan el mismo user-agent, el servidor puede bloquear las solicitudes.
        connection.setRequestProperty("group_33", "lab_paradigmas");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

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
}
