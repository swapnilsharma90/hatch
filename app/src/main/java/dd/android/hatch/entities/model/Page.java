package dd.android.hatch.entities.model;

public class Page {
    public final String title;
    public final Thumbnail thumbnail;

    public Page(String title, Thumbnail thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Page{" +
                "title='" + title + '\'' +
                ", thumbnail=" + thumbnail +
                '}';
    }
}
