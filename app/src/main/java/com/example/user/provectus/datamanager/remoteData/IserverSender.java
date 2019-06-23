package com.example.user.provectus.datamanager.remoteData;

import com.example.user.provectus.datamanager.remoteData.constants.Constants;
import com.example.user.provectus.datamanager.model.UsersListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IserverSender {
    @GET("api")
    Observable<UsersListResponse> load_random_users(@Query(Constants.RESULT_PARAM) int result,
                                                    @Query(Constants.ICN_PARAM) String inc);

}
