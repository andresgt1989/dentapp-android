package com.dentapp.app.ui.home;

import com.dentapp.app.data.api.ApiService;
import com.dentapp.app.data.repository.DoctorRepository;
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
public final class HomePatientViewModel_Factory implements Factory<HomePatientViewModel> {
  private final Provider<DoctorRepository> doctorRepositoryProvider;

  private final Provider<ApiService> apiProvider;

  private final Provider<TokenStore> tokenStoreProvider;

  public HomePatientViewModel_Factory(Provider<DoctorRepository> doctorRepositoryProvider,
      Provider<ApiService> apiProvider, Provider<TokenStore> tokenStoreProvider) {
    this.doctorRepositoryProvider = doctorRepositoryProvider;
    this.apiProvider = apiProvider;
    this.tokenStoreProvider = tokenStoreProvider;
  }

  @Override
  public HomePatientViewModel get() {
    return newInstance(doctorRepositoryProvider.get(), apiProvider.get(), tokenStoreProvider.get());
  }

  public static HomePatientViewModel_Factory create(
      Provider<DoctorRepository> doctorRepositoryProvider, Provider<ApiService> apiProvider,
      Provider<TokenStore> tokenStoreProvider) {
    return new HomePatientViewModel_Factory(doctorRepositoryProvider, apiProvider, tokenStoreProvider);
  }

  public static HomePatientViewModel newInstance(DoctorRepository doctorRepository, ApiService api,
      TokenStore tokenStore) {
    return new HomePatientViewModel(doctorRepository, api, tokenStore);
  }
}
