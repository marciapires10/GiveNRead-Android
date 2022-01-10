package pt.ua.givenread;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BooksClient {

    private BooksAPIEndpoints apiService;
    private MutableLiveData<VolumesResponse> volumeResponseLiveData;

    public BooksClient(){
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        this.apiService = retrofitInstance.create(BooksAPIEndpoints.class);

        volumeResponseLiveData = new MutableLiveData<>();

    }

    public void searchBooks(String keyword, String author){
        ArrayList<BookInfo> books = new ArrayList<>();
        Call<VolumesResponse> call = apiService.searchBooks(keyword, author);
        call.enqueue(new Callback<VolumesResponse>() {
            @Override
            public void onResponse(Call<VolumesResponse> call, Response<VolumesResponse> response) {
                if (response.body() != null){
                    volumeResponseLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<VolumesResponse> call, Throwable t) {
                volumeResponseLiveData.postValue(null);
            }
        });
    }

    public LiveData<VolumesResponse> getVolumesReponseLiveData(){
        return volumeResponseLiveData;
    }
}
