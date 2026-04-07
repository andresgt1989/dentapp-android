package com.dentapp.app.di;

import android.content.Context;
import com.dentapp.app.utils.TokenStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AppModule_ProvideTokenStoreFactory implements Factory<TokenStore> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideTokenStoreFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public TokenStore get() {
    return provideTokenStore(contextProvider.get());
  }

  public static AppModule_ProvideTokenStoreFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideTokenStoreFactory(contextProvider);
  }

  public static TokenStore provideTokenStore(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideTokenStore(context));
  }
}
