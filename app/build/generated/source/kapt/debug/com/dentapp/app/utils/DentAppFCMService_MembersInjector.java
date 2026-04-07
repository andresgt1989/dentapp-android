package com.dentapp.app.utils;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class DentAppFCMService_MembersInjector implements MembersInjector<DentAppFCMService> {
  private final Provider<TokenStore> tokenStoreProvider;

  public DentAppFCMService_MembersInjector(Provider<TokenStore> tokenStoreProvider) {
    this.tokenStoreProvider = tokenStoreProvider;
  }

  public static MembersInjector<DentAppFCMService> create(Provider<TokenStore> tokenStoreProvider) {
    return new DentAppFCMService_MembersInjector(tokenStoreProvider);
  }

  @Override
  public void injectMembers(DentAppFCMService instance) {
    injectTokenStore(instance, tokenStoreProvider.get());
  }

  @InjectedFieldSignature("com.dentapp.app.utils.DentAppFCMService.tokenStore")
  public static void injectTokenStore(DentAppFCMService instance, TokenStore tokenStore) {
    instance.tokenStore = tokenStore;
  }
}
