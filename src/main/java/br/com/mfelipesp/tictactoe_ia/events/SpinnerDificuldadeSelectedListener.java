package br.com.mfelipesp.tictactoe_ia.events;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by markFelipe on 24/09/16.
 */
public class SpinnerDificuldadeSelectedListener implements AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(parent.getContext(),
                "Olha a dificuldade : " + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
