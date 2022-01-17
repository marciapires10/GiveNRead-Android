package pt.ua.givenread;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BooksClient {

    final BooksAPIEndpoints apiService;
    final MutableLiveData<VolumesResponse> volumeResponseLiveData;

    private final BookDao bookDao;
    private final LiveData<List<Book>> books;
    private final LiveData<List<Book>> booksToGive;
    private final LiveData<List<Book>> booksToRead;


    public BooksClient(Application application){
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        this.apiService = retrofitInstance.create(BooksAPIEndpoints.class);

        volumeResponseLiveData = new MutableLiveData<>();

        bookDao = BookRoomDatabase.getInstance(application).bookDao();
        books = bookDao.getAll();
        booksToGive = bookDao.getAllToGive();
        booksToRead = bookDao.getAllToRead();

    }

    public void searchBooks(String keyword){
        Call<VolumesResponse> call = apiService.searchBooks(keyword);
        call.enqueue(new Callback<VolumesResponse>() {
            @Override
            public void onResponse(@NonNull Call<VolumesResponse> call, @NonNull Response<VolumesResponse> response) {
                if (response.body() != null){
                    volumeResponseLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<VolumesResponse> call, @NonNull Throwable t) {
                volumeResponseLiveData.postValue(null);
            }
        });
    }

    public void searchBookByISBN(String isbn){
        Call<VolumesResponse> call = apiService.searchBookByISBN(isbn);
        call.enqueue(new Callback<VolumesResponse>() {
            @Override
            public void onResponse(@NonNull Call<VolumesResponse> call, @NonNull Response<VolumesResponse> response) {
                if (response.body() != null){
                    volumeResponseLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<VolumesResponse> call, @NonNull Throwable t) {
                volumeResponseLiveData.postValue(null);
            }
        });
    }

    public LiveData<VolumesResponse> getVolumesResponseLiveData(){
        return volumeResponseLiveData;
    }

    LiveData<List<Book>> getBooks() {
        return books;
    }

    LiveData<List<Book>> getBooksToGive() {
        return booksToGive;
    }

    LiveData<List<Book>> getBooksToRead() {
        return booksToRead;
    }

    void deleteAll(){
        BookRoomDatabase.databaseWriteExecutor.execute(() -> {
            bookDao.deleteAll();
        });
    }

    void insert(Book book){
        BookRoomDatabase.databaseWriteExecutor.execute(() -> {
            bookDao.insert(book);
        });
    }
}
