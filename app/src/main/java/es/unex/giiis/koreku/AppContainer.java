package es.unex.giiis.koreku;

import android.content.Context;

import es.unex.giiis.koreku.new_api.ProductNetworkDataSource;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.ui.games.GamesRepository;
import es.unex.giiis.koreku.ui.games.GamesViewModelFactory;
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
    private ProductNetworkDataSource networkDataSource;

    public ConsoleRepository consolasrepo;
    public ServiceRepository servicerepo;
    public GamesRepository gamesrepo;
    public GamesViewModelFactory gfactory;
    public ProfileRepository profilerepo;

    public ConsoleViewModelFactory cfactory;
    public ProductRepository repository;
    public ProductViewModelFactory afactory;
    public ServiceViewModelFactory sfactory;
    public ProfileViewModelFactory pfactory;


    public AppContainer(Context context){

        database = KorekuDatabase.getInstance(context);
        networkDataSource = ProductNetworkDataSource.getInstance();

        gamesrepo = GamesRepository.getInstance(database.getDao1());
        consolasrepo = ConsoleRepository.getInstance(database.getDao2());
        profilerepo=  ProfileRepository.getInstance(database.getDao3());
        servicerepo = ServiceRepository.getInstance(database.getDao4());
        repository = ProductRepository.getInstance(database.getDao5(), networkDataSource);

        cfactory = new ConsoleViewModelFactory(consolasrepo);
        afactory = new ProductViewModelFactory(repository);
        sfactory = new ServiceViewModelFactory(servicerepo);
        pfactory = new ProfileViewModelFactory(profilerepo);
        gfactory = new GamesViewModelFactory(gamesrepo);
    }
}
