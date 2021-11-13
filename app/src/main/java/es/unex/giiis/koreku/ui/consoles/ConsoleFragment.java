package es.unex.giiis.koreku.ui.consoles;

import android.content.Intent;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.databinding.FragmentConsoleBinding;

public class ConsoleFragment extends Fragment {

    private FragmentConsoleBinding binding;
    private static final int MENU_DELETE = Menu.FIRST;
    private static final int ADD_CONSOLES_REQUEST = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConsoleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // - Attach Listener to FloatingActionButton. Implement onClick()
                Intent intent = new Intent(getActivity(), AddConsoles.class);
                startActivityForResult(intent,ADD_CONSOLES_REQUEST);
            }
        });

        final TextView textView = binding.textConsole;
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
        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, R.string.delete_consoles);
    }
}