package es.unex.giiis.koreku.ui.consoles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import es.unex.giiis.koreku.AppContainer;
import es.unex.giiis.koreku.MyApplication;
import es.unex.giiis.koreku.R;

public class APISearch extends AppCompatActivity implements ProductsAdapter.OnListInteractionListener{
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProductsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_search);

        mRecyclerView = (RecyclerView) findViewById(R.id.productsList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // - Create a new Adapter for the RecyclerView
        mAdapter = new ProductsAdapter(new ArrayList<>(),this);
        mRecyclerView.setAdapter(mAdapter);
        EditText searchBox = findViewById(R.id.searchBox);
        Button searchButton = findViewById(R.id.searchButton);
        mProgressBar = findViewById(R.id.progressBar);

        //MainViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactory(this.getApplicationContext());
        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        ProductViewModel mViewModel = new ViewModelProvider(this, appContainer.afactory).get(ProductViewModel.class);
        mViewModel.getProducts().observe(this, products -> {
            mAdapter.swap(products);
            // Show the repo list or the loading screen based on whether the repos data exists and is loaded
            if (products != null && products.size() != 0) showProductsDataView();
            else showLoading();
        });
        searchButton.setOnClickListener(view -> mViewModel.setConsoleName(searchBox.getText().toString()));
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(mViewModel::onRefresh);
    }

    private void showLoading(){
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void showProductsDataView(){
        mProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onListInteraction(String url) {
        Uri webPage = Uri.parse(url);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webPage);
        startActivity(webIntent);
    }
}

