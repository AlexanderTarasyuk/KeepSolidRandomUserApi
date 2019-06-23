package com.example.user.provectus.datamanager.remoteData;

import com.example.user.provectus.datamanager.remoteData.constants.Constants;
import com.example.user.provectus.datamanager.model.UsersListResponse;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class NetworkModel {
    private IserverSender iserverSender;

    public NetworkModel() {
        Retrofit retrofit = RetrofitClient.getInstance();
        iserverSender = retrofit.create(IserverSender.class);
    }

    public Observable<UsersListResponse> getUsers(){
        return iserverSender.load_random_users(
                Constants.COUNT_RANDOM_CONTACTS,
                Constants.INC_LIST_PARAM);
    }
}
