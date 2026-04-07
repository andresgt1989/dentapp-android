package com.dentapp.app.data.repository;

import com.dentapp.app.data.api.ApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DoctorRepository_Factory implements Factory<DoctorRepository> {
  private final Provider<ApiService> apiProvider;

  public DoctorRepository_Factory(Provider<ApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public DoctorRepository get() {
    return newInstance(apiProvider.get());
  }

  public static DoctorRepository_Factory create(Provider<ApiService> apiProvider) {
    return new DoctorRepository_Factory(apiProvider);
  }

  public static DoctorRepository newInstance(ApiService api) {
    return new DoctorRepository(api);
  }
}
