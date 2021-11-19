package es.unex.giiis.koreku.ui.games;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.Consolas;
import es.unex.giiis.koreku.Games;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.databinding.FragmentGameBinding;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.ui.games.AddGames;
import es.unex.giiis.koreku.ui.games.GameAdapter;
import es.unex.giiis.koreku.ui.games.GameDetailFragment;

public class GameFragment extends Fragment {

    private FragmentGameBinding binding;
    private static final int MENU_DELETE = Menu.FIRST;
    private static final int MENU_Listar = 4;
    private static final int MENU_ListarFecha = 5;
    private static final int ADD_GAMES_REQUEST = 0;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private GameAdapter mAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGameBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // - Attach Listener to FloatingActionButton. Implement onClick()
                Intent intent = new Intent(getActivity(), AddGames.class);
                startActivityForResult(intent, ADD_GAMES_REQUEST);
            }
        });

        mRecyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view_game);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // - Create a new Adapter for the RecyclerView
        mAdapter = new GameAdapter(getActivity(), new GameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Games g) {
                GameDetailFragment fragment = GameDetailFragment.newInstance(g);
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
        if (requestCode == ADD_GAMES_REQUEST) {
            if (resultCode == getActivity().RESULT_OK) {
                Games g = new Games(data);

                //insert into DB
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        KorekuDatabase db = KorekuDatabase.getInstance(getActivity());
                        long id = db.getDao1().insert(g);

                        //update item ID
                        g.setId(id);

                        //insert into adapter list
                        getActivity().runOnUiThread(() -> mAdapter.add(g));
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
        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, R.string.delete_games);
        menu.add(Menu.NONE, MENU_Listar, Menu.NONE, R.string.list_by_gender);
        menu.add(Menu.NONE, MENU_ListarFecha, Menu.NONE, R.string.list_by_date);
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
                        db.getDao1().deleteAll();
                        getActivity().runOnUiThread(() -> mAdapter.clear());
                    }
                });
                return true;
            case MENU_Listar:
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        KorekuDatabase db = KorekuDatabase.getInstance(getActivity());
                        List<Games>juegos =db.getDao1().getAllByGender();
                        getActivity().runOnUiThread(() -> mAdapter.load(juegos));
                    }
                });
                return true;
            case MENU_ListarFecha:
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        KorekuDatabase db = KorekuDatabase.getInstance(getActivity());
                        List<Games>juegos =db.getDao1().getAllByDate();
                        getActivity().runOnUiThread(() -> mAdapter.load(juegos));
                    }
                });
            default:
                return true;
        }
    }

    // Load stored Games
    private void loadItems() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Games> games = KorekuDatabase.getInstance(getActivity()).getDao1().getAll();
                getActivity().runOnUiThread(()->mAdapter.load(games));
            }
        });
    }
}