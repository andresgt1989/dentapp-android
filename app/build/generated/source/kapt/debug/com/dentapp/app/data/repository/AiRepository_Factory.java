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
public final class AiRepository_Factory implements Factory<AiRepository> {
  private final Provider<ApiService> apiProvider;

  public AiRepository_Factory(Provider<ApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public AiRepository get() {
    return newInstance(apiProvider.get());
  }

  public static AiRepository_Factory create(Provider<ApiService> apiProvider) {
    return new AiRepository_Factory(apiProvider);
  }

  public static AiRepository newInstance(ApiService api) {
    return new AiRepository(api);
  }
}
