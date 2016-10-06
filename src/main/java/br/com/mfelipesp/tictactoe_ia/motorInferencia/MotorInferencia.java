package br.com.mfelipesp.tictactoe_ia.motorInferencia;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.mfelipesp.tictactoe_ia.R;
import br.com.mfelipesp.tictactoe_ia.baseconhecimento.BaseConhecimento;
import br.com.mfelipesp.tictactoe_ia.models.Jogada;

/**
 * Created by markFelipe on 24/09/16.
 */
public class MotorInferencia {


    private Context context;
    private List<Integer> jogadasUsuarios;
    private List<Integer> jogadasCPU;
    private List<Integer> jogadasVencedoras;
    private String tipoJogador;
    private String dificuldade;
    private BaseConhecimento baseConhecimento;

    private static MotorInferencia instancie;

    private MotorInferencia(Context context){
        this.jogadasCPU = new ArrayList<>();
        this.jogadasUsuarios = new ArrayList<>();
        this.jogadasVencedoras = new ArrayList<>();
        this.context = context;
        this.baseConhecimento = new BaseConhecimento(context);
    }

    public static MotorInferencia getInstancie(Context context){
        if(instancie == null){
            instancie = new MotorInferencia(context);
        }
        return instancie;
    }

    public boolean hasVitoriaPlayer(){
        List<Integer> vencedor = baseConhecimento.hasVitoria(jogadasUsuarios);
        if(vencedor == null){
            return false;
        } else {
            this.jogadasVencedoras = vencedor;
            return true;
        }
    }

    public void resetMotorInferencia(){
        instancie = null;

    }

    public boolean hasVitoriaCPU(){
        List<Integer> vencedor = baseConhecimento.hasVitoria(jogadasCPU);
        if (vencedor == null){
            return false;
        } else {
            this.jogadasVencedoras = vencedor;
            return true;
        }
    }

    public void comecarMaquinas(boolean x){
        if(x){
            this.jogadasUsuarios.add(baseConhecimento.qualquerJogada(jogadasUsuarios,jogadasCPU));
        } else {
            this.jogadasCPU.add(baseConhecimento.qualquerJogada(jogadasUsuarios,jogadasCPU));
        }

    }

    public int qtdJogadasUsuario(){
        return this.jogadasUsuarios.size();
    }

    public int qtdJogadasCPU(){
        return this.jogadasCPU.size();
    }

    public List<Integer> todasJogadas(){
        List<Integer> jogadas = new ArrayList<>();
        jogadas.addAll(this.jogadasUsuarios);
        jogadas.addAll(this.jogadasCPU);
        return jogadas;
    }

    public boolean isFim(){

        int valor = qtdJogadasCPU() + qtdJogadasUsuario();
        if(valor == 9){
            return true;
        }
        return false;
    }

    public void realizarJogadaCPU() {
        int jogada = 0;

        boolean hasVitoria = hasVitoriaPlayer() || hasVitoriaCPU();


        if(dificuldade != null && !hasVitoria) {
            String []mode = this.context.getResources().getStringArray(R.array.country_arrays);
            if (mode[1].equalsIgnoreCase(dificuldade)) { // MEDIO
                jogada = jogadaNivelMedio();

            } else if (mode[0].equalsIgnoreCase(dificuldade)) { //DIFICIL
                jogada = jogadaNivelDificil();

            } else if (mode[2].equalsIgnoreCase(dificuldade)) { //FACIL
                jogada = jogadaNivemFacil();
            }

            if (jogada != 0) {
                jogadasCPU.add(jogada);
            }
        }

    }


    private int jogadaNivelImpossivel(){
        int jogada = 0;

        if(jogadasCPU.isEmpty()){
            jogada = 5;

        } else if (jogadasCPU.size() == 1){
            jogada = terceiraJogadaNivelDificil();

        } else if (jogadasCPU.size() > 1 ){

            int playVence = isJogadorGanharImpossible();

            if (cpuVence() != 0){
                jogada = cpuVence();

            } else if(playVence != 0){
                jogada = playVence;

            } else if (jogadasCPU.size() == 2 && playVence == 0) {
                jogada = terceiraJogadaNivelDificil();

            } else {
                jogada = atacaEmDificil();

            }

            if(jogada == 0 ){
                jogada = baseConhecimento.qualquerJogada(jogadasCPU, jogadasUsuarios);
            }

        }

        return jogada;
    }

    /**
     * Retiradao por reclamacoes dos usuarios
     * @return
     */
    private int isJogadorGanharImpossible() {
        List<Integer> vitorias = this.baseConhecimento.possiveisVitorias(this.jogadasUsuarios);
        for(Integer i: vitorias){
            if(!todasJogadas().contains(i)){
                return i;
            }

        }
        return 0;
    }



