package es.unex.giiis.koreku.ui.consoles;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConsoleViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ConsoleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is console fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}