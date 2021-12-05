package es.unex.giiis.koreku.ui.consoles;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.new_api.Product;
import es.unex.giiis.koreku.new_api.ProductNetworkDataSource;
import es.unex.giiis.koreku.roomdb.ProductsDAO;


/**
 * Handles data operations in Sunshine. Acts as a mediator between {@link es.unex.giiis.koreku.new_api.ProductNetworkDataSource}
 * and {@link es.unex.giiis.koreku.roomdb.ProductsDAO}
 */
public class ProductRepository {
    private static final String LOG_TAG = ProductRepository.class.getSimpleName();
    // For Singleton instantiation
    private static ProductRepository sInstance;
    private final ProductsDAO mProductsDao;
    private final ProductNetworkDataSource mProductNetworkDataSource;
    private final AppExecutors mExecutors = AppExecutors.getInstance();
    private final MutableLiveData<String> userFilterLiveData = new MutableLiveData<>();
    private final Map<String, Long> lastUpdateTimeMillisMap = new HashMap<>();
    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;

    private ProductRepository(ProductsDAO productsDao, ProductNetworkDataSource productNetworkDataSource) {
        mProductsDao = productsDao;
        mProductNetworkDataSource = productNetworkDataSource;
        // LiveData that fetches repos from network
        LiveData<Product[]> networkData = mProductNetworkDataSource.getCurrentProducts();
        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        networkData.observeForever(newProductsFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Deleting cached repos of user
                if (newProductsFromNetwork.length > 0){
                    mProductsDao.deleteReposByUser(newProductsFromNetwork[0].getConsolename());
                }
                // Insert our new repos into local database
                mProductsDao.bulkInsert(Arrays.asList(newProductsFromNetwork));
                Log.d(LOG_TAG, "New values inserted in Room");
            });
        });
    }

    public synchronized static ProductRepository getInstance(ProductsDAO dao, ProductNetworkDataSource nds) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new ProductRepository(dao, nds);
            Log.d(LOG_TAG, "Made new repository");
        }
        return sInstance;
    }

    public void setConsoleName(final String consoleName){
        userFilterLiveData.setValue(consoleName);
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (isFetchNeeded(consoleName)) {
                doFetchRepos(consoleName);
            }
        });
    }

    public void doFetchRepos(String consoleName){
        Log.d(LOG_TAG, "Fetching Repos from Github");
        AppExecutors.getInstance().diskIO().execute(() -> {
            mProductsDao.deleteReposByUser(consoleName);
            mProductNetworkDataSource.fetchRepos(consoleName);
            lastUpdateTimeMillisMap.put(consoleName, System.currentTimeMillis());
        });
    }

    /**
     * Database related operations
     **/

    public LiveData<List<Product>> getCurrentProducts() {
        return Transformations.switchMap(userFilterLiveData, mProductsDao::getProductsByConsole);
    }

    /**
     * Checks if we have to update the repos data.
     * @return Whether a fetch is needed
     */
    private boolean isFetchNeeded(String consoleName) {
        Long lastFetchTimeMillis = lastUpdateTimeMillisMap.get(consoleName);
        lastFetchTimeMillis = lastFetchTimeMillis == null ? 0L : lastFetchTimeMillis;
        long timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis;
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS || mProductsDao.getNumberProductsByConsole(consoleName) == 0;
    }
}