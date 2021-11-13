package es.unex.giiis.koreku.ui.consoles;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.Consolas;
import es.unex.giiis.koreku.MainActivity;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.databinding.FragmentConsoleBinding;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;

public class ConsoleFragment extends Fragment {

    private FragmentConsoleBinding binding;
    private static final int MENU_DELETE = Menu.FIRST;
    private static final int ADD_CONSOLES_REQUEST = 0;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ConsoleAdapter mAdapter;


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
                startActivityForResult(intent, ADD_CONSOLES_REQUEST);
            }
        });

        mRecyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // - Create a new Adapter for the RecyclerView
        mAdapter = new ConsoleAdapter(getActivity(), new ConsoleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Consolas c) {
                Snackbar.make(getActivity().getCurrentFocus(), "Console " + c.getTitle() + " clicked", Snackbar.LENGTH_LONG).show();
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
        if (requestCode == ADD_CONSOLES_REQUEST) {
            if (resultCode == getActivity().RESULT_OK) {
                Consolas c = new Consolas(data);

                //insert into DB
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        KorekuDatabase db = KorekuDatabase.getInstance(getActivity());
                        long id = db.getDao2().insert(c);

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
        KorekuDatabase.getInstance(getActivity()).close();
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, R.string.delete_consoles);
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
                        db.getDao2().deleteAll();
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
                List <Consolas> cons = KorekuDatabase.getInstance(getActivity()).getDao2().getAll();
                getActivity().runOnUiThread(()->mAdapter.load(cons));
            }
        });
    }
}