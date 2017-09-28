package com.xkoders.zuncallandroid.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xkoders.zuncallandroid.R;
import com.xkoders.zuncallandroid.models.Call;

public class DetailCallActivity extends AppCompatActivity {
    private static final String CONTACT_NAME = "com.xkoders.zuncallandroid.contact_name";
    private static final String CONTACT_CALL_COST = "com.xkoders.zuncallandroid.contact_call_cost";
    private static final String CONTACT_PHONE_NUMBER = "com.xkoders.zuncallandroid.contact_phone_number";
    private static final String CALL_DATE = "com.xkoders.zuncallandroid.contact_date";
    private static final String CONTACT_CALL_DURATION = "com.xkoders.zuncallandroid.contact_call_duration";
    private static final String CONTACT_AVATAR = "com.xkoders.zuncallandroid.contact_avatar";

    private TextView phoneNumberTextView;
    private TextView callCostTextView;
    private TextView callDurationTextView;

    /**
     * Inicia una nueva instancia de la actividad
     *
     * @param activity Contexto desde donde se lanzará
     * @param title    Item a procesar
     */
    public static void createInstance(Activity activity, Call title) {
        Intent intent = getLaunchIntent(activity, title);
        activity.startActivity(intent);
    }

    /**
     * Construye un Intent a partir del contexto y la actividad
     * de detalle.
     *
     * @param context Contexto donde se inicia
     * @param call    Identificador de la chica
     * @return Intent listo para usar
     */
    public static Intent getLaunchIntent(Context context, Call call) {
        Intent intent = new Intent(context, DetailCallActivity.class);
        intent.putExtra(CONTACT_NAME, call.getName());
        intent.putExtra(CONTACT_CALL_COST, call.getCost());
        intent.putExtra(CONTACT_PHONE_NUMBER, call.getDigitos());
        intent.putExtra(CALL_DATE, call.getFecha());
        intent.putExtra(CONTACT_CALL_DURATION, call.getDuration());
        intent.putExtra(CONTACT_AVATAR, call.getIdImagen());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWidgetsReferences();

        setToolbar();// Añadir action bar
        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String name = i.getStringExtra(CONTACT_NAME);
        String phoneNumber = i.getStringExtra(CONTACT_PHONE_NUMBER);
        String callCost = i.getStringExtra(CONTACT_CALL_COST);
        String callDuration = i.getStringExtra(CONTACT_CALL_DURATION);
       // int idDrawable = i.getIntExtra(CONTACT_AVATAR, -1);

        CollapsingToolbarLayout collapser =
                (CollapsingToolbarLayout) findViewById(R.id.collapser);

        if (collapser != null)
            collapser.setTitle(name); // Cambiar título

        //loadImageParallax(idDrawable);// Cargar Imagen

        // Setear escucha al FAB
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null)
            fab.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showSnackBar("Opción de Chatear");
                        }
                    }
            );


        phoneNumberTextView.setText(phoneNumber);
        callCostTextView.setText(callCost+" cuc");
        callDurationTextView.setText(callDuration+" min");


    }

    private void setToolbar() {
        // Añadir la Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
    }

    /**
     * Se carga una imagen aleatoria para el detalle
     */
    private void loadImageParallax(int id) {
        ImageView image = (ImageView) findViewById(R.id.image_paralax);
        // Usando Glide para la carga asíncrona
        Glide.with(this)
                .load(id)
                .centerCrop()
                .into(image);
    }

    /**
     * Proyecta una {@link Snackbar} con el string usado
     *
     * @param msg Mensaje
     */
    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                showSnackBar("Se abren los ajustes");
                return true;
            case R.id.action_add:
                showSnackBar("Añadir a contactos");
                return true;
            case R.id.action_favorite:
                showSnackBar("Añadir a favoritos");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public void getWidgetsReferences() {
        phoneNumberTextView = (TextView) findViewById(R.id.contact_phone_number_detail);
        callCostTextView = (TextView) findViewById(R.id.contact_call_cost_detail);
        callDurationTextView = (TextView) findViewById(R.id.contact_call_duration_detail);
    }

}
