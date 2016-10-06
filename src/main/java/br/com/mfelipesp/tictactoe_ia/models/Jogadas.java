package br.com.mfelipesp.tictactoe_ia.models;

import java.util.List;

/**
 * Created by markFelipe on 28/09/16.
 */
public class Jogadas {

    private List<Integer> cpuX;

    private List<Integer> cpuO;

    private String ganhador;

    public Jogadas(List<Integer> cpuX, List<Integer> cpuO){
        this.cpuX = cpuX;
        this.cpuO = cpuO;
    }

    public List<Integer> getCpuX() {
        return cpuX;
    }

    public void setCpuX(List<Integer> cpuX) {
        this.cpuX = cpuX;
    }

    public List<Integer> getCpuO() {
        return cpuO;
    }

    public void setCpuO(List<Integer> cpuO) {
        this.cpuO = cpuO;
    }

    public String getGanhador() {
        return ganhador;
    }

    public void setGanhador(String ganhador) {
        this.ganhador = ganhador;
    }
}
