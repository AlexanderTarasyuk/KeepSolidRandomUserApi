package com.example.user.provectus.di;

import com.example.user.provectus.datamanager.remoteData.NetworkModel;

import dagger.Module;
import dagger.Provides;

@Module
public class NetWorkModule {
    @Provides
    public NetworkModel provideNetworkModel() {
        return new NetworkModel();
    }
}
