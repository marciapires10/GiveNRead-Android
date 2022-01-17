package pt.ua.givenread;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BooksAPIEndpoints {

    @GET("/books/v1/volumes")
    Call<VolumesResponse> searchBooks(@Query("q") String query);

    @GET("/books/v1/volumes")
    Call<VolumesResponse> searchBookByISBN(@Query("q") String isbn);



}