    private int jogadaNivelDificil(){
        int jogada = 0;

        if(jogadasCPU.isEmpty()){
            jogada = 5;

        } else if (jogadasCPU.size() == 1){
            jogada = terceiraJogadaNivelDificil();

        } else if (jogadasCPU.size() > 1 ){

            boolean playVence = isJogadorGanhar();

            if (cpuVence() != 0){
                jogada = cpuVence();

            } else if(playVence){
                jogada = defendeEmDificil();

            } else if (jogadasCPU.size() == 2 && !playVence) {
                jogada = terceiraJogadaNivelDificil();

            } else {
                jogada = atacaEmDificil();

            }

            if(jogada == 0 ){
                jogada = baseConhecimento.qualquerJogada(jogadasCPU, jogadasUsuarios);
            }

        }

        return jogada;
    }

    private boolean isJogadorGanhar() {
        List<Integer> vitorias = this.baseConhecimento.possiveisVitorias(this.jogadasUsuarios);
        for(Integer i: vitorias){
            if(!todasJogadas().contains(i)){
                return true;
            }

        }
        return false;
    }

    private int defendeEmDificil(){
        List<Integer> vitorias = this.baseConhecimento.lstTerceiraJogadas(jogadasUsuarios.get(0), jogadasUsuarios.get(1));
        for (Integer i : vitorias){
            if(!todasJogadas().contains(i)){
                return i;
            }
        }
        return 0;
    }

    private int cpuVence(){
        List<Integer> vitorias = this.baseConhecimento.possiveisVitorias(this.jogadasCPU);
        for (Integer i : vitorias){
            if(!todasJogadas().contains(i)){
                return i;
            }

        }
        return 0;
    }

    private int atacaEmDificil() {
        List<Integer> vitorias = this.baseConhecimento.possiveisVitorias(jogadasCPU);
        for (Integer i : vitorias){
            if(!todasJogadas().contains(i)){
                return i;
            }
        }
        return 0;
    }

    private int terceiraJogadaNivelDificil() {

        if (!todasJogadas().contains(3)){
            return 3;

        } else if(!todasJogadas().contains(1)){
            return 1;

        } else if(!todasJogadas().contains(9)){
            return 9;

        } else if(!todasJogadas().contains(7)){
            return 7;

        } else {
            return 0;

        }


    }

    private int jogadaNivelMedio(){
        return baseConhecimento.qualquerJogada(jogadasCPU, jogadasUsuarios);
    }

    private int jogadaNivemFacil() {

        int jogada = 0;

        //Realiza a segunda jogada.
        if(this.jogadasUsuarios.size() == 1){

            List<Integer> jogadas = baseConhecimento
                    .lstSegundaJogada(jogadasUsuarios.get(0));

            List<Integer> ondeNaoJogar = new ArrayList<>();

            for (Integer i : jogadas){
                if(!todasJogadas().contains(i)){
                    ondeNaoJogar.add(i);
                }
            }
            jogada = baseConhecimento.qualquerJogadaMenos(jogadasUsuarios, ondeNaoJogar);

        }else if(this.jogadasUsuarios.size() > 1){
            List<Integer> vitorias = this.baseConhecimento.possiveisVitorias(this.jogadasUsuarios);
            jogada = baseConhecimento.qualquerJogadaMenos(todasJogadas(), vitorias);

        }

        return jogada;
    }


    public void salvarJogadas(){
        String ganhador = "Nao Tem";
        if(hasVitoriaCPU()){
            ganhador = "cpuO";
        } else if (hasVitoriaPlayer()){
            ganhador = "cpuX";
        } else {
            ganhador = "velha";
        }
        this.baseConhecimento.salvarJogadas(jogadasUsuarios, jogadasCPU, ganhador);

    }

    public Jogada lerJogadasRealizadas(){
        return this.baseConhecimento.lerJogadasRealizadas();

    }
    public void resetarJogadasRealizadas(){
        this.baseConhecimento.resetarJogadasRealizadas();
    }

    /***
     *  GETTER E SETTER
     * @param numero
     */


    public void addJogadaUsuario(int numero){
        this.jogadasUsuarios.add(numero);
    }

    public void addJogadaCPU(int numero){
        this.jogadasCPU.add(numero);
    }

    public List<Integer> getJogadasUsuarios(){
        return jogadasUsuarios;
    }

    public List<Integer> getJogadasCPU() {
        return jogadasCPU;
    }

    public String getTipoJogador() {
        return tipoJogador;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    public void setTipoJogador(String tipoJogador) {
        this.tipoJogador = tipoJogador;
    }

    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    public List<Integer> getJogadasVencedoras() {
        return jogadasVencedoras;
    }
}
