package es.unex.giiis.koreku.api;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import es.unex.giiis.koreku.AppExecutors;

public class ProductsLoaderRunnable implements Runnable{
    private final InputStream mInFile;
    private final OnProductsLoadedListener mOnProductsLoadedListener;

    public ProductsLoaderRunnable(InputStream inFile, OnProductsLoadedListener onProductsLoadedListener){
        mInFile = inFile;
        mOnProductsLoadedListener = onProductsLoadedListener;
    }
    @Override
    public void run() {
        // Obtención de los datos a partir del InputStream
        // Parse json file into JsonReader
        JsonReader reader = new JsonReader(new InputStreamReader(mInFile));
        // Parse JsonReader into list of Repo using Gson
        Product product = new Gson().fromJson(reader, Product.class);

        // Llamada al Listener con los datos obtenidos
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                mOnProductsLoadedListener.onProductsLoaded(product);
            }
        });
    }
}
