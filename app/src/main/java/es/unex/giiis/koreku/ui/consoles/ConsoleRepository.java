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


/**
 * Handles data operations in Sunshine {@link ConsolasDAO}
 */
public class ConsoleRepository {
    private static final String LOG_TAG = ConsoleRepository.class.getSimpleName();

    // For Singleton instantiation
    private static ConsoleRepository sInstance;
    private final ConsolasDAO mConsolasDAO;
    private final AppExecutors mExecutors = AppExecutors.getInstance();
    private final MutableLiveData<String> userFilterLiveData = new MutableLiveData<>();
    private final Map<String, Long> lastUpdateTimeMillisMap = new HashMap<>();
    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;

    private ConsoleRepository(ConsolasDAO consolasDAO) {
        mConsolasDAO = consolasDAO;
        // LiveData that fetches repos from network
        LiveData<List<Consolas>> allData = consolasDAO.getAll();
        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        allData.observeForever(newConsolasFromDB -> {
            mExecutors.diskIO().execute(() -> {
                // Deleting cached repos of user
                if (newConsolasFromDB.size() > 0){
                    mConsolasDAO.deleteConsole(newConsolasFromDB.get(0).getTitle());
                }
                // Insert our new repos into local database
                mConsolasDAO.bulkInsert(newConsolasFromDB);
                Log.d(LOG_TAG, "New values inserted in Room");
            });
        });
    }
}
