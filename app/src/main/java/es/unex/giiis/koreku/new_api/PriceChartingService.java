package es.unex.giiis.koreku.new_api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PriceChartingService {
    @GET("api/products")
    Call<ResultQuery> getProducts(@Query("t") String key , @Query("q") String consolename);
}
