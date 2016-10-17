package dd.android.hatch.entities.model;

import com.google.gson.annotations.SerializedName;

public class Continue {
    public final int gpsoffset;

    @SerializedName("continue")
    public final String continuePage;

    public Continue(int gpsoffset, String continuePage) {
        this.gpsoffset = gpsoffset;
        this.continuePage = continuePage;
    }

    @Override
    public String toString() {
        return "Continue{" +
                "gpsoffset=" + gpsoffset +
                ", continuePage='" + continuePage + '\'' +
                '}';
    }
}
