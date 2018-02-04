package org.androidluckyguys.architecture.data.viewModel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import org.androidluckyguys.architecture.data.data.Receipe;
import org.androidluckyguys.architecture.data.data.ReciepeRepository;
import org.androidluckyguys.architecture.data.util.NetworkResponse;
import org.androidluckyguys.architecture.data.util.SnackbarMessage;

/**
 * Created by LuckyRana on 04/02/2018.
 */

public class ReciepeViewModel extends AndroidViewModel {

    private ReciepeRepository mReciepeRepository;
    SnackbarMessage mSnackbarTextLiveData = new SnackbarMessage();
    MediatorLiveData<Receipe[]> receipiesListDataResponseMediatorLiveData = new MediatorLiveData<>();
    private MutableLiveData<NetworkResponse> networkResponseMutableLiveData;


    public ReciepeViewModel(@NonNull Application application) {
        super(application);
        this.mReciepeRepository = new ReciepeRepository();
    }

    public SnackbarMessage getSnackbarMessage() {
        return mSnackbarTextLiveData;
    }

    public LiveData<Receipe[]> getReceipesListData() {
        networkResponseMutableLiveData = mReciepeRepository.getReceipesListData();

        LiveData<Receipe[]> receipiesListDataResponseLiveData = Transformations.switchMap(networkResponseMutableLiveData, new Function<NetworkResponse, LiveData<Receipe[]>>() {
            @Override
            public LiveData<Receipe[]> apply(NetworkResponse networkResponse) {
                if (networkResponse == null) {
                    getSnackbarMessage().setValue("Oops something went wrong,Try again!");
                    return null;
                }

                if (networkResponse.status == NetworkResponse.SUCCESS) {
                    if(networkResponse.data != null) {
                        receipiesListDataResponseMediatorLiveData.setValue((Receipe[]) networkResponse.data);
                    }
                }
                else if(networkResponse.status == NetworkResponse.BAD_REQUEST){
                    if(networkResponse.data != null) {
                        getSnackbarMessage().setValue(networkResponse.errorMessage);
                        return null;
                    }
                }
                else if(networkResponse.status == NetworkResponse.UNAUTHORISED){
                    if(networkResponse.data != null){
                        getSnackbarMessage().setValue((String) networkResponse.data);
                        return null;
                    }
                }
                else if(networkResponse.status == NetworkResponse.FAILURE){
                    if(networkResponse.errorMessage != null) {
                        getSnackbarMessage().setValue(networkResponse.errorMessage);
                        return null;
                    }
                }
                else if(networkResponse.status == NetworkResponse.NO_NETWORK){
                    if(networkResponse.data != null) {
                        getSnackbarMessage().setValue((String) networkResponse.data);
                        return null;
                    }
                }
                else if(networkResponse.status == NetworkResponse.LOADING){

                }
                return receipiesListDataResponseMediatorLiveData;
            }
        });
        return receipiesListDataResponseLiveData;
    }
}
