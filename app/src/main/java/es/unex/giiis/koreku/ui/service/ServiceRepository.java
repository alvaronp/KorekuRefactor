package es.unex.giiis.koreku.ui.service;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.roomdb.ServiceDAO;

/**
 * Handles data operations in Sunshine {@link ServiceDAO}
 */

public class ServiceRepository {

    private static final String LOG_TAG = ServiceRepository.class.getSimpleName();

    // For Singleton instantiation

    private static ServiceRepository sInstance;
    private final ServiceDAO mServiceDAO;
    private final AppExecutors mExecutors = AppExecutors.getInstance();
    private LiveData<List<Service>> service;

    private ServiceRepository(ServiceDAO serviceDAO) {

        mServiceDAO = serviceDAO;
        service = mServiceDAO.getAll();

    }

    public synchronized static ServiceRepository getInstance(ServiceDAO dao) {

        Log.d(LOG_TAG, "Getting the repository");

        if (sInstance == null) {

            sInstance = new ServiceRepository(dao);
            Log.d(LOG_TAG, "Made new repository");

        }

        return sInstance;

    }

    private void doFetchService(){

        Log.d(LOG_TAG, "Fetching Consoles from DB");

        AppExecutors.getInstance().diskIO().execute(() -> {
            service = mServiceDAO.getAll();
        });

    }


    /**
     * Database related operations
     **/

    public LiveData<List<Service>> getService() {

        doFetchService();
        return service;

    }

    public void delete(String title){

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                mServiceDAO.deleteService(title);

            }
        });
    }

    public void insert(Service service){

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                mServiceDAO.insert(service);

            }
        });
    }

    public void deleteAll(){

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                mServiceDAO.deleteAll();

            }
        });
    }

    public void update(Service service){

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                mServiceDAO.update(service);

            }
        });
    }

    public LiveData<List<Service>> getServicesByDueDate(){

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                service = mServiceDAO.getAllByDueDate();

            }
        });

        return service;

    }

}
