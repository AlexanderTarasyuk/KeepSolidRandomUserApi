package com.example.user.provectus.di;

import com.example.user.provectus.mvp.View.UserListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {PresenterModule.class})
public interface UsersListPresenterImplComponent {

    void inject(UserListActivity userListActivity);
}
