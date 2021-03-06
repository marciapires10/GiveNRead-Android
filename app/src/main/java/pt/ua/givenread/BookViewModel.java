package pt.ua.givenread;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookViewModel extends AndroidViewModel {
    private BooksClient bookClient;
    private LiveData<VolumesResponse> volumeResponseLiveData;
    private LiveData<List<Book>> booksToGive;
    private LiveData<List<Book>> booksToRead;

    public BookViewModel(@NonNull Application application) {

        super(application);
    }

    public void init(){
        bookClient = new BooksClient(getApplication());
        volumeResponseLiveData = bookClient.getVolumesResponseLiveData();
        LiveData<List<Book>> books = bookClient.getBooks();
        booksToGive = bookClient.getBooksToGive();
        booksToRead = bookClient.getBooksToRead();
    }

    public void searchBooks(String keyword){
        bookClient.searchBooks(keyword);
    }

    public void searchBookByISBN(String isbn){
        bookClient.searchBookByISBN(isbn);
    }

    public LiveData<List<Book>> getBooksToGive() { return booksToGive;}

    public LiveData<List<Book>> getBooksToRead() { return booksToRead;}

    public List<Book> getBooksToGiveList() throws ExecutionException, InterruptedException {
        return bookClient.getAllToGiveList();
    }

    public List<Book> getBooksToReadList() throws ExecutionException, InterruptedException {
       return bookClient.getAllToReadList();
    }

    public void delete(Book book) { bookClient.delete(book);}

    public void insert(Book book){
        bookClient.insert(book);
    }

    public LiveData<VolumesResponse> getVolumeResponseLiveData(){
        return volumeResponseLiveData;
    }
}
