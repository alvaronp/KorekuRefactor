package es.unex.giiis.koreku.ui.service;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Factory method that allows us to create a ViewModel with a constructor that takes a
 * {@link ServiceRepository}
 */

public class ServiceViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final ServiceRepository mRepository;

    public ServiceViewModelFactory(ServiceRepository repository) {

        this.mRepository = repository;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        //noinspection unchecked
        return (T) new es.unex.giiis.koreku.ui.service.ServiceViewModel(mRepository);

    }


}
