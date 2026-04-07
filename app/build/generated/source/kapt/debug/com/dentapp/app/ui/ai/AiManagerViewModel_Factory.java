package com.dentapp.app.ui.ai;

import com.dentapp.app.data.repository.AiRepository;
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
public final class AiManagerViewModel_Factory implements Factory<AiManagerViewModel> {
  private final Provider<AiRepository> repoProvider;

  public AiManagerViewModel_Factory(Provider<AiRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public AiManagerViewModel get() {
    return newInstance(repoProvider.get());
  }

  public static AiManagerViewModel_Factory create(Provider<AiRepository> repoProvider) {
    return new AiManagerViewModel_Factory(repoProvider);
  }

  public static AiManagerViewModel newInstance(AiRepository repo) {
    return new AiManagerViewModel(repo);
  }
}
