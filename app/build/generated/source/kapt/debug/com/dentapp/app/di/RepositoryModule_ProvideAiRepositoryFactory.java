package com.dentapp.app.di;

import com.dentapp.app.data.api.ApiService;
import com.dentapp.app.data.repository.AiRepository;
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
public final class RepositoryModule_ProvideAiRepositoryFactory implements Factory<AiRepository> {
  private final Provider<ApiService> apiProvider;

  public RepositoryModule_ProvideAiRepositoryFactory(Provider<ApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public AiRepository get() {
    return provideAiRepository(apiProvider.get());
  }

  public static RepositoryModule_ProvideAiRepositoryFactory create(
      Provider<ApiService> apiProvider) {
    return new RepositoryModule_ProvideAiRepositoryFactory(apiProvider);
  }

  public static AiRepository provideAiRepository(ApiService api) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideAiRepository(api));
  }
}
