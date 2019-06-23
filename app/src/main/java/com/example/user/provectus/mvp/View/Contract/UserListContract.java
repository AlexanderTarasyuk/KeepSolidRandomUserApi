package com.example.user.provectus.mvp.View.Contract;

import com.example.user.provectus.datamanager.model.UserItem;

import java.util.List;

public interface UserListContract {
    void setList(List<UserItem> list);
    void hideLoading();
    void showLoading();
    void showErrorLoading();
}
