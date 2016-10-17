package dd.android.hatch.entities.model;


public class Thumbnail {
    public final String source;

    public Thumbnail(String source) {
        this.source = source;
    }
    @Override
    public String toString() {
        return "Thumbnail{" +
                "source='" + source + '\'' +
                '}';
    }
}
