package br.com.mfelipesp.tictactoe_ia;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.mfelipesp.tictactoe_ia.models.Jogada;
import br.com.mfelipesp.tictactoe_ia.motorInferencia.MotorInferencia;

public class JogadasListActivity extends ListActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Jogada jogada = MotorInferencia.getInstancie(this).lerJogadasRealizadas();
        Integer qtde = jogada.getJogadas().size();

        String[] jogadas = new String[qtde];
        for (int i = 0 ; i < qtde; i ++){
            jogadas[i] = "" + i;
        }

        setListAdapter(new JogadasAdapter(this, jogadas));



    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //get selected items
        String selectedValue = (String) getListAdapter().getItem(position);
        Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();

        Intent myIntent = new Intent(this, ShowJogadaActivity.class);
        myIntent.putExtra(ShowJogadaActivity.EXTRA_JOGADA, selectedValue);
        startActivity(myIntent);

    }

}
