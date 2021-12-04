package es.unex.giiis.koreku.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.databinding.FragmentProfileBinding;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;

public class ProfileFragment extends Fragment {
    FloatingActionButton busqueda, anadirPerfil;
    private static final int ADD_Profile_REQUEST = 0;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProfileAdapter mAdapter;
    private FragmentProfileBinding binding;
    private static final int MENU_DELETE = Menu.FIRST;
    private static final int ADD_PROFILE_REQUEST = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        TransitionInflater tInf = TransitionInflater.from(this.getActivity());
        setExitTransition(tInf.inflateTransition(R.transition.fade_in));

        busqueda = (FloatingActionButton) root.findViewById(R.id.Busqueda);
        busqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // - Attach Listener to FloatingActionButton. Implement onClick()
                Intent intent = new Intent(getActivity(), BuscarPerfiles.class);
                startActivity(intent);

            }
        });




        anadirPerfil = (FloatingActionButton) root.findViewById(R.id.fab);
        anadirPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // - Attach Listener to FloatingActionButton. Implement onClick()
                Intent intent = new Intent(getActivity(), AddProfile.class);
                startActivityForResult(intent, ADD_PROFILE_REQUEST);

            }
        });
        mRecyclerView = root.findViewById(R.id.recyclerviewprofile);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // - Create a new Adapter for the RecyclerView
        mAdapter = new ProfileAdapter(getActivity(), new ProfileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Perfil x) {
                ProfileDetailFragment fragment = ProfileDetailFragment.newInstance(x);
                getFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, fragment)
                        .addToBackStack(null)  //permite mantener la navegacion del boton back
                        .commit();
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        KorekuDatabase.getInstance(getActivity());
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //  - Check result code and request code.
        // If user submitted a new ToDoItem
        // Create a new ToDoItem from the data Intent
        // and then add it to the adapter
        if (requestCode == ADD_PROFILE_REQUEST) {
            if (resultCode == getActivity().RESULT_OK) {
                Perfil c = new Perfil(data);

                //insert into DB
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        KorekuDatabase db = KorekuDatabase.getInstance(getActivity());
                        long id = db.getDao3().insert(c);

                        //update item ID
                        c.setId(id);

                        //insert into adapter list
                        getActivity().runOnUiThread(() -> mAdapter.add(c));
                    }
                });
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        // Load saved ToDoItems, if necessary

        if (mAdapter.getItemCount() == 0)
            loadItems();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_DELETE:
                //ToDoItemCRUD crud = ToDoItemCRUD.getInstance(this);
                //crud.deleteAll();
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        KorekuDatabase db = KorekuDatabase.getInstance(getActivity());
                        db.getDao3().deleteAll();
                        getActivity().runOnUiThread(() -> mAdapter.clear());
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Load stored ToDoItems
    private void loadItems() {
        //ToDoItemCRUD crud = ToDoItemCRUD.getInstance(this);
        //List<ToDoItem> items = crud.getAll();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List <Perfil> cons = KorekuDatabase.getInstance(getActivity()).getDao3().getAll();
                getActivity().runOnUiThread(()->mAdapter.load(cons));
            }
        });
    }
}