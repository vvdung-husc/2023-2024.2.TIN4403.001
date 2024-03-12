package Book;

public class Book {
    private int resourseId;
    private String title;

    public Book(int resourseId, String title) {
        this.resourseId = resourseId;
        this.title = title;
    }

    public int getResourseId() {
        return resourseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setResourseId(int resourseId) {
        this.resourseId = resourseId;
    }
}
