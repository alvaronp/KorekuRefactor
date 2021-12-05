package es.unex.giiis.koreku.new_api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.ui.games.AddGames;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductsNetworkLoaderRunnable implements Runnable{
    private final OnProductsLoadedListener mOnProductsLoadedListener;
    private final String mConsolename;
    private static String key;

    public ProductsNetworkLoaderRunnable(String consolename, OnProductsLoadedListener onProductsLoadedListener) {
        mOnProductsLoadedListener = onProductsLoadedListener;
        mConsolename = consolename + '\n';
    }
    @Override
    public void run() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pricecharting.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PriceChartingService service = retrofit.create(PriceChartingService.class);
        key = "c0b53bce27c1bdab90b1605249e600dc43dfd1d5";

        Call<ResultQuery> call = service.getProducts(key,mConsolename);
        try {
            Response<ResultQuery> response = call.execute();
            List<Product> products = response.body() == null ? new ArrayList<>() : response.body().getProductList();
            AppExecutors.getInstance().mainThread().execute(() -> mOnProductsLoadedListener.onProductsLoaded(products));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
