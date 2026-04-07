package com.dentapp.app.ui.expediente;

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
public final class ExpedienteViewModel_Factory implements Factory<ExpedienteViewModel> {
  private final Provider<ApiService> apiProvider;

  public ExpedienteViewModel_Factory(Provider<ApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public ExpedienteViewModel get() {
    return newInstance(apiProvider.get());
  }

  public static ExpedienteViewModel_Factory create(Provider<ApiService> apiProvider) {
    return new ExpedienteViewModel_Factory(apiProvider);
  }

  public static ExpedienteViewModel newInstance(ApiService api) {
    return new ExpedienteViewModel(api);
  }
}
