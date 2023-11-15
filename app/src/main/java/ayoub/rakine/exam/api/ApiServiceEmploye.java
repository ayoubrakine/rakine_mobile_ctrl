package ayoub.rakine.exam.api;

import java.util.List;

import ayoub.rakine.exam.beans.Employe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServiceEmploye {

    @GET("api/employe")
    Call<List<Employe>> getEmployes();
}
