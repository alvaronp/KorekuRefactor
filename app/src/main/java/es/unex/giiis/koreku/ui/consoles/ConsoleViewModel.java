package es.unex.giiis.koreku.ui.consoles;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

/**
 * {@link ViewModel} for {@link ConsoleFragment}
 */
class ConsoleViewModel extends ViewModel {

    private final ConsoleRepository mRepository;
    private final LiveData<List<Consolas>> mConsoles;

    public ConsoleViewModel(ConsoleRepository repository) {
        mRepository = repository;
        mConsoles = mRepository.getConsoles();
    }

    public LiveData<List<Consolas>> getConsoles() {
        return mConsoles;
    }

    public LiveData<List<Consolas>> getConsolesByDate() {
        return mRepository.getConsolesByDate();
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void delete(String nombre){
        mRepository.delete(nombre);
    }

    public void insert (Consolas c){
        mRepository.insert(c);
    }

    public void update (Consolas c){
        mRepository.update(c);
    }
}
