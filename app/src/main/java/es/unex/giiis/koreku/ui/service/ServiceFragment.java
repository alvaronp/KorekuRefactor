package es.unex.giiis.koreku.ui.service;

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
import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.MyApplication;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.databinding.FragmentServiceBinding;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.ui.consoles.Consolas;

public class ServiceFragment extends Fragment {

    private FragmentServiceBinding binding;

    private static final int MENU_DELETE = Menu.FIRST;
    private static final int MENU_ListarFecha = 5;
    private static final int ADD_SERVICE_REQUEST = 0;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ServiceAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentServiceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        TransitionInflater tInf = TransitionInflater.from(this.getActivity());
        setExitTransition(tInf.inflateTransition(R.transition.fade_in));
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fragmentService_Button);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // - Attach Listener to FloatingActionButton. Implement onClick()

                Intent intent = new Intent(getActivity(), AddService.class);
                startActivityForResult(intent, ADD_SERVICE_REQUEST);

            }
        });

        mRecyclerView = (RecyclerView) root.findViewById(R.id.fragment_service_recycledView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // - Create a new Adapter for the RecyclerView

        mAdapter = new ServiceAdapter(getActivity(), new ServiceAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(Service service) {

                ServiceDetailFragment fragment = ServiceDetailFragment.newInstance(service);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main,
                        fragment).addToBackStack(null).commit();

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
        // If user submitted a new ToDoItem Create a new ToDoItem from the data Intent and then add it to the adapter

        if (requestCode == ADD_SERVICE_REQUEST) {

            if (resultCode == getActivity().RESULT_OK) {
                Service service = new Service(data);

                //insert into DB

                AppContainer appContainer = ((MyApplication) this.getActivity().getApplication()).appContainer;
                ServiceViewModel mViewModel = new ViewModelProvider(this, appContainer.sfactory).get(ServiceViewModel.class);
                mViewModel.insert(service);
                mAdapter.add(service);

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

        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, R.string.delete_services);
        menu.add(Menu.NONE, MENU_ListarFecha, Menu.NONE, R.string.list_by_duedate);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AppContainer appContainer = ((MyApplication) this.getActivity().getApplication()).appContainer;
        ServiceViewModel mViewModel = new ViewModelProvider(this, appContainer.sfactory).get(ServiceViewModel.class);

        switch (item.getItemId()) {

            case MENU_DELETE:

                mViewModel.deleteAll();
                mViewModel.getServices().observe(this, services -> {
                    mAdapter.load(services);
                });

                return true;

            case MENU_ListarFecha:

                List<Service> s = mViewModel.getServices().getValue();
                s.sort(new Comparator<Service>() {
                    @Override
                    public int compare(Service t1, Service t2) {
                        return t1.getDueDate().compareTo(t2.getDueDate());
                    }
                });
                mAdapter.load(s);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Load stored Services

    private void loadItems() {

        AppContainer appContainer = ((MyApplication) this.getActivity().getApplication()).appContainer;
        ServiceViewModel mViewModel = new ViewModelProvider(this, appContainer.sfactory).get(ServiceViewModel.class);
        mViewModel.getServices().observe(this, services -> {
            mAdapter.load(services);
        });

    }

}
