package pt.ua.givenread;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class BookSearchViewModel extends AndroidViewModel {
    private BooksClient bookClient;
    private LiveData<VolumesResponse> volumeResponseLiveData;

    public BookSearchViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        bookClient = new BooksClient();
        volumeResponseLiveData = bookClient.getVolumesReponseLiveData();
    }

    public void searchBooks(String keyword, String author){
        bookClient.searchBooks(keyword, author);
    }

    public LiveData<VolumesResponse> getVolumeResponseLiveData(){
        return volumeResponseLiveData;
    }
}
