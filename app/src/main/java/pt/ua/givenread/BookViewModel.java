package pt.ua.givenread;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BookViewModel extends AndroidViewModel {
    private BooksClient bookClient;
    private LiveData<VolumesResponse> volumeResponseLiveData;
    private LiveData<List<Book>> books;
    private LiveData<List<Book>> booksToGive;
    private LiveData<List<Book>> booksToRead;


    public BookViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        bookClient = new BooksClient(getApplication());
        volumeResponseLiveData = bookClient.getVolumesResponseLiveData();
        books = bookClient.getBooks();
        booksToGive = bookClient.getBooksToGive();
        booksToRead = bookClient.getBooksToRead();
    }

    public void searchBooks(String keyword){
        bookClient.searchBooks(keyword);
    }

    public void searchBookByISBN(String isbn){
        bookClient.searchBookByISBN(isbn);
    }

    public LiveData<List<Book>> getBooks(){
        return books;
    }

    public LiveData<List<Book>> getBooksToGive() { return booksToGive;}

    public LiveData<List<Book>> getBooksToRead() { return booksToRead;}

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
