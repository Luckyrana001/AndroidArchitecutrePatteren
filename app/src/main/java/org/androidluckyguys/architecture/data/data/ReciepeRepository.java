package org.androidluckyguys.architecture.data.data;

import android.arch.lifecycle.MutableLiveData;


import org.androidluckyguys.architecture.data.util.NetworkResponse;

import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LuckyRana on 04/02/2018.
 */

public class ReciepeRepository {

    public ReciepeRepository() {
    }

    public MutableLiveData<NetworkResponse> getReceipesListData() {
        final MutableLiveData<NetworkResponse> getReceipesListDataResponseMutableLiveData = new MutableLiveData<>();

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(60, TimeUnit.SECONDS);
        okHttpClient.readTimeout(60, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(60, TimeUnit.SECONDS);
        okHttpClient.retryOnConnectionFailure(true);

        FoodFormulasAPIService foodFormulasAPIService = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build()
                .create(FoodFormulasAPIService.class);

        Call<Receipe[]> couponsListDataResponseCall = foodFormulasAPIService.getReceipesListData();

        couponsListDataResponseCall.enqueue(new Callback<Receipe[]>() {
            @Override
            public void onResponse(Call<Receipe[]> call, Response<Receipe[]> response) {
                if (response.isSuccessful()) {

                    Receipe[] receipes = response.body();

                    if(receipes != null){
                        getReceipesListDataResponseMutableLiveData.setValue(NetworkResponse.success(receipes));
                    }
                } else {
                    if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        getReceipesListDataResponseMutableLiveData.setValue(NetworkResponse.unAuthorised("Session Expired"));
                    } else {
                        String errorMessage = "Oops something went wrong!"; //Hard coded for code clarity
                        getReceipesListDataResponseMutableLiveData.setValue(NetworkResponse.error(null,errorMessage));
                    }
                }
            }

            @Override
            public void onFailure(Call<Receipe[]> call, Throwable t) {
                String failureMessage = "Please check your network connection";
                getReceipesListDataResponseMutableLiveData.setValue(NetworkResponse.error(null,failureMessage));
            }
        });
        return getReceipesListDataResponseMutableLiveData;
    }
}
