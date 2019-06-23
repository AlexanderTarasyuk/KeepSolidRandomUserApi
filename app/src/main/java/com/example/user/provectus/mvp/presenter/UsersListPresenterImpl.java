package com.example.user.provectus.mvp.presenter;

import com.example.user.provectus.datamanager.model.UserItem;
import com.example.user.provectus.datamanager.model.UsersListResponse;
import com.example.user.provectus.datamanager.remoteData.NetworkModel;
import com.example.user.provectus.manager.ConnectiviteManager;
import com.example.user.provectus.mvp.View.Contract.UserListContract;
import com.example.user.provectus.mvp.View.Contract.UserListPresenter;
import com.example.user.provectus.mvp.View.UserListActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class UsersListPresenterImpl implements UserListPresenter {
    @Inject
    NetworkModel networkModel;

    @Inject
    UserListActivity contract;

    private Disposable usersDisposable = null;

    public UsersListPresenterImpl() {

    }

    @Override
    public void loadData() {
        Timber.d("start loading");
        usersDisposable = networkModel
                .getUsers()
                .map((Function<UsersListResponse, List<UserItem>>)
                        usersListResponse -> usersListResponse == null ? new ArrayList<>() : usersListResponse.getResults())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userItems -> {
                    contract.hideLoading();
                    contract.setList(userItems);
                }, throwable -> contract.showErrorLoading());
        Timber.d("stop loading");
    }

    public void unSubscribe() {
        if (usersDisposable != null) {
            usersDisposable.dispose();
        }
    }

    @Override
    public void attach(UserListContract userListActivity) {
        contract = (UserListActivity) userListActivity;
    }


    @Override
    public void detach() {

        contract = null;
    }

    @Override
    public boolean isNetworkConnection(UserListActivity userListActivity) {
        return new ConnectiviteManager().isNetworkConnection(userListActivity);
    }
}
