package feed;

public class Article {
    private String title;
    private String description;
    private String link;
    private String pubDate;

    // Contructor clase Article
    public Article(String title, String description, String link, String pubDate) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLink() {
        return this.link;
    }

    public String getPubDate() {
        return this.pubDate;
    }

    public void printArticle() {
        System.out.println("Title: " + this.title);
        System.out.println("Description: " + this.description);
        System.out.println("Publication Date: " + this.pubDate);
        System.out.println("Link: " + this.link);
        System.out.println("*************************************************************************************");
    }
}