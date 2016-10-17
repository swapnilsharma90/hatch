package dd.android.hatch.api;


import dd.android.hatch.entities.response.WikiResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * This Interface is like a wrapper to the retrofit used for making server calls
 * <p/>
 * Created by swapsharma on 4/7/16.
 */
public interface WikiApi {

    /* HTTP Methods*/


    /***
     * Get method to get the wiki search results.
     * This is called in the text change listener of search view on the initial screen of the app
     */
    @GET("api.php")
    Observable<WikiResponse> getWikiSearchResultsNew(@Query("action") String action,
                                                     @Query("prop") String prop,
                                                     @Query("format") String format,
                                                     @Query("piprop") String piprop,
                                                     @Query("pithumbsize") int pithumbsize,
                                                     @Query("pilimit") int pilimit,
                                                     @Query("generator") String generator,
                                                     @Query("gpssearch") String searchString,
                                                     @Query("gpsoffset") int gpsoffset);

}