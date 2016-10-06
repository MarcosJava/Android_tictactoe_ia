package br.com.mfelipesp.tictactoe_ia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.mfelipesp.tictactoe_ia.models.Jogada;
import br.com.mfelipesp.tictactoe_ia.motorInferencia.MotorInferencia;

/**
 * Created by markFelipe on 28/09/16.
 */
public class JogadasAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private Jogada jogada;

    public JogadasAdapter(Context context, String[] values) {
        super(context, R.layout.activity_jogadas_list, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_jogadas_list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.text);
        textView.setText(values[position]);


        return rowView;
    }
}
