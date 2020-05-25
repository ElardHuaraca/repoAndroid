package pe.com.tecsup.laboratorio08;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pe.com.tecsup.laboratorio08.models.ResponsePlace;
import pe.com.tecsup.laboratorio08.models.Result;
import pe.com.tecsup.laboratorio08.service.API;
import pe.com.tecsup.laboratorio08.service.ServiceGenerator;
import retrofit2.Call;
import android.util.Log;

import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        API client = ServiceGenerator.createService(API.class);
         Call<ResponsePlace> call = client.getDataMarkers("-12.046373, -77.042755","50000","restaurant","pollo%20a%20la%20brasa","AIzaSyAn8DpxSG8yU35XhtDeS5R_eMvBI8XXm2g");
        call.enqueue(new Callback<ResponsePlace>() {
            @Override
            public void onResponse(Call<ResponsePlace> call, Response<ResponsePlace> response) {
                for (Result result : response.body().getResults()){
                    LatLng markerLocation =new LatLng(
                            result.getGeometry().getLocation().getLat(),
                            result.getGeometry().getLocation().getLng());
                    String markerTitle = result.getName();

                    mMap.addMarker(new MarkerOptions().position(markerLocation).title(markerTitle));
                }
            }

            @Override
            public void onFailure(Call<ResponsePlace> call, Throwable t) {
                Toast.makeText(MapsActivity2.this,"No se pudieron encontrar las localizaciones",Toast.LENGTH_SHORT).show();
                Log.e(this.getClass().getName(),t.getMessage());
            }
        });
    }
}
