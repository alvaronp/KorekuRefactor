package es.unex.giiis.koreku.ui.games;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Factory method that allows us to create a ViewModel with a constructor that takes a
 * {@link GamesRepository}
 */
public class GamesViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final GamesRepository mRepository;

    public GamesViewModelFactory(GamesRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new es.unex.giiis.koreku.ui.games.GamesViewModel(mRepository);
    }
}
