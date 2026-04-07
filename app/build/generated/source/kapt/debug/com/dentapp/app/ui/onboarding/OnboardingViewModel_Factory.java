package com.dentapp.app.ui.onboarding;

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
public final class OnboardingViewModel_Factory implements Factory<OnboardingViewModel> {
  private final Provider<ApiService> apiProvider;

  private final Provider<TokenStore> tokenStoreProvider;

  public OnboardingViewModel_Factory(Provider<ApiService> apiProvider,
      Provider<TokenStore> tokenStoreProvider) {
    this.apiProvider = apiProvider;
    this.tokenStoreProvider = tokenStoreProvider;
  }

  @Override
  public OnboardingViewModel get() {
    return newInstance(apiProvider.get(), tokenStoreProvider.get());
  }

  public static OnboardingViewModel_Factory create(Provider<ApiService> apiProvider,
      Provider<TokenStore> tokenStoreProvider) {
    return new OnboardingViewModel_Factory(apiProvider, tokenStoreProvider);
  }

  public static OnboardingViewModel newInstance(ApiService api, TokenStore tokenStore) {
    return new OnboardingViewModel(api, tokenStore);
  }
}
