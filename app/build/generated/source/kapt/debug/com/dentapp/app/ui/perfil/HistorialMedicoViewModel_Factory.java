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
public final class HistorialMedicoViewModel_Factory implements Factory<HistorialMedicoViewModel> {
  private final Provider<ApiService> apiProvider;

  public HistorialMedicoViewModel_Factory(Provider<ApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public HistorialMedicoViewModel get() {
    return newInstance(apiProvider.get());
  }

  public static HistorialMedicoViewModel_Factory create(Provider<ApiService> apiProvider) {
    return new HistorialMedicoViewModel_Factory(apiProvider);
  }

  public static HistorialMedicoViewModel newInstance(ApiService api) {
    return new HistorialMedicoViewModel(api);
  }
}
