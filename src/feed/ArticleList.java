package feed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArticleList implements Iterable<Article> {
    // List of articles
    private List<Article> articles;

    // Constructor
    public ArticleList() {
        this.articles = new ArrayList<>();
    }

    // Add an article to the list
    public void addArticle(Article article) {
        this.articles.add(article);
    }

    // Get an article from the list by index
    public Article getArticleIndex(int index) {
        return this.articles.get(index);
    }

    // Get the list of articles
    public List<Article> getArticles() {
        return this.articles;
    }

    // Print all the articles
    public void printArticles() {
        for (Article article : this.articles) {
            article.printArticle();
        }
    }

    // Get the number of articles
    public int getArticlesSize() {
        return this.articles.size();
    }

    // Implement the iterator method to use For-Each loop
    @Override
    public Iterator<Article> iterator() {
        return this.articles.iterator();
    }
}
