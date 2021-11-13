package es.unex.giiis.koreku.ui.games;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.databinding.FragmentGameBinding;

public class GameFragment extends Fragment {

    private FragmentGameBinding binding;
    private static final int MENU_DELETE = Menu.FIRST;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGameBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGames;
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, R.string.delete_games);
    }
}