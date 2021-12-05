package es.unex.giiis.koreku.ui.profile;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Factory method that allows us to create a ViewModel with a constructor that takes a
 * {@link ProfileRepository}
 */
public class ProfileViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final ProfileRepository mRepository;

    public ProfileViewModelFactory(ProfileRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new ProfileViewModel(mRepository);
    }
}