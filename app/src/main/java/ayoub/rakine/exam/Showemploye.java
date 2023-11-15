package ayoub.rakine.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ayoub.rakine.exam.adapter.EmployeAdapter;
import ayoub.rakine.exam.beans.Employe;
import ayoub.rakine.exam.beans.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Showemploye extends AppCompatActivity {

    List<Employe> emplpoyes;
    private Spinner serviceSpinner;
    List<Service> services;
    private ListView listView;
    private EmployeAdapter employeAdpater;
    private RequestQueue requestQueue;
    private String loadUrl = "http://10.0.2.2:8080/api/employe";

    String insertUrl = "http://10.0.2.2:8080/api/employe";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showemploye);

        listView = findViewById(R.id.list);
        emplpoyes = new ArrayList<>();
        employeAdpater = new EmployeAdapter(emplpoyes, this);
        listView.setAdapter(employeAdpater);

        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, loadUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject employeJson = response.getJSONObject(i);
                                Employe employe = new Employe();
                                employe.setId(employeJson.getInt("id"));
                                employe.setNom(employeJson.getString("nom"));
                                employe.setPrenom(employeJson.getString("prenom"));

                                employe.setPhoto("photo");


                                if (employeJson.has("dateNaissance") && !employeJson.isNull("dateNaissance")) {

                                    String dateNaissanceString = employeJson.getString("dateNaissance");
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                    try {

                                        employe.setDateNaissance(sdf.parse(dateNaissanceString));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }

                                // Create Filiere object and set its details
                                JSONObject serviceJson = employeJson.getJSONObject("service");
                                Service service = new Service();
                                service.setId(serviceJson.getInt("id"));
                                service.setNom(serviceJson.getString("nom"));

                                employe.setService(service);

                                emplpoyes.add(employe);
                            }

                            employeAdpater.notifyDataSetChanged();
                            Log.d("List_employe", "Données chargées avec succès. Nombre d'employe : " + emplpoyes.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("List_employe", "Erreur lors de la récupération des données JSON : " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Showemploye.this, "Erreur de chargement des étudiants", Toast.LENGTH_SHORT).show();
                        Log.e("List_Students", "Erreur de réseau : " + error.getMessage());
                    }
                });

        requestQueue.add(request);


    }
}
