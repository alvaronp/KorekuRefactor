package es.unex.giiis.koreku.api;

import java.io.IOException;

import es.unex.giiis.koreku.api.Product;
import es.unex.giiis.koreku.AppExecutors;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductsNetworkLoaderRunnable implements Runnable{
    private final OnProductsLoadedListener mOnProductsLoadedListener;
    private final String mTitle;
    private static String key;
    public Product product;

    public ProductsNetworkLoaderRunnable(String gametitle, OnProductsLoadedListener onProductsLoadedListener) {
        mOnProductsLoadedListener = onProductsLoadedListener;
        mTitle = gametitle;
        product = new Product();
    }
    @Override
    public void run() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pricecharting.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PriceChartingService service = retrofit.create(PriceChartingService.class);
        key = "c0b53bce27c1bdab90b1605249e600dc43dfd1d5";

        //FORMA ASINCRONA
        try {
            product = service.getProduct(key, mTitle).execute().body();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Product getFoundProduct(){
        return product;
    }
}
