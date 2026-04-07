package com.dentapp.app.ui.home;

import com.dentapp.app.data.api.ApiService;
import com.dentapp.app.utils.TokenStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class HomeDoctorViewModel_Factory implements Factory<HomeDoctorViewModel> {
  private final Provider<ApiService> apiProvider;

  private final Provider<TokenStore> tokenStoreProvider;

  public HomeDoctorViewModel_Factory(Provider<ApiService> apiProvider,
      Provider<TokenStore> tokenStoreProvider) {
    this.apiProvider = apiProvider;
    this.tokenStoreProvider = tokenStoreProvider;
  }

  @Override
  public HomeDoctorViewModel get() {
    return newInstance(apiProvider.get(), tokenStoreProvider.get());
  }

  public static HomeDoctorViewModel_Factory create(Provider<ApiService> apiProvider,
      Provider<TokenStore> tokenStoreProvider) {
    return new HomeDoctorViewModel_Factory(apiProvider, tokenStoreProvider);
  }

  public static HomeDoctorViewModel newInstance(ApiService api, TokenStore tokenStore) {
    return new HomeDoctorViewModel(api, tokenStore);
  }
}
