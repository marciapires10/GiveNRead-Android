package pt.ua.givenread;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VolumesResponse {

    @SerializedName("kind")
    @Expose
    private String kind;

    @SerializedName("items")
    @Expose
    final
    ArrayList<Volume> items = null;

    @SerializedName("totalItems")
    @Expose
    int totalItems;

    public String getKind(){
        return kind;
    }

    public ArrayList<Volume> getItems(){
        return items;
    }

    public int getTotalItems(){
        return totalItems;
    }

}
