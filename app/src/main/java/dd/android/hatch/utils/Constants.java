package dd.android.hatch.utils;

/**
 * Created by swapsharma on 4/7/16.
 */
public class Constants {
//    https://en.wikipedia.org/w/api.php?action=query&prop=pageimages&for
//    // mat=json&piprop=thumbnail&pithumbsize=50&pilimit=50&generator=prefixsearch&gpssearch=saibaba
    public static final String BASE_URL = "https://en.wikipedia.org/w/";
    public static final String ACTION = "query";
    public static final String PROP = "pageimages";
    public static final String FORMAT = "json";
    public static final String PIPROP = "thumbnail";
    public static final String GENERATOR = "prefixsearch";
    //max size of image
    public static final int PITHUMBSIZE = 1024;
    //no of pages for users only 50 it is  :(
    public static final int PILIMIT = 50;
}
