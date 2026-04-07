package com.dentapp.app.ui.qr;

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
public final class GenerarQRViewModel_Factory implements Factory<GenerarQRViewModel> {
  private final Provider<ApiService> apiProvider;

  public GenerarQRViewModel_Factory(Provider<ApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public GenerarQRViewModel get() {
    return newInstance(apiProvider.get());
  }

  public static GenerarQRViewModel_Factory create(Provider<ApiService> apiProvider) {
    return new GenerarQRViewModel_Factory(apiProvider);
  }

  public static GenerarQRViewModel newInstance(ApiService api) {
    return new GenerarQRViewModel(api);
  }
}
