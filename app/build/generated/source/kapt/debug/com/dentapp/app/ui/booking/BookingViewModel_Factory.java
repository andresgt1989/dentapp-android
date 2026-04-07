package com.dentapp.app.ui.booking;

import com.dentapp.app.data.api.ApiService;
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
public final class BookingViewModel_Factory implements Factory<BookingViewModel> {
  private final Provider<ApiService> apiProvider;

  public BookingViewModel_Factory(Provider<ApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public BookingViewModel get() {
    return newInstance(apiProvider.get());
  }

  public static BookingViewModel_Factory create(Provider<ApiService> apiProvider) {
    return new BookingViewModel_Factory(apiProvider);
  }

  public static BookingViewModel newInstance(ApiService api) {
    return new BookingViewModel(api);
  }
}
