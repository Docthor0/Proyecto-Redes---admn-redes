package mx.edu.itsuruapan.admnistracionderedes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class JPlanes extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    Button BAtras;
    Button BBuscar;
    Button BNuevo;
    Button BLista;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ArrayList<ListElementListar> elements = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_j_planes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        BAtras =(Button)findViewById(R.id.botonReturnP);
        BBuscar=(Button)findViewById(R.id.botonBuscarP);
        BNuevo=(Button)findViewById(R.id.botonAñadirP);
        BLista=(Button)findViewById(R.id.botonListaP);


        BNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JPlanes.this, JaddPlanes.class);
                startActivity(intent);
            }
        });


        BBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JPlanes.this, JresultadosPlanes.class);
                startActivity(intent);
            }
        });

        BAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JPlanes.this,menu.class);
                startActivity(intent);
            }
        });

        buscarPlan();


    }


    public void buscarPlan(){

        String url = "https://la-wea-redes.000webhostapp.com/hshaPHPs/buscarListarPlan.php";

        request = Volley.newRequestQueue(getApplicationContext());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("plan");
        JSONObject object;

        for(int i = 0; i<json.length();i++){
            try{
                object = Objects.requireNonNull(json).getJSONObject(i);
                elements.add(new ListElementListar(object.optString("id_Plan"),object.optString("nombre"),object.optString("descripcion"),object.optString("cantidad")));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ListAdapterListarPlanes ListAdapterListarPlanes = new ListAdapterListarPlanes(elements,this);
        RecyclerView recyclerView = findViewById(R.id.recVIPlanes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ListAdapterListarPlanes);

    }


}