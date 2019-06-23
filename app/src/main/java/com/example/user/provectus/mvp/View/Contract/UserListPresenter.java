package com.example.user.provectus.mvp.View.Contract;

import com.example.user.provectus.mvp.View.UserListActivity;

public interface UserListPresenter {


    void attach(UserListContract userListActivity);

    void detach();

    public boolean isNetworkConnection(UserListActivity userListActivity);

    void loadData();
}
