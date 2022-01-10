package pt.ua.givenread;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VolumeImageLinks {

    @SerializedName("smallThumbnail")
    @Expose
    private String smallThumbnail;

    public String getSmallThumbnail(){
        return smallThumbnail;
    }
}
