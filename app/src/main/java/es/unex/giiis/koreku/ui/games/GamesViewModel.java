package es.unex.giiis.koreku.ui.games;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

/**
 * {@link ViewModel} for {@link GameFragment}
 */
public class GamesViewModel extends ViewModel {

    private final GamesRepository mRepository;
    private final LiveData<List<Games>> mGames;

    public GamesViewModel(GamesRepository repository) {
        mRepository = repository;
        mGames = mRepository.getGames();
    }

    public LiveData<List<Games>> getGames() {
        return mGames;
    }

    public LiveData<List<Games>> getGamesByGenre() {
        return mRepository.getGamesByGenre();
    }

    public LiveData<List<Games>> getGamesByDate() {
        return mRepository.getGamesByDate();
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void delete(String nombre){
        mRepository.delete(nombre);
    }

    public void insert (Games g){
        mRepository.insert(g);
    }

    public void update (Games g){
        mRepository.update(g);
    }

}
