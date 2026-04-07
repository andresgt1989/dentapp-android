package com.dentapp.app.data.repository;

import com.dentapp.app.data.api.ApiService;
import com.dentapp.app.utils.TokenStore;
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
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<ApiService> apiProvider;

  private final Provider<TokenStore> tokenStoreProvider;

  public AuthRepository_Factory(Provider<ApiService> apiProvider,
      Provider<TokenStore> tokenStoreProvider) {
    this.apiProvider = apiProvider;
    this.tokenStoreProvider = tokenStoreProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(apiProvider.get(), tokenStoreProvider.get());
  }

  public static AuthRepository_Factory create(Provider<ApiService> apiProvider,
      Provider<TokenStore> tokenStoreProvider) {
    return new AuthRepository_Factory(apiProvider, tokenStoreProvider);
  }

  public static AuthRepository newInstance(ApiService api, TokenStore tokenStore) {
    return new AuthRepository(api, tokenStore);
  }
}
