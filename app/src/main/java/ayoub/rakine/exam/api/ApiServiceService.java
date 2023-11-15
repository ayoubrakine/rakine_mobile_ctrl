package ayoub.rakine.exam.api;

import java.util.List;

import ayoub.rakine.exam.beans.Service;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServiceService {

    @GET("api/service")
    Call<List<Service>> getServices();
}
