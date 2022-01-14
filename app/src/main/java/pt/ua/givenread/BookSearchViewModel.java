package pt.ua.givenread;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BookSearchViewModel extends AndroidViewModel {
    private BooksClient bookClient;
    private LiveData<VolumesResponse> volumeResponseLiveData;
    private LiveData<List<Book>> books;

    public BookSearchViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        bookClient = new BooksClient(getApplication());
        volumeResponseLiveData = bookClient.getVolumesResponseLiveData();
        books = bookClient.getBooks();
    }

    public void searchBooks(String keyword){
        bookClient.searchBooks(keyword);
    }

    public LiveData<List<Book>> getBooks(){
        return books;
    }

    public void deleteAll(){
        bookClient.deleteAll();
    }

    public void insert(Book book){
        bookClient.insert(book);
    }

    public LiveData<VolumesResponse> getVolumeResponseLiveData(){
        return volumeResponseLiveData;
    }
}
