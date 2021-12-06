package es.unex.giiis.koreku;

import android.content.Context;

import androidx.room.Database;

import es.unex.giiis.koreku.new_api.ProductNetworkDataSource;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.ui.consoles.ConsoleRepository;
import es.unex.giiis.koreku.ui.consoles.ConsoleViewModelFactory;
import es.unex.giiis.koreku.ui.service.ServiceRepository;
import es.unex.giiis.koreku.ui.service.ServiceViewModelFactory;
import es.unex.giiis.koreku.ui.profile.ProfileRepository;
import es.unex.giiis.koreku.ui.profile.ProfileViewModelFactory;
import es.unex.giiis.koreku.ui.consoles.ProductRepository;
import es.unex.giiis.koreku.ui.consoles.ProductViewModelFactory;

public class AppContainer {

    private KorekuDatabase database;
    public ConsoleRepository consolasrepo;
    public ServiceRepository servicerepo;

    public ConsoleViewModelFactory cfactory;
    private ProductNetworkDataSource networkDataSource;
    public ProductRepository repository;
    public ProductViewModelFactory afactory;
    public ServiceViewModelFactory sfactory;

    public ProfileRepository profilerepo;
    public ProfileViewModelFactory pfactory;
    /*
    private RepoNetworkDataSource networkDataSource;
    public accesoriosRepository accrepo;
    public GamesRepository gamesrepo;
    public ProfileRepository profilerepo;
    public ServiceRepository servicerepo;

     */

/*
    public GamesViewModelFactory gfactory;
    public ProfileViewModelFactory pfactory;
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
        servicerepo = ServiceRepository.getInstance(database.getDao4());
        sfactory = new ServiceViewModelFactory(servicerepo);
        pfactory = new ProfileViewModelFactory(profilerepo);
        /*
        gfactory = new GamesViewModelFactory(gamesrepo);
        pfactory = new ProfileViewModelFactory(profilerepo);
        sfactory = new ServiceViewModelFactory(servicerepo);

        afactory = new AccesoriosViewModelFactory(accrepo);
        pfactory = new ProfileViewModelFactory(profilerepo);
        */
    }
}
