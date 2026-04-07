package com.dentapp.app.ui.tratamiento;

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
public final class TreatmentTrackingViewModel_Factory implements Factory<TreatmentTrackingViewModel> {
  private final Provider<ApiService> apiProvider;

  public TreatmentTrackingViewModel_Factory(Provider<ApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public TreatmentTrackingViewModel get() {
    return newInstance(apiProvider.get());
  }

  public static TreatmentTrackingViewModel_Factory create(Provider<ApiService> apiProvider) {
    return new TreatmentTrackingViewModel_Factory(apiProvider);
  }

  public static TreatmentTrackingViewModel newInstance(ApiService api) {
    return new TreatmentTrackingViewModel(api);
  }
}
