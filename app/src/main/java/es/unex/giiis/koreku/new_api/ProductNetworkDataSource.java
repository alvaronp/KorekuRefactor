package es.unex.giiis.koreku.new_api;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import es.unex.giiis.koreku.AppExecutors;

public class ProductNetworkDataSource {
    private static final String LOG_TAG = ProductNetworkDataSource.class.getSimpleName();
    private static ProductNetworkDataSource sInstance;

    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<Product[]> mDownloadedProducts;

    private ProductNetworkDataSource() {
        mDownloadedProducts = new MutableLiveData<>();
    }

    public synchronized static ProductNetworkDataSource getInstance() {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            sInstance = new ProductNetworkDataSource();
            Log.d(LOG_TAG, "Made new network data source");
        }
        return sInstance;
    }

    public LiveData<Product[]> getCurrentProducts() {
        return mDownloadedProducts;
    }

    /**
     * Gets the newest repos
     */
    public void fetchRepos(String consolename) {
        Log.d(LOG_TAG, "Fetch repos started");
        // Get data from network and pass it to LiveData
        AppExecutors.getInstance().networkIO().execute(new ProductsNetworkLoaderRunnable(consolename, new OnProductsLoadedListener() {
            @Override
            public void onProductsLoaded(List<Product> products) {
                mDownloadedProducts.postValue(products.toArray(new Product[0]));
            }
        }));
    }
}
