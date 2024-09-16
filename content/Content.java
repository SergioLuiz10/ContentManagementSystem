package content;

import java.util.Date;
import java.util.List;

import persistency.ContentPersistencyDB;

public class Content {
    private int id;
    private String title;
    private String content;
    private Date createdAt;
    private static int counter = 1;

    private static ContentPersistencyDB contentDb = new ContentPersistencyDB();

    public Content() {

    }

    public Content(String title, String content) {


        this.title = title;
        this.content = content;
        this.createdAt = new Date();
        this.id = counter;
        this.counter ++;

        setContent(content);

        setTitle(title);
    }


    public static void listContents(){
        List<Content> contents = contentDb.list();
        if (contents.size() <= 0) {
            System.out.println("\nSem artigos no momento\n");
        } else {
            contents.forEach(content -> {
                System.out.println("\n"+ content.getId() + "- " +  "Titulo: " + content.getTitle() + "\n" + "   Conteúdo: " + content.getContent());
            });
        }
    }

    public static void addContent(Content content) {
        contentDb.save(content);
    }

    public static Content findOne(int id) {
        List<Content> contents = contentDb.list();
        Content selectedContent = null;
        for (Content content : contents) {
            if(content.getId() == id) selectedContent = content;
        }
        return selectedContent;
    }

    public static void deleteContent(int id) {
        Content deletedContent = findOne(id);
        contentDb.delete(deletedContent.getId());
    }

    public static void updateContent(Content content) {
        contentDb.update(content);
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}