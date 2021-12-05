package es.unex.giiis.koreku.ui.service;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

/**
 * {@link ViewModel} for {@link ServiceFragment}
 */

public class ServiceViewModel extends ViewModel {

    private final ServiceRepository mRepository;
    private final LiveData<List<Service>> mServices;

    public ServiceViewModel(ServiceRepository repository) {

        mRepository = repository;
        mServices = mRepository.getService();

    }

    public LiveData<List<Service>> getServices() {

        return mServices;

    }

    public void delete(String title){

        mRepository.delete(title);

    }

    public void insert (Service service){

        mRepository.insert(service);

    }

    public void deleteAll(){

        mRepository.deleteAll();

    }

    public void update (Service service){

        mRepository.update(service);

    }


    public LiveData<List<Service>> getServicesByDueDate() {

        return mRepository.getServicesByDueDate();

    }

}
