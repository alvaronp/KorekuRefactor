package es.unex.giiis.koreku.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import es.unex.giiis.koreku.Consolas;
import es.unex.giiis.koreku.Perfil;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.databinding.FragmentConsoleBinding;
import es.unex.giiis.koreku.databinding.FragmentProfileBinding;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.ui.consoles.AddConsoles;
import es.unex.giiis.koreku.ui.consoles.ConsoleAdapter;

public class ProfileFragment extends Fragment {
    Button busqueda;
    private static final int ADD_Profile_REQUEST = 0;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ConsoleAdapter mAdapter;
    private FragmentProfileBinding binding;
    private static final int MENU_DELETE = Menu.FIRST;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        busqueda = (Button) root.findViewById(R.id.Busqueda);
        busqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // - Attach Listener to FloatingActionButton. Implement onClick()
                Intent intent = new Intent(getActivity(), BuscarPerfiles.class);
                startActivity(intent);

            }
        });
        KorekuDatabase.getInstance(getActivity());
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
        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, R.string.delete_profile);
    }

}