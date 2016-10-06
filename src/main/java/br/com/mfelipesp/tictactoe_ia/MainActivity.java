package br.com.mfelipesp.tictactoe_ia;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import br.com.mfelipesp.tictactoe_ia.events.SpinnerDificuldadeSelectedListener;
import br.com.mfelipesp.tictactoe_ia.motorInferencia.MotorInferencia;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioTipoJogadoGroup;
    private Spinner spinnerDificuldade;
    private TextView txtDificuldade;
    private FloatingActionButton fab;
    private String tipoJogador = PLAYER;
    public static final String PLAYER = "PLAYER";
    public static final String CPU = "CPU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.fab = (FloatingActionButton) findViewById(R.id.fab);


        addListenerRadioButton();
        addSpinnerDificuldade();
        addListenerFab();


    }

    public void addSpinnerDificuldade(){
        this.txtDificuldade = (TextView) findViewById(R.id.txtDificuldade);
        this.spinnerDificuldade = (Spinner) findViewById(R.id.spinnerDificuldade);
        spinnerDificuldade.setOnItemSelectedListener(new SpinnerDificuldadeSelectedListener());
    }

    public void addListenerFab(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Preparece-se para jogar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                goPlayerActivity();


            }
        });
    }

    public void goPlayerActivity(){
        Intent myIntent = new Intent(this, PlayerCPUActivity.class);
        myIntent.putExtra(PlayerCPUActivity.EXTRA_TIPO_JOGADOR, tipoJogador);
        myIntent.putExtra(PlayerCPUActivity.EXTRA_DIFICULDADE, getDificuldade());
        startActivity(myIntent);
    }

    public void showDificuldade(){
        this.txtDificuldade.setEnabled(true);
        this.spinnerDificuldade.setEnabled(true);
    }

    public void hideDificuldade(){
        this.txtDificuldade.setEnabled(false);
        this.spinnerDificuldade.setEnabled(false);
    }

    public void addListenerRadioButton(){
        radioTipoJogadoGroup = (RadioGroup) findViewById(R.id.radioTipoJogada);
        radioTipoJogadoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selecionado = radioTipoJogadoGroup.getCheckedRadioButtonId();
                RadioButton radioTipoJogadoButton = (RadioButton) findViewById(selecionado);

                if (radioTipoJogadoButton.getId() == R.id.radioPlayer) {
                    tipoJogador = PLAYER;
                    showDificuldade();
                } else {
                    tipoJogador = CPU;
                    hideDificuldade();
                }

            }
        });
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
            MotorInferencia.getInstancie(this).lerJogadasRealizadas();
            startActivity(new Intent(this, JogadasListActivity.class));
            return true;
        } else if (id == R.id.action_reset){
            MotorInferencia.getInstancie(this).resetarJogadasRealizadas();
        }

        return super.onOptionsItemSelected(item);
    }

    public String getDificuldade() {

        if (spinnerDificuldade.isEnabled()){
            return String.valueOf(spinnerDificuldade.getSelectedItem());
        } else {
            return "NULL";
        }
    }


}
