package es.unex.giiis.koreku.ui.consoles;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.koreku.new_api.Product;

/**
 * {@link ViewModel} for {@link APISearch}
 */
class ProductViewModel extends ViewModel {

    private final ProductRepository mRepository;
    private final LiveData<List<Product>> mProducts;
    private String mConsoleName = "";

    public ProductViewModel(ProductRepository repository) {
        mRepository = repository;
        mProducts = mRepository.getCurrentProducts();
    }

    public void setConsoleName(String consoleName){
        mConsoleName = consoleName;
        mRepository.setConsoleName(consoleName);
    }

    public void onRefresh() {
        mRepository.doFetchRepos(mConsoleName);
    }

    public LiveData<List<Product>> getProducts() {
        return mProducts;
    }


}
