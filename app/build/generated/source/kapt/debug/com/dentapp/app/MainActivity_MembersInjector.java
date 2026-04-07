package com.dentapp.app;

import com.dentapp.app.utils.TokenStore;
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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<TokenStore> tokenStoreProvider;

  public MainActivity_MembersInjector(Provider<TokenStore> tokenStoreProvider) {
    this.tokenStoreProvider = tokenStoreProvider;
  }

  public static MembersInjector<MainActivity> create(Provider<TokenStore> tokenStoreProvider) {
    return new MainActivity_MembersInjector(tokenStoreProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectTokenStore(instance, tokenStoreProvider.get());
  }

  @InjectedFieldSignature("com.dentapp.app.MainActivity.tokenStore")
  public static void injectTokenStore(MainActivity instance, TokenStore tokenStore) {
    instance.tokenStore = tokenStore;
  }
}
