package pt.ua.givenread;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VolumeIndustryIdentifiers {

    @SerializedName("identifier")
    @Expose
    private String identifier;

    public String getIdentifier(){
        return identifier;
    }
}
