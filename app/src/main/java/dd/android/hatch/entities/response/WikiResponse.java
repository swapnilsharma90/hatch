package dd.android.hatch.entities.response;

import com.google.gson.annotations.SerializedName;

import dd.android.hatch.entities.model.Continue;
import dd.android.hatch.entities.model.Query;


public class WikiResponse {
    @SerializedName("continue")
    public final Continue continueObj;
    public final Query query;

    public WikiResponse(Continue continueObj, Query query) {
        this.continueObj = continueObj;
        this.query = query;
    }

    @Override
    public String toString() {
        return "WikiResponse{" +
                "continueObj=" + continueObj +
                ", query=" + query +
                '}';
    }
}

