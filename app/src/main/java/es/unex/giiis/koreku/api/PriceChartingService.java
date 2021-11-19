package es.unex.giiis.koreku.api;


import java.util.List;

import es.unex.giiis.koreku.api.Product;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PriceChartingService {
    @GET("api/products?t=c0b53bce27c1bdab90b1605249e600dc43dfd1d5&q={gametitle}")
    Call<List<Product>> listProducts(@Path("gametitle") String gametitle);
}
