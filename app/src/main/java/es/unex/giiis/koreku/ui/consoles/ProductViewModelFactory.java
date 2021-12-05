package es.unex.giiis.koreku.ui.consoles;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Factory method that allows us to create a ViewModel with a constructor that takes a
 * {@link ProductRepository}
 */
public class ProductViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final ProductRepository mRepository;

    public ProductViewModelFactory(ProductRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new es.unex.giiis.koreku.ui.consoles.ProductViewModel(mRepository);
    }
}