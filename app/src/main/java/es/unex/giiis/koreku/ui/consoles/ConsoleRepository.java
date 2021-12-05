package es.unex.giiis.koreku.ui.consoles;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.roomdb.ConsolasDAO;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;


/**
 * Handles data operations in Sunshine {@link ConsolasDAO}
 */
public class ConsoleRepository {
    private static final String LOG_TAG = ConsoleRepository.class.getSimpleName();

    // For Singleton instantiation
    private static ConsoleRepository sInstance;
    private final ConsolasDAO mConsolasDAO;
    private final AppExecutors mExecutors = AppExecutors.getInstance();
    private LiveData<List<Consolas>> consolas;
    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;

    private ConsoleRepository(ConsolasDAO consolasDAO) {
        mConsolasDAO = consolasDAO;
        consolas = mConsolasDAO.getAll();
    }

    public synchronized static ConsoleRepository getInstance(ConsolasDAO dao) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new ConsoleRepository(dao);
            Log.d(LOG_TAG, "Made new repository");
        }
        return sInstance;
    }

    private void doFetchConsoles(){
        Log.d(LOG_TAG, "Fetching Consoles from DB");
        AppExecutors.getInstance().diskIO().execute(() -> {
            consolas = mConsolasDAO.getAll();
        });
    }

    /**
     * Database related operations
     **/

    public LiveData<List<Consolas>> getConsoles() {
        doFetchConsoles();
        return consolas;
    }

    public LiveData<List<Consolas>> getConsolesByDate(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                consolas = mConsolasDAO.getAllByDate();
            }
        });
        return consolas;
    }

    public void deleteAll(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mConsolasDAO.deleteAll();
            }
        });
    }

    public void delete(String nombre){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mConsolasDAO.deleteConsole(nombre);
            }
        });
    }

    public void insert(Consolas c){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mConsolasDAO.insert(c);
            }
        });
    }

    public void update(Consolas c){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mConsolasDAO.update(c);
            }
        });
    }
}
