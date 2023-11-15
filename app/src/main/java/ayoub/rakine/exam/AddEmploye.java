package ayoub.rakine.exam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ayoub.rakine.exam.api.ApiServiceService;
import ayoub.rakine.exam.beans.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.android.volley.Response;

public class AddEmploye extends AppCompatActivity implements View.OnClickListener{

List<Service> services;
private Spinner servicespinner;

private EditText nom, prenom;
private DatePicker dateNaissance;
private Button bnAdd;
private RequestQueue requestQueue;
private String insertUrl = "http://10.0.2.2:8080/api/employe";


@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employe);

        servicespinner = findViewById(R.id.service);

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);

        dateNaissance = findViewById(R.id.dateNaissance);

        bnAdd = findViewById(R.id.bnAdd);

        bnAdd.setOnClickListener(this);



        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        ApiServiceService apiServiceFiliere = retrofit.create(ApiServiceService.class);

        Call<List<Service>> callSpecialites = apiServiceFiliere.getServices();


        callSpecialites.enqueue(new Callback<List<Service>>() {
@Override
public void onResponse(Call<List<Service>> call, retrofit2.Response<List<Service>> response) {
        if (response.isSuccessful()) {
        services = response.body();
        String[] names = new String[services.size()];
        for (int i = 0; i < services.size(); i++) {
        names[i] = services.get(i).getNom();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddEmploye.this, android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        servicespinner.setAdapter(adapter);
        } else {
        Toast.makeText(AddEmploye.this, "Erreur de chargement des services", Toast.LENGTH_SHORT).show();
        }
        }

@Override
public void onFailure(Call<List<Service>> call, Throwable t) {
        Toast.makeText(AddEmploye.this, "Erreur de chargement des services", Toast.LENGTH_SHORT).show();
        }
        });
        }

@Override
public void onClick(View v) {
        if (validateFields()) {
        JSONObject jsonBody = new JSONObject();
        try {
        JSONObject serviceObject = new JSONObject();
        long selectedSpecialiteId = services.get(servicespinner.getSelectedItemPosition()).getId();
        //String selectedSpecialiteCode = specialites.get(specialitespinner.getSelectedItemPosition()).getCode();
        //String selectedSpecialiteLibelle = specialites.get(specialitespinner.getSelectedItemPosition()).getLibelle();
                serviceObject.put("id", selectedSpecialiteId);
        //specialiteObject.put("code", selectedSpecialiteCode);
        //specialiteObject.put("libelle", selectedSpecialiteLibelle);


        if (serviceObject == null) {
        Toast.makeText(getApplicationContext(), "dbab adob", Toast.LENGTH_LONG).show();
        }

        int year = dateNaissance.getYear();
        int month = dateNaissance.getMonth(); // Les mois commencent à partir de 0 (0 = janvier, 11 = décembre)
        int dayOfMonth = dateNaissance.getDayOfMonth();

        // Créez un objet Calendar et initialisez-le avec la date choisie
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        // Formatez la date au format "yyyy-MM-dd"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateNaissanceformated = sdf.format(calendar.getTime());



        jsonBody.put("nom", nom.getText().toString());
        jsonBody.put("prenom", prenom.getText().toString());
        jsonBody.put("service",serviceObject);
        jsonBody.put("dateNaissance",dateNaissanceformated);


        } catch (JSONException e) {
        e.printStackTrace();
        }
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
        insertUrl, jsonBody, new Response.Listener<JSONObject>() {
@Override
public void onResponse(JSONObject response) {
        Log.d("resultat", response + "");
        // Créez un AlertDialog de succès
        AlertDialog.Builder builder = new AlertDialog.Builder(AddEmploye.this);
        builder.setTitle("Succès");
        builder.setMessage("Ajout réussi");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {

        dialog.dismiss();

        nom.setText("");
        prenom.setText("");



        }
        });

        // Affichez le dialogue
        AlertDialog dialog = builder.create();
        dialog.show();
        }
        }, new Response.ErrorListener() {
@Override
public void onErrorResponse(VolleyError error) {
        Log.d("Erreur", error.toString());
        }
        });
        requestQueue.add(request);
        } else {
        Toast.makeText(AddEmploye.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }
        }

private boolean validateFields() {
        return     !nom.getText().toString().isEmpty()
        && !prenom.getText().toString().isEmpty();
        }
        }