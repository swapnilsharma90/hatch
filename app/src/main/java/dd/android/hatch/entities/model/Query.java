package dd.android.hatch.entities.model;

import java.util.Map;

public class Query {
    public final Map<String, Page> pages;

    public Query(Map<String, Page> pages) {
        this.pages = pages;
    }


    @Override
    public String toString() {
        return "Query{" +
                "pages=" + pages +
                '}';
    }
}
