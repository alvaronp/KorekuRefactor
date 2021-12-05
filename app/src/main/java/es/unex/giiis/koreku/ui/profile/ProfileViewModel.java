package es.unex.giiis.koreku.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.koreku.ui.consoles.Consolas;
import es.unex.giiis.koreku.ui.consoles.ConsoleFragment;

/**
 * {@link ViewModel} for {@link ConsoleFragment}
 */
class ProfileViewModel extends ViewModel {

    private final ProfileRepository mRepository;
    private final LiveData<List<Perfil>> mPerfiles;

    public ProfileViewModel(ProfileRepository repository) {
        mRepository = repository;
        mPerfiles = mRepository.getPerfiles();
    }

    public LiveData<List<Perfil>> getPerfiles() {
        return mPerfiles;
    }

    public Perfil getPerfil(String nombre) {
        Perfil devuelta=mRepository.getPerfil(nombre);
        return devuelta;
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void delete(String nombre){
        mRepository.delete(nombre);
    }

    public void insert (Perfil c){
        mRepository.insert(c);
    }

    public void update (Perfil c){
        mRepository.update(c);
    }
}
