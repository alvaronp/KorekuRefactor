package es.unex.giiis.koreku.api_product;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PriceChartingService {
    @GET("api/product")
    Call<Product> getProduct(@Query("t") String key ,@Query("q") String gametitle);
}
