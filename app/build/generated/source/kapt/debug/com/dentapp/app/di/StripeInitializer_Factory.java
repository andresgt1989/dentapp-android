package com.dentapp.app.di;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class StripeInitializer_Factory implements Factory<StripeInitializer> {
  private final Provider<Context> contextProvider;

  public StripeInitializer_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public StripeInitializer get() {
    return newInstance(contextProvider.get());
  }

  public static StripeInitializer_Factory create(Provider<Context> contextProvider) {
    return new StripeInitializer_Factory(contextProvider);
  }

  public static StripeInitializer newInstance(Context context) {
    return new StripeInitializer(context);
  }
}
