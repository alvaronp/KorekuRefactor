package es.unex.giiis.koreku.api;

import java.io.IOException;
import java.util.List;

import es.unex.giiis.koreku.api.Product;
import es.unex.giiis.koreku.AppExecutors;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductsNetworkLoaderRunnable implements Runnable{
    private final OnProductsLoadedListener mOnProductsLoadedListener;
    private final String mTitle;

    public ProductsNetworkLoaderRunnable(String gametitle, OnProductsLoadedListener onProductsLoadedListener) {
        mOnProductsLoadedListener = onProductsLoadedListener;
        mTitle = gametitle;
    }
    @Override
    public void run() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pricecharting.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PriceChartingService service = retrofit.create(PriceChartingService.class);

        //FORMA ASINCRONA
        try {
            List<Product> products = service.listProducts(mTitle).execute().body();
            AppExecutors.getInstance().mainThread().execute(() -> mOnProductsLoadedListener.onProductsLoaded(products));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Llamada al Listener con los datos obtenidos
    }
}
