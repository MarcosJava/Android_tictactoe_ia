package br.com.mfelipesp.tictactoe_ia.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by markFelipe on 28/09/16.
 */
public class Jogada {

    private List<Jogadas> jogadas;

    public Jogada(){
        this.jogadas = new ArrayList<Jogadas>();
    }

    public void addJogadas(Jogadas jogadas){
        this.jogadas.add(jogadas);
    }

    public List<Jogadas> getJogadas() {
        return jogadas;
    }

    public void setJogadas(List<Jogadas> jogadas) {
        this.jogadas = jogadas;
    }
}
