package br.com.mfelipesp.tictactoe_ia;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import br.com.mfelipesp.tictactoe_ia.motorInferencia.MotorInferencia;

public class PlayerCPUActivity extends AppCompatActivity implements Button.OnClickListener {

    public static final String EXTRA_TIPO_JOGADOR = "TIPO_JOGADOR";
    public static final String EXTRA_DIFICULDADE = "DIFICULDADE";
    private Context context;
    private Button bnt1, bnt2, bnt3;
    private Button bnt4, bnt5, bnt6;
    private Button bnt7, bnt8, bnt9;

    private Button btnJogaNovamente;

    private String tipoJogador;
    private boolean hasVitoria;
    private boolean hasDerrota;
    private int verificador = 0;

    private ImageView imageViewTabuleiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_cpu);

        context = this;
        checkParametros();
        addButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Execute some code after 2 seconds have passed
        if("CPU".equalsIgnoreCase(this.tipoJogador)){

            travarJogo(false);
            Handler handler = new Handler();
            for (int i = 1 ; i < 10 ; i ++) {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        if (!hasDerrota && !hasVitoria) {
                            ++verificador;
                            boolean x = verificador % 2 == 0;

                            if (x) {
                                MotorInferencia.getInstancie(context).comecarMaquinas(true);
                                marcarJogadasUsuario();
                            } else {
                                MotorInferencia.getInstancie(context).comecarMaquinas(false);
                                marcarJogadasCPU();
                            }

                            if (MotorInferencia.getInstancie(context).hasVitoriaPlayer()) {
                                marcarVitoria();
                                travarJogo(true);
                                btnJogaNovamente.setText(getString(R.string.jogueDeNovo_vitoria));
                                MotorInferencia.getInstancie(context).salvarJogadas();
                                hasVitoria = true;

                            } else if (MotorInferencia.getInstancie(context).hasVitoriaCPU()) {
                                marcarDerrota();
                                travarJogo(true);
                                btnJogaNovamente.setText(getString(R.string.jogueDeNovo_derrota));
                                MotorInferencia.getInstancie(context).salvarJogadas();
                                hasDerrota = true;

                            } else if (MotorInferencia.getInstancie(context).isFim()){
                                marcarVelha();
                                travarJogo(true);
                                btnJogaNovamente.setText(getString(R.string.jogueDeNovo));
                                MotorInferencia.getInstancie(context).salvarJogadas();


                            }
                        }
                    }
                }, i * 800);
            }
        }
    }

    public void checkParametros(){
        Intent myIntent = getIntent();

        String dificuldade = myIntent.getStringExtra(EXTRA_DIFICULDADE);
        this.tipoJogador = myIntent.getStringExtra(EXTRA_TIPO_JOGADOR);

        initMotorInferencia(dificuldade, tipoJogador);
        Log.i("DEBUG", "Dificuldade eh : " + dificuldade + " e a jogada :" + tipoJogador);

    }

    private void initMotorInferencia(String dificuldade, String tipoJogador){

        MotorInferencia.getInstancie(this).resetMotorInferencia();


        MotorInferencia.getInstancie(this).setTipoJogador(tipoJogador);
        String []mode = getResources().getStringArray(R.array.country_arrays);
        if(dificuldade != null){
            MotorInferencia.getInstancie(this).setDificuldade(dificuldade);
            if(dificuldade.equalsIgnoreCase(mode[0])){
                MotorInferencia.getInstancie(this).realizarJogadaCPU();
                marcarJogadasCPU();
            }
        }
        hasDerrota = false;
        hasVitoria = false;



    }
    public void addButtons(){
        this.bnt1 = (Button) findViewById(R.id.btn1);
        this.bnt1.setOnClickListener(this);


        this.bnt2 = (Button) findViewById(R.id.btn2);
        this.bnt2.setOnClickListener(this);

        this.bnt3 = (Button) findViewById(R.id.btn3);
        this.bnt3.setOnClickListener(this);

        this.bnt4 = (Button) findViewById(R.id.btn4);
        this.bnt4.setOnClickListener(this);

        this.bnt5 = (Button) findViewById(R.id.btn5);
        this.bnt5.setOnClickListener(this);

        this.bnt6 = (Button) findViewById(R.id.btn6);
        this.bnt6.setOnClickListener(this);

        this.bnt7 = (Button) findViewById(R.id.btn7);
        this.bnt7.setOnClickListener(this);

        this.bnt8 = (Button) findViewById(R.id.btn8);
        this.bnt8.setOnClickListener(this);

        this.bnt9 = (Button) findViewById(R.id.btn9);
        this.bnt9.setOnClickListener(this);

        this.btnJogaNovamente = (Button) findViewById(R.id.btnReset);
        this.btnJogaNovamente.setVisibility(View.INVISIBLE);
        this.btnJogaNovamente.setEnabled(false);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        String nomeId = getResources().getResourceEntryName(id);
        int jogada = pegaJogada(nomeId);

        if(jogada != 0){

            MotorInferencia.getInstancie(this).addJogadaUsuario(jogada);
            marcarJogadasPlayer(id);
            MotorInferencia.getInstancie(this).realizarJogadaCPU();
            marcarJogadasCPU();
            joga();
            showMensagem(v);

        }

    }

    private void showMensagem(View v){
        if(hasVitoria){
            Snackbar.make(v, "TEMOS UM GANHADOR", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        } else if(hasDerrota){
            Snackbar.make(v, "TENTE NOVAMENTE =( !!! ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void joga(){
        if(MotorInferencia.getInstancie(this).hasVitoriaPlayer()) {
            marcarVitoria();
            travarJogo(true);
            btnJogaNovamente.setText(getString(R.string.jogueDeNovo_vitoria));
            MotorInferencia.getInstancie(context).salvarJogadas();
            hasVitoria = true;

        } else if (MotorInferencia.getInstancie(this).hasVitoriaCPU()) {
            marcarDerrota();
            travarJogo(true);
            btnJogaNovamente.setText(getString(R.string.jogueDeNovo_derrota));
            MotorInferencia.getInstancie(context).salvarJogadas();
            hasDerrota = true;

        } else if(MotorInferencia.getInstancie(this).isFim()){
            btnJogaNovamente.setText(getString(R.string.jogueDeNovo_velha));
            marcarVelha();
            travarJogo(true);
            MotorInferencia.getInstancie(context).salvarJogadas();
        }

    }

    private int pegaJogada(String nomeId) {

        String []valor = nomeId.split("n");
        try {
            return new Integer(valor[1]);

        }catch (Exception e){
            return 0;
        }


    }

    public int pegaIddaJogada(int jogada){
        try {
            String botao = "btn" + jogada;
            return getResources().getIdentifier(botao, "id", getPackageName());
        } catch (Exception e){
            return 0;
        }


    }

    private void marcarJogadasCPU(){

        for(Integer jogada : MotorInferencia.getInstancie(this).getJogadasCPU()){

            int id = pegaIddaJogada(jogada);
            setStyleJogadaBolaNoButton(id);
        }
    }

    private void marcarJogadasUsuario(){

        for(Integer jogada : MotorInferencia.getInstancie(this).getJogadasUsuarios()){

            int id = pegaIddaJogada(jogada);
            setStyleJogadaXNoButton(id);
        }
    }

    private void marcarJogadasPlayer(Integer id){
        setStyleJogadaXNoButton(id);
    }


    private void marcarVitoria(){
        for(Integer jogada : MotorInferencia.getInstancie(this).getJogadasVencedoras()){
            int id = pegaIddaJogada(jogada);
            setStyleVitoriaNoButton(id);
        }

    }
    private void marcarDerrota(){
        for(Integer jogada : MotorInferencia.getInstancie(this).getJogadasVencedoras()){
            int id = pegaIddaJogada(jogada);
            setStyleDerrotaNoButton(id);

        }
    }

    private void setStyleDerrotaNoButton(int id){
        Button btn = (Button) findViewById(id);
        Drawable botaoDerrota = getResources().getDrawable(R.drawable.botao_derrota);
        btn.setBackgroundDrawable(botaoDerrota);

    }

    private void setStyleVitoriaNoButton(int id){
        Button btn = (Button) findViewById(id);
        Drawable botao = getResources().getDrawable(R.drawable.botao_campeao);
        btn.setBackgroundDrawable(botao);
    }

    private void setStyleJogadaXNoButton(int id){
        Button btn = (Button) findViewById(id);
        btn.setText(R.string.x);
        btn.setEnabled(false);
        Drawable botao = getResources().getDrawable(R.drawable.botao_jogado);
        btn.setBackgroundDrawable(botao);
    }

    private void setStyleJogadaBolaNoButton(int id){
        Button btn = (Button) findViewById(id);
        btn.setText(R.string.bola);
        btn.setEnabled(false);
        Drawable botao = getResources().getDrawable(R.drawable.botao_jogado);
        btn.setBackgroundDrawable(botao);
    }

    private void setStyleVelhaNoButton(int id){
        Button btn = (Button) findViewById(id);
        btn.setEnabled(false);
        Drawable botao = getResources().getDrawable(R.drawable.botao_velha);
        btn.setBackgroundDrawable(botao);
    }

    private void setStyleDefaultButton(int id){
        Button btn = (Button) findViewById(id);
        btn.setEnabled(true);
        bnt1.setText(getString(R.string.marque));
        Drawable botao = getResources().getDrawable(R.drawable.botao_default);
        btn.setBackgroundDrawable(botao);
    }

    private void setStyleDefaultButton(int id, boolean ativo){
        Button btn = (Button) findViewById(id);
        btn.setEnabled(ativo);
        btn.setText(getString(R.string.marque));
        Drawable botao = getResources().getDrawable(R.drawable.botao_default);
        btn.setBackgroundDrawable(botao);
    }


    private void marcarVelha() {
        for(Integer i : MotorInferencia.getInstancie(this).todasJogadas()){
            int id = pegaIddaJogada(i);
            setStyleVelhaNoButton(id);
        }
    }

    private void travarJogo(boolean jogaNovamente) {
        this.bnt1.setEnabled(false);
        this.bnt2.setEnabled(false);
        this.bnt3.setEnabled(false);
        this.bnt4.setEnabled(false);
        this.bnt5.setEnabled(false);
        this.bnt6.setEnabled(false);
        this.bnt7.setEnabled(false);
        this.bnt8.setEnabled(false);
        this.bnt9.setEnabled(false);
        if(jogaNovamente){
            this.btnJogaNovamente.setVisibility(View.VISIBLE);
        } else {
            this.btnJogaNovamente.setVisibility(View.GONE);
        }

        this.btnJogaNovamente.setEnabled(jogaNovamente);

    }

    public void resetJogo(View view) {

        if("CPU".equalsIgnoreCase(tipoJogador)){
            resetButtonsWithEnable(false);
            initMotorInferencia(null, tipoJogador);
            onResume();

        } else {
            this.btnJogaNovamente.setVisibility(View.INVISIBLE);
            this.btnJogaNovamente.setEnabled(false);

            resetButtonsWithEnable(true);


            String dificuldade = MotorInferencia.getInstancie(this).getDificuldade();
            String tipoJogador = MotorInferencia.getInstancie(this).getTipoJogador();

            initMotorInferencia(dificuldade, tipoJogador);
        }



    }

    private void resetButtonsWithEnable(boolean enable) {

        for (int i = 1 ; i < 10; i++){
            int id = pegaIddaJogada(i);
            setStyleDefaultButton(id, enable);
        }


    }
}
