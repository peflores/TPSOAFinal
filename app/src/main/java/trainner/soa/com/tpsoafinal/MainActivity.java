package trainner.soa.com.tpsoafinal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import trainner.soa.com.tpsoafinal.entidad.Arduino;
import trainner.soa.com.tpsoafinal.interfaces.ClienteService;

public class MainActivity extends AppCompatActivity {

    private Retrofit srvArduino;

    private Button empezar;

    private EditText txtIp;

    private EditText txtPuerto;

    private StringBuilder dirHost;

    private TextView temperatura;

    private TextView luz;

    private void inicializar(){
        empezar  = (Button) findViewById(R.id.btnEmpezar);
        txtIp = (EditText) findViewById(R.id.txtIp);
        txtPuerto = (EditText) findViewById(R.id.txtPuerto);
        temperatura = (TextView) findViewById(R.id.temperatura);
        luz = (TextView) findViewById(R.id.luz);
        dirHost = new StringBuilder();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializar();

        //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.5.160.125:8080").build();
        //ClienteService servicio =   retrofit.create(ClienteService.class);
        empezar.setOnClickListener(listernerEmpezar());
        //servicio.getEstadoLuz(callBackEstado());
    }

    @NonNull
    private View.OnClickListener listernerEmpezar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Editable ip = txtIp.getText();
              Editable puerto = txtPuerto.getText();
              dirHost.append("http://")
                      .append(ip.toString()).append(":")
              .append(puerto);
                //.append("/SoaRest/")
                srvArduino = new Retrofit.Builder().baseUrl(dirHost.toString())
                        .addConverterFactory(GsonConverterFactory.create() ).build();
                ClienteService servicio =   srvArduino.create(ClienteService.class);

                Call<Arduino>  resultao =  servicio.getEstadoLuz();
                resultao.enqueue(callBackEstado());
                dirHost.delete(0,dirHost.length());
            }
        };
    }

    @NonNull
    private Callback<Arduino> callBackEstado() {
        return new Callback<Arduino>() {
            @Override
            public void onResponse(Call<Arduino> call, Response<Arduino> response) {
                Arduino placa = response.body();
                luz.setText(placa.getCorte());
                temperatura.setText(placa.getTemp().toString());

            }

            @Override
            public void onFailure(Call<Arduino> call, Throwable throwable) {

            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
