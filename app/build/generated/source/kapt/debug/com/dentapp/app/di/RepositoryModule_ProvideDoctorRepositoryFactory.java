package com.dentapp.app.di;

import com.dentapp.app.data.api.ApiService;
import com.dentapp.app.data.repository.DoctorRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class RepositoryModule_ProvideDoctorRepositoryFactory implements Factory<DoctorRepository> {
  private final Provider<ApiService> apiProvider;

  public RepositoryModule_ProvideDoctorRepositoryFactory(Provider<ApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public DoctorRepository get() {
    return provideDoctorRepository(apiProvider.get());
  }

  public static RepositoryModule_ProvideDoctorRepositoryFactory create(
      Provider<ApiService> apiProvider) {
    return new RepositoryModule_ProvideDoctorRepositoryFactory(apiProvider);
  }

  public static DoctorRepository provideDoctorRepository(ApiService api) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideDoctorRepository(api));
  }
}
