package com.example.user.provectus.di;

import com.example.user.provectus.datamanager.remoteData.NetworkModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetWorkModule {
    @Singleton
    @Provides
    public NetworkModel provideNetworkModel() {
        return new NetworkModel();
    }
}
