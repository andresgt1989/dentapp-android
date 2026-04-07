package com.dentapp.app.di;

import com.dentapp.app.data.api.ApiService;
import com.dentapp.app.data.repository.AuthRepository;
import com.dentapp.app.utils.TokenStore;
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
public final class RepositoryModule_ProvideAuthRepositoryFactory implements Factory<AuthRepository> {
  private final Provider<ApiService> apiProvider;

  private final Provider<TokenStore> tokenStoreProvider;

  public RepositoryModule_ProvideAuthRepositoryFactory(Provider<ApiService> apiProvider,
      Provider<TokenStore> tokenStoreProvider) {
    this.apiProvider = apiProvider;
    this.tokenStoreProvider = tokenStoreProvider;
  }

  @Override
  public AuthRepository get() {
    return provideAuthRepository(apiProvider.get(), tokenStoreProvider.get());
  }

  public static RepositoryModule_ProvideAuthRepositoryFactory create(
      Provider<ApiService> apiProvider, Provider<TokenStore> tokenStoreProvider) {
    return new RepositoryModule_ProvideAuthRepositoryFactory(apiProvider, tokenStoreProvider);
  }

  public static AuthRepository provideAuthRepository(ApiService api, TokenStore tokenStore) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideAuthRepository(api, tokenStore));
  }
}
