package feed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import utils.FeedsData;

public abstract class FeedParser {

    // Este método ya está implementado y probado con un unit test
    // Este método es el que se encarga de parsear el contenido de un XML y devolver una lista de artículos
    private static ArticleList parseXML(String xmlData) {

        ArticleList articles = new ArticleList();

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

                    if (!(titleNode == null || descriptionNode == null || linkNode == null || pubDateNode == null)) {
                        String title = titleNode.getTextContent();
                        String description = descriptionNode.getTextContent();
                        String link = linkNode.getTextContent();
                        String pubDate = pubDateNode.getTextContent();

                        // Crea un nuevo artículo y lo añade a la lista de artículos
                        articles.addArticle(new Article(title, description, link, pubDate));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return articles;
    }

    // Este método ya está implementado y probado con un unit test
    // Este método es el que se encarga de leer el archivo feeds.json y parsear cada feed
    public static ArticleList getArticlesFromFeeds(List<FeedsData> feedsDataList, String feedKey) throws Exception {

        ArticleList allArticles = new ArticleList();

        // Si se seleccionó un feed específico, se obtienen los artículos de ese feed
        // Si no, se obtienen los artículos de todos los feeds
        if (feedKey != null) {
            boolean feedFound = false;
            for (FeedsData feedData : feedsDataList) {
                if (feedData.getLabel().equals(feedKey)) {
                    feedFound = true;
                    // Obtiene la lista de artículos del feed seleccionado
                    ArticleList articles = getArticles(feedData.getUrl());
                    for (Article article : articles) {
                        allArticles.addArticle(article);
                    }
                    break;
                }
            }
            if (!feedFound) {
                System.out.println("No se ha encontrado el feed con la clave " + feedKey);
                System.exit(1);
            }
        } else {
            for (FeedsData feedData : feedsDataList) {
                // Obtiene la lista de artículos de cada feed
                ArticleList articles = getArticles(feedData.getUrl());
                for (Article article : articles) {
                    allArticles.addArticle(article);
                }
            }
        }
        return allArticles;
    }

    // Este método devuelve una lista de artículos a partir de una URL
    private static ArticleList getArticles(String url) throws Exception {
        // Hace la solicitud HTTP al feed y devuelve el XML como un String
        String xmlData = fetchFeed(url);
        // Parsea el XML y devuelve una lista de artículos
        return parseXML(xmlData);
    }

    // Este método ya vino implementada
    // Este método es el que se encarga de hacer la solicitud HTTP a la URL del feed
    private static String fetchFeed(String feedURL) throws Exception {

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
        connection.setRequestProperty("User-Agent", "lab_paradigmas_group_33");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        return connection;
    }
}
