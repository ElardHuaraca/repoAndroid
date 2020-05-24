package pe.com.tecsup.laboratorio08.service;
import pe.com.tecsup.laboratorio08.models.ResponsePlace;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    @GET("nearbysearch/json")
    Call<ResponsePlace> getDataMarkers(@Query("location") String location,
                                       @Query("radius") String radius,
                                       @Query("type") String type,
                                       @Query("keyword") String keyword,
                                       @Query("key") String key);
}
