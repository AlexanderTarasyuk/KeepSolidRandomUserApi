package com.example.user.provectus.di;

import com.example.user.provectus.mvp.View.UserListActivity;
import com.example.user.provectus.mvp.presenter.UsersListPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Singleton
    @Provides
    public UsersListPresenterImpl provideUserListPresenter() {
        return new UsersListPresenterImpl();
    }
}
