package com.dentapp.app.ui.perfil;

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
public final class HistorialCitasViewModel_Factory implements Factory<HistorialCitasViewModel> {
  private final Provider<ApiService> apiProvider;

  public HistorialCitasViewModel_Factory(Provider<ApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public HistorialCitasViewModel get() {
    return newInstance(apiProvider.get());
  }

  public static HistorialCitasViewModel_Factory create(Provider<ApiService> apiProvider) {
    return new HistorialCitasViewModel_Factory(apiProvider);
  }

  public static HistorialCitasViewModel newInstance(ApiService api) {
    return new HistorialCitasViewModel(api);
  }
}
