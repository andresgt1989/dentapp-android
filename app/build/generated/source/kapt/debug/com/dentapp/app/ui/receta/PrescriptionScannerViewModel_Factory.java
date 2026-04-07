package com.dentapp.app.ui.receta;

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
public final class PrescriptionScannerViewModel_Factory implements Factory<PrescriptionScannerViewModel> {
  private final Provider<ApiService> apiProvider;

  public PrescriptionScannerViewModel_Factory(Provider<ApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public PrescriptionScannerViewModel get() {
    return newInstance(apiProvider.get());
  }

  public static PrescriptionScannerViewModel_Factory create(Provider<ApiService> apiProvider) {
    return new PrescriptionScannerViewModel_Factory(apiProvider);
  }

  public static PrescriptionScannerViewModel newInstance(ApiService api) {
    return new PrescriptionScannerViewModel(api);
  }
}
