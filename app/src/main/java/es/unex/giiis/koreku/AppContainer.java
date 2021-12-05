package es.unex.giiis.koreku;

import android.content.Context;

import androidx.room.Database;

import es.unex.giiis.koreku.new_api.ProductNetworkDataSource;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.ui.consoles.ConsoleRepository;
import es.unex.giiis.koreku.ui.consoles.ConsoleViewModelFactory;
import es.unex.giiis.koreku.ui.profile.ProfileRepository;
import es.unex.giiis.koreku.ui.profile.ProfileViewModelFactory;
import es.unex.giiis.koreku.ui.consoles.ProductRepository;
import es.unex.giiis.koreku.ui.consoles.ProductViewModelFactory;

public class AppContainer {

    private KorekuDatabase database;
    public ConsoleRepository consolasrepo;
    public ConsoleViewModelFactory cfactory;
    private ProductNetworkDataSource networkDataSource;
    public ProductRepository repository;
    public ProductViewModelFactory afactory;
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
    public ProfileViewModelFactory pfactory;
    public AccesoriosViewModelFactory afactory;
    */


    public AppContainer(Context context){
        database = KorekuDatabase.getInstance(context);
        consolasrepo = ConsoleRepository.getInstance(database.getDao2());
        profilerepo=  ProfileRepository.getInstance(database.getDao3());
        cfactory = new ConsoleViewModelFactory(consolasrepo);
        networkDataSource = ProductNetworkDataSource.getInstance();
        repository = ProductRepository.getInstance(database.getDao5(), networkDataSource);
        afactory = new ProductViewModelFactory(repository);
        pfactory = new ProfileViewModelFactory(profilerepo);
        /*
        gfactory = new GamesViewModelFactory(gamesrepo);
        sfactory = new ServiceViewModelFactory(servicerepo);

        afactory = new AccesoriosViewModelFactory(accrepo);
        pfactory = new ProfileViewModelFactory(profilerepo);
        */
    }
}
