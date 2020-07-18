package pe.edu.tecsup.motorizapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import pe.edu.tecsup.motorizapp.R;
import pe.edu.tecsup.motorizapp.service.OrderService;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Define el layout activity_home como interface de esta actividad
        setContentView(R.layout.activity_home);

        // Instanciamos el toolbar de la aplicación
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Habilitamos la librería de soporte para este componente
        setSupportActionBar(toolbar);

        // Instanciamos el drawer (menú lateral de la aplicación)
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        // Instanciamos el navigation view
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Agregamos cada id de menu de cada elemento del drawer
        // Asociamos el drawer con el AppBar de la aplicación
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_orders, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();

        // Instanciamos el controlador de navegación
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(new Intent(getBaseContext(), OrderService.class));
    }
}