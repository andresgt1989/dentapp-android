package com.dentapp.app.ui.radiografia;

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
public final class XRayCaptureViewModel_Factory implements Factory<XRayCaptureViewModel> {
  private final Provider<ApiService> apiProvider;

  public XRayCaptureViewModel_Factory(Provider<ApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public XRayCaptureViewModel get() {
    return newInstance(apiProvider.get());
  }

  public static XRayCaptureViewModel_Factory create(Provider<ApiService> apiProvider) {
    return new XRayCaptureViewModel_Factory(apiProvider);
  }

  public static XRayCaptureViewModel newInstance(ApiService api) {
    return new XRayCaptureViewModel(api);
  }
}
