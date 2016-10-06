package br.com.mfelipesp.tictactoe_ia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.mfelipesp.tictactoe_ia.models.Jogada;
import br.com.mfelipesp.tictactoe_ia.models.Jogadas;
import br.com.mfelipesp.tictactoe_ia.motorInferencia.MotorInferencia;

public class ShowJogadaActivity extends AppCompatActivity {

    public static String EXTRA_JOGADA = "JOGADA";

    private TextView jogadasX, jogadasO, resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_jogada);

        resultado = (TextView) findViewById(R.id.txtResultado);
        jogadasX = (TextView) findViewById(R.id.txtJogadasJogadorX);
        jogadasO = (TextView) findViewById(R.id.txtJogadasJogadorO);

        checkParametros();
    }

    public void checkParametros(){
        Intent myIntent = getIntent();

        String jogadaIndex = myIntent.getStringExtra(EXTRA_JOGADA);
        int index = Integer.parseInt(jogadaIndex);
        Jogada jogada = MotorInferencia.getInstancie(this).lerJogadasRealizadas();

        Jogadas jogadas = jogada.getJogadas().get(index);

        resultado.setText(jogadas.getGanhador());

        String jogadaX = "";
        for(int i : jogadas.getCpuX()){
            jogadaX = jogadaX + "--" + i ;
        }

        jogadasX.setText(jogadaX);

        String jogadaO = "";
        for (int i: jogadas.getCpuO()){
            jogadaO = jogadaO + "--" + i;
        }

        jogadasO.setText(jogadaO);




    }

}
