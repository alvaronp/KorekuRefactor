package es.unex.giiis.koreku;

import android.content.Context;

import androidx.room.Database;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.ui.consoles.ConsoleRepository;
import es.unex.giiis.koreku.ui.consoles.ConsoleViewModelFactory;
import es.unex.giiis.koreku.ui.profile.ProfileRepository;
import es.unex.giiis.koreku.ui.profile.ProfileViewModelFactory;

public class AppContainer {

    private KorekuDatabase database;
    public ConsoleRepository consolasrepo;
    public ConsoleViewModelFactory cfactory;
    public ProfileRepository profilerepo;
    public ProfileViewModelFactory pfactory;
    /*
    private RepoNetworkDataSource networkDataSource;
    public accesoriosRepository accrepo;
    public GamesRepository gamesrepo;
    public ServiceRepository servicerepo;

     */

/*
    public GamesViewModelFactory gfactory;
    public ServiceViewModelFactory sfactory;
    */


     /*
    public AccesoriosViewModelFactory afactory;
    */


    public AppContainer(Context context){
        database = KorekuDatabase.getInstance(context);
        consolasrepo = ConsoleRepository.getInstance(database.getDao2());
        profilerepo=  ProfileRepository.getInstance(database.getDao3());
        cfactory = new ConsoleViewModelFactory(consolasrepo);
        pfactory = new ProfileViewModelFactory(profilerepo);
        /*
        networkDataSource = RepoNetworkDataSource.getInstance();
        repository = accesoriosRepository.getInstance(database.accesoriosDao(), networkDataSource);
        gfactory = new GamesViewModelFactory(gamesrepo);
        sfactory = new ServiceViewModelFactory(servicerepo);

        afactory = new AccesoriosViewModelFactory(accrepo);
        */
    }
}
