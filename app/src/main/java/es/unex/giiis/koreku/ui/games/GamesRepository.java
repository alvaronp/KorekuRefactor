package es.unex.giiis.koreku.ui.games;
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
import es.unex.giiis.koreku.roomdb.GamesDAO;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;


/**
 * Handles data operations in Sunshine {@link ConsolasDAO}
 */
public class GamesRepository {
    private static final String LOG_TAG = GamesRepository.class.getSimpleName();

    // For Singleton instantiation
    private static GamesRepository sInstance;
    private final GamesDAO mGamesDAO;
    private final AppExecutors mExecutors = AppExecutors.getInstance();
    private LiveData<List<Games>> games;
    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;

    public GamesRepository(GamesDAO gamesDAO) {
        mGamesDAO = gamesDAO;
        games = mGamesDAO.getAll();
    }

    public synchronized static GamesRepository getInstance(GamesDAO dao) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new GamesRepository(dao);
            Log.d(LOG_TAG, "Made new repository");
        }
        return sInstance;
    }

    private void doFetchGames(){
        Log.d(LOG_TAG, "Fetching Games from DB");
        AppExecutors.getInstance().diskIO().execute(() -> {
            games = mGamesDAO.getAll();
        });
    }

    /**
     * Database related operations
     **/

    public LiveData<List<Games>> getGames() {
        doFetchGames();
        return games;
    }

    public LiveData<List<Games>> getGamesByGenre(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                games = mGamesDAO.getAllByGender();
            }
        });
        return games;
    }

    public LiveData<List<Games>> getGamesByDate(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                games = mGamesDAO.getAllByDate();
            }
        });
        return games;
    }

    public void deleteAll(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mGamesDAO.deleteAll();
            }
        });
    }

    public void delete(String nombre){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mGamesDAO.deleteGame(nombre);
            }
        });
    }

    public void insert(Games c){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mGamesDAO.insert(c);
            }
        });
    }

    public void update(Games c){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mGamesDAO.update(c);
            }
        });
    }
}
