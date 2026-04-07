package com.dentapp.app.ui.subscriptions;

import com.dentapp.app.data.api.ApiService;
import com.dentapp.app.utils.TokenStore;
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
public final class SubscriptionViewModel_Factory implements Factory<SubscriptionViewModel> {
  private final Provider<ApiService> apiProvider;

  private final Provider<TokenStore> tokenStoreProvider;

  public SubscriptionViewModel_Factory(Provider<ApiService> apiProvider,
      Provider<TokenStore> tokenStoreProvider) {
    this.apiProvider = apiProvider;
    this.tokenStoreProvider = tokenStoreProvider;
  }

  @Override
  public SubscriptionViewModel get() {
    return newInstance(apiProvider.get(), tokenStoreProvider.get());
  }

  public static SubscriptionViewModel_Factory create(Provider<ApiService> apiProvider,
      Provider<TokenStore> tokenStoreProvider) {
    return new SubscriptionViewModel_Factory(apiProvider, tokenStoreProvider);
  }

  public static SubscriptionViewModel newInstance(ApiService api, TokenStore tokenStore) {
    return new SubscriptionViewModel(api, tokenStore);
  }
}
