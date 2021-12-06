package es.unex.giiis.koreku.ui.games;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Comparator;
import java.util.List;

import es.unex.giiis.koreku.AppContainer;
import es.unex.giiis.koreku.MyApplication;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.databinding.FragmentGameBinding;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.ui.consoles.Consolas;
import es.unex.giiis.koreku.ui.consoles.ConsoleViewModel;
import es.unex.giiis.koreku.ui.consoles.ConsoleViewModelFactory;

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
        TransitionInflater tInf = TransitionInflater.from(this.getActivity());
        setExitTransition(tInf.inflateTransition(R.transition.fade_in));

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
                AppContainer appContainer = ((MyApplication) this.getActivity().getApplication()).appContainer;
                GamesViewModel mViewModel = new ViewModelProvider(this, appContainer.gfactory).get(GamesViewModel.class);
                mViewModel.insert(g);
                mAdapter.add(g);
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
        AppContainer appContainer = ((MyApplication) this.getActivity().getApplication()).appContainer;
        GamesViewModel mViewModel = new ViewModelProvider(this, appContainer.gfactory).get(GamesViewModel.class);
        switch (item.getItemId()) {
            case MENU_DELETE:
                mViewModel.deleteAll();
                mViewModel.getGames().observe(this, games -> {
                    mAdapter.load(games);
                });
                return true;
            case MENU_Listar:
                List<Games> g1 = mViewModel.getGames().getValue();
                g1.sort(new Comparator<Games>() {
                    @Override
                    public int compare(Games t1, Games t2) {
                        return t1.getGenero().compareTo(t2.getGenero());
                    }
                });
                mAdapter.load(g1);
                return true;
            case MENU_ListarFecha:
                List<Games> g2 = mViewModel.getGames().getValue();
                g2.sort(new Comparator<Games>() {
                    @Override
                    public int compare(Games t1, Games t2) {
                        return t1.getBuydate().compareTo(t2.getBuydate());
                    }
                });
                mAdapter.load(g2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Load stored Game
    private void loadItems() {
        AppContainer appContainer = ((MyApplication) this.getActivity().getApplication()).appContainer;
        GamesViewModel mViewModel = new ViewModelProvider(this, appContainer.gfactory).get(GamesViewModel.class);
        mViewModel.getGames().observe(this, games -> {
            mAdapter.load(games);
        });
    }
}