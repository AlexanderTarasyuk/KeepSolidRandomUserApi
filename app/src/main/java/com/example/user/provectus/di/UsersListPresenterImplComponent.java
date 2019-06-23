package com.example.user.provectus.di;

import com.example.user.provectus.mvp.View.UserListActivity;
import com.example.user.provectus.mvp.presenter.UsersListPresenterImpl;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(modules = {PresenterModule.class, NetWorkModule.class, UserlistModule.class})
public interface UsersListPresenterImplComponent {

    void inject(UserListActivity userListActivity);
    void inject(UsersListPresenterImpl usersListPresenter);

}
