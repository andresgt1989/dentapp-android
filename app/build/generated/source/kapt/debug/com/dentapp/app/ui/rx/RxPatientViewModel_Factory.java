package com.dentapp.app.ui.rx;

import com.dentapp.app.data.api.ApiService;
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
public final class RxPatientViewModel_Factory implements Factory<RxPatientViewModel> {
  private final Provider<ApiService> apiProvider;

  public RxPatientViewModel_Factory(Provider<ApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public RxPatientViewModel get() {
    return newInstance(apiProvider.get());
  }

  public static RxPatientViewModel_Factory create(Provider<ApiService> apiProvider) {
    return new RxPatientViewModel_Factory(apiProvider);
  }

  public static RxPatientViewModel newInstance(ApiService api) {
    return new RxPatientViewModel(api);
  }
}
