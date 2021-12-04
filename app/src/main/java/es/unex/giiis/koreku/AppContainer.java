package es.unex.giiis.koreku;

import android.content.Context;

import androidx.room.Database;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.ui.consoles.ConsoleRepository;
import es.unex.giiis.koreku.ui.consoles.ConsoleViewModelFactory;

public class AppContainer {

    private KorekuDatabase database;
    public ConsoleRepository consolasrepo;
    public ConsoleViewModelFactory cfactory;

    /*
    private RepoNetworkDataSource networkDataSource;
    public accesoriosRepository accrepo;
    public GamesRepository gamesrepo;
    public ServiceRepository servicerepo;
    public ProfileRepository profilerepo;

    public GamesViewModelFactory gfactory;
    public ServiceViewModelFactory sfactory;
    public ProfileViewModelFactory pfactory;
    public AccesoriosViewModelFactory afactory;
    */


    public AppContainer(Context context){
        database = KorekuDatabase.getInstance(context);
        consolasrepo = ConsoleRepository.getInstance(database.getDao2());
        cfactory = new ConsoleViewModelFactory(consolasrepo);
        /*
        networkDataSource = RepoNetworkDataSource.getInstance();
        repository = accesoriosRepository.getInstance(database.accesoriosDao(), networkDataSource);
        gfactory = new GamesViewModelFactory(gamesrepo);
        sfactory = new ServiceViewModelFactory(servicerepo);
        pfactory = new ProfileViewModelFactory(profilerepo);
        afactory = new AccesoriosViewModelFactory(accrepo);
        */
    }
}
