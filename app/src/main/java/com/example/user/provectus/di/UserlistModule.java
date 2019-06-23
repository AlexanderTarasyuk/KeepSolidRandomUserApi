package com.example.user.provectus.di;

import com.example.user.provectus.mvp.View.UserListActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class UserlistModule {
    @Provides
    public UserListActivity provideUserlistactivity(){
        return new UserListActivity();
    }
}
