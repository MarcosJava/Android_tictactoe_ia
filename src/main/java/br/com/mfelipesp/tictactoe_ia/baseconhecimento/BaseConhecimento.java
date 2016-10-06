package br.com.mfelipesp.tictactoe_ia.baseconhecimento;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.mfelipesp.tictactoe_ia.models.Jogada;

/**
 * Created by markFelipe on 24/09/16.
 */
public class BaseConhecimento {

    private Context context;
    private DAO dao;

    public BaseConhecimento(Context context){
        this.context = context;
        this.dao = new DAO(context);
    }


    public List<Integer> hasVitoria(List<Integer> jogadas){
        if(!jogadas.isEmpty()
                && jogadas.size() > 2){
            return this.dao.vitoria(jogadas);
        }
        return null;

    }

    public List<Integer> lstTerceiraJogadas(int primeiraJogada, int segundaJogada){
        return this.dao.terceiraJogada(primeiraJogada, segundaJogada);
    }

    public List<Integer> lstSegundaJogada(int primeiraJogada){
        return this.dao.segundaJogada(primeiraJogada);
    }



    public Integer qualquerJogada(List<Integer> cpu, List<Integer> jogador){

        List<Integer> jogadas = new ArrayList<>();
        jogadas.addAll(cpu);
        jogadas.addAll(jogador);

        if(jogadas.size() == 9){
            return 0;
        }

        boolean para = true;
        int numero = 5;
        while(para){

            if(!jogadas.contains(numero)){
                para = false;
                continue;
            }
            Random gerador = new Random();
            numero = gerador.nextInt(9) + 1;
        }
        return  numero;

    }

    public Integer  qualquerJogadaMenos(List<Integer> jogadas, List<Integer> menos){

        for (int i = 1; i < 10 ; i++){
            if(!jogadas.contains(i) && !menos.contains(i)){
                return i;
            }
        }
        return  qualquerJogada(jogadas, new ArrayList<Integer>());

    }

    public List<Integer> possiveisVitorias(List<Integer> jogadas){
        return this.dao.possivelVitoria(jogadas);
    }

    public void salvarJogadas(List<Integer> cpuX, List<Integer> cpuO, String ganhador){
        this.dao.salvarJogada(cpuX, cpuO, ganhador);
    }

    public Jogada lerJogadasRealizadas(){
        return this.dao.lerJogadasRealizadas();
    }
    public void resetarJogadasRealizadas(){
        this.dao.resetarJogadasRealizadas();
    }
}
