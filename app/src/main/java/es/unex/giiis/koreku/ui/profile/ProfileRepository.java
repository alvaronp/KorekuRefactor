package es.unex.giiis.koreku.ui.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.roomdb.ConsolasDAO;
import es.unex.giiis.koreku.roomdb.PerfilDAO;
import es.unex.giiis.koreku.ui.consoles.Consolas;
import es.unex.giiis.koreku.ui.games.AddGames;


/**
 * Handles data operations in Sunshine {@link ConsolasDAO}
 */
public class ProfileRepository {
    private static final String LOG_TAG = ProfileRepository.class.getSimpleName();
    public static final Object lock = new Object();
    // For Singleton instantiation
    private static ProfileRepository sInstance;
    private final PerfilDAO mPerfilDAO;
    private   Perfil perfil=null;
    private final AppExecutors mExecutors = AppExecutors.getInstance();
    private LiveData<List<Perfil>> perfiles;
    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;

    private ProfileRepository(PerfilDAO perfilesDAO) {
        mPerfilDAO = perfilesDAO;
        perfiles = mPerfilDAO.getAll();
    }

    public synchronized static ProfileRepository getInstance(PerfilDAO dao) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new ProfileRepository(dao);
            Log.d(LOG_TAG, "Made new repository");
        }
        return sInstance;
    }

    private void doFetchConsoles(){
        Log.d(LOG_TAG, "Fetching Consoles from DB");
        AppExecutors.getInstance().diskIO().execute(() -> {
            perfiles = mPerfilDAO.getAll();
        });
    }

    /**
     * Database related operations
     **/

    public LiveData<List<Perfil>> getPerfiles() {
        doFetchConsoles();
        return perfiles;
    }

    public Perfil getPerfil(String nombre) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
             perfil=mPerfilDAO.getPerfil(nombre);
                synchronized (lock){
                    lock.notifyAll();
                }
            }
        });
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return perfil;
    }



    public void deleteAll(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mPerfilDAO.deleteAll();
            }
        });
    }

    public void delete(String nombre){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mPerfilDAO.deleteProfile(nombre);
            }
        });
    }

    public void insert(Perfil c){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mPerfilDAO.insert(c);
            }
        });
    }

    public void update(Perfil c){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mPerfilDAO.update(c);
            }
        });
    }
}
