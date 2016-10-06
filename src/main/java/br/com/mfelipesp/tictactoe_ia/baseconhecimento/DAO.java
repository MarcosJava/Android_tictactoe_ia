package br.com.mfelipesp.tictactoe_ia.baseconhecimento;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import br.com.mfelipesp.tictactoe_ia.R;
import br.com.mfelipesp.tictactoe_ia.models.Jogada;
import br.com.mfelipesp.tictactoe_ia.models.Jogadas;

/**
 * Created by markFelipe on 25/09/16.
 */
public class DAO {

    private Context context;
    private  JogadaRealizadaDAO jogadaRealizadaDAO;

    public DAO(Context context){
        this.context = context;
        this.jogadaRealizadaDAO = new JogadaRealizadaDAO(context);
    }

    /***
     * Retorna Jogadas Ganhas, se houver vencedor.
     * Caso nao tenha vencedor retorna null.
     * @param jogadas
     * @return
     */
    public List<Integer> vitoria(List<Integer> jogadas) {

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray arrayVitorias = obj.getJSONArray("vitoria");
            List<Integer> jogadasGanhas = new ArrayList<>();
            for (Integer jogada: jogadas){

                JSONObject jsonVitoria = (JSONObject) arrayVitorias.get(0);
                JSONArray jsonJogadas23 = jsonVitoria.getJSONArray(jogada + "");

                for (int i = 0; i < jsonJogadas23.length(); i++) {

                    JSONObject json = jsonJogadas23.getJSONObject(i);
                    Integer jogada2 = Integer.parseInt(json.getString("jogada2"));
                    Integer jogada3 = Integer.parseInt(json.getString("jogada3"));

                    // Verificar a segunda jogada
                    if (jogadas.contains(jogada2) && jogadas.contains(jogada3)) {
                        jogadasGanhas.add(jogada);
                        jogadasGanhas.add(jogada2);
                        jogadasGanhas.add(jogada3);
                        return jogadasGanhas;
                    }

                }
            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Integer> possivelVitoria(List<Integer> jogadas) {

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray arrayVitorias = obj.getJSONArray("vitoria");
            List<Integer> jogadasGanhas = new ArrayList<>();
            for (Integer jogada: jogadas){

                JSONObject jsonVitoria = (JSONObject) arrayVitorias.get(0);
                JSONArray jsonJogadas23 = jsonVitoria.getJSONArray(jogada + "");

                for (int i = 0; i < jsonJogadas23.length(); i++) {

                    JSONObject json = jsonJogadas23.getJSONObject(i);
                    Integer jogada2 = Integer.parseInt(json.getString("jogada2"));
                    Integer jogada3 = Integer.parseInt(json.getString("jogada3"));

                    // Verificar a segunda jogada
                    if (jogadas.contains(jogada2)) {
                        jogadasGanhas.add(jogada3);
                    }

                }
            }
            return jogadasGanhas;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Integer> segundaJogada(int jogada1) {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray arrayVitorias = obj.getJSONArray("vitoria");
            List<Integer> segundasJogadas = new ArrayList<>();

            JSONObject jsonVitoria = (JSONObject) arrayVitorias.get(0);
            JSONArray jsonJogadas23 = jsonVitoria.getJSONArray(jogada1 + "");

            for (int i = 0; i < jsonJogadas23.length(); i++) {
                JSONObject json = jsonJogadas23.getJSONObject(i);
                Integer jogada2 = Integer.parseInt(json.getString("jogada2"));
                segundasJogadas.add(jogada2);
            }

            return segundasJogadas;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Integer> terceiraJogada(Integer jogada1, Integer jogada2) {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray arrayVitorias = obj.getJSONArray("vitoria");
            List<Integer> terceirasJogadas = new ArrayList<>();

            JSONObject jsonVitoria = (JSONObject) arrayVitorias.get(0);
            JSONArray jsonJogadas23 = jsonVitoria.getJSONArray(jogada1 + "");

            for (int i = 0; i < jsonJogadas23.length(); i++) {
                JSONObject json = jsonJogadas23.getJSONObject(i);
                Integer jogada2Temp = Integer.parseInt(json.getString("jogada2"));
                if (jogada2 == jogada2Temp) {
                    Integer jogada3 = Integer.parseInt(json.getString("jogada3"));
                    terceirasJogadas.add(jogada3);
                }
            }

            return terceirasJogadas;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.base_conhecimento);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void salvarJogada(List<Integer> cpuX, List<Integer> cpuO, String ganhador){

        Jogada jogada = montaJogada(cpuX, cpuO, ganhador);
        Gson gson = new Gson();
        String jsonJogada = gson.toJson(jogada);
        jogadaRealizadaDAO.escrever(jsonJogada);

    }

    private Jogada montaJogada(List<Integer> cpuX, List<Integer> cpuO, String ganhador){

        //CORPO
        Jogadas jogadas = new Jogadas(cpuX, cpuO);
        jogadas.setGanhador(ganhador);

        //PAI
        Jogada jogada = lerJogadasRealizadas();
        jogada.addJogadas(jogadas);


        return jogada;
    }

    public Jogada lerJogadasRealizadas(){
        try {
            String jsonJogada = "";
            jsonJogada = jogadaRealizadaDAO.ler();
            if(jsonJogada.isEmpty()){
                return new Jogada();
            } else {
                Log.i("JOGADAS", jsonJogada);
                Gson gson = new Gson();
                Jogada jogada = gson.fromJson(jsonJogada, Jogada.class);
                return jogada;
            }

        } catch (Exception e){
            Log.e("ERRO", e.getMessage());
            e.printStackTrace();
            return new Jogada();
        }

    }

    public void resetarJogadasRealizadas(){
        jogadaRealizadaDAO.escrever("");
    }



}
