package pt.ua.givenread;

import pt.ua.givenread.BookInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BooksAPIEndpoints {

    @GET("/books/v1/volumes")
    Call<VolumesResponse> searchBooks(@Query("q") String query);



}
