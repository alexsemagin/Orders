package ru.rarus.restart.orders.di.component;



import javax.inject.Singleton;

import dagger.Component;
import ru.rarus.restart.orders.data.info.InfoStorage;
import ru.rarus.restart.orders.data.settings.RestartSettings;
import ru.rarus.restart.orders.di.module.AppModule;
import ru.rarus.restart.orders.di.module.NetModule;
import ru.rarus.restart.orders.di.managers.SyncManager;
import ru.rarus.restart.orders.ui.auth.AuthPresenter;
import ru.rarus.restart.orders.ui.settings.SettingsPresenter;


@Singleton
@Component(modules = {AppModule.class,  NetModule.class })
public interface AppComponent {

    UserComponent.Builder userComponentBuilder();


    SyncManager exposeSyncManager();
    InfoStorage exposeInfoStorage();
    RestartSettings exposeSettings();


    void inject(AuthPresenter authPresenter);
    void inject(SettingsPresenter settingsPresenter);
}
