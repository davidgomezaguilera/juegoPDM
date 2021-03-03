package com.example.fishingpdm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowMetrics;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class juegoView extends SurfaceView {

    private Bitmap bm, fondo;
    private SurfaceHolder holder;
    private hiloJuegoLoop hiloJuego;
    private int x = 0;
    private int speedX = 2;
    private Pez pez;
    private Personaje personaje;
    private long ultimoClick;
    public int xJugador, yJugador;
    private int puntos;
    private Paint lapizInterfaz;
    private int tiempo;
    private int vidas;

    private List<Pez> peces = new ArrayList<Pez>();
    private List<Object> objetos = new ArrayList<Object>();

    public juegoView(Context context) {
        super(context);
hiloJuego = new hiloJuegoLoop(this);
        holder = getHolder();
        vidas = 3;
        xJugador = 500;
        //sustituir estoy por la mitad del alto y la mitad del ancho de la pantalla
        yJugador = 1050;
        lapizInterfaz = new Paint();
        lapizInterfaz.setColor(getResources().getColor(R.color.black));
        lapizInterfaz.setStrokeWidth(20);
        lapizInterfaz.setTextSize(60);
        personaje = new Personaje(this);

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                crearPeces();
                hiloJuego.setRunning(true);
                hiloJuego.start();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

                boolean r = true;
                hiloJuego.setRunning(false);
                while(r){
                    try{
                        hiloJuego.join();//esperamos a que se termine de ejecutar el run
                        r = false;//cuando termine, lo paras mediante la variable r
                    }catch(Exception e){

                    }
                }

            }
        });
        new CountDownTimer(60000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                tiempo = (int)millisUntilFinished/1000;
            }
            @Override
            public void onFinish() {
                System.exit(0);
            }
        }.start();
        /*bm = BitmapFactory.decodeResource(getResources(), R.drawable.pez_azul);
        pez = new Pez(this,bm);
        pez = new Pez(this,bm);
        pez = new Pez(this,bm);
        pez = new Pez(this,bm);

         */
        fondo = BitmapFactory.decodeResource(getResources(), R.drawable.fondo3);
        bm = BitmapFactory.decodeResource(getResources(),R.drawable.personaje);
    }

    public void cambiar(float x, float y){
        if(vidas == 0){
            System.exit(0);
        }
        if (x >= 0.5 || x <= -0.5 || y >= 0.5 || y <= -0.5) {
            x = -x;
            x = x*2;
            y = y*2;
            //yJugador = getHeight()/2;

            //este if es para que si el dispositivo esta sobre una base plana, que no esté constantemente moviendose.
            if (xJugador < getWidth()-bm.getWidth()) {
                if (xJugador > 0) {
                    if (yJugador > 0) {
                        if (yJugador < getHeight()-bm.getHeight()) {
                            xJugador += x;
                            yJugador += y;
                        }else{
                            xJugador += x;
                            yJugador = getHeight() - bm.getHeight()-1;
                        }
                    } else {
                        xJugador += x;
                        yJugador = 1;
                    }
                } else {
                    xJugador = 1;
                    yJugador += y;
                }
            } else {
                xJugador = getWidth() - bm.getWidth()-40;
                yJugador += y;
            }
        }
    }
    private void crearPeces(){
        objetos.add(crearPezRojo(R.drawable.pez_rojo2));
        objetos.add(crearSerpiente(R.drawable.serpiente));
        objetos.add(crearPezAzul(R.drawable.pez_azul));
        objetos.add(crearPezAzul(R.drawable.pez_azul3));
        objetos.add(crearManta(R.drawable.manta2));
        objetos.add(crearTiburon(R.drawable.tiburonn));
        objetos.add(crearPezAmarillo(R.drawable.pez_amarillo));
        objetos.add(crearPezAmarillo(R.drawable.pez_amarillo2));
        objetos.add(crearTiburon(R.drawable.tiburon2));
        objetos.add(crearPezRojo(R.drawable.pez_rojo));
        objetos.add(crearPezVerde(R.drawable.pez_verde2));
        objetos.add(crearManta(R.drawable.manta_raya));
        objetos.add(crearTiburon(R.drawable.tiburon3));
        objetos.add(crearPezVerde(R.drawable.pez_verde3));
        objetos.add(crearTiburon(R.drawable.tiburonn));
        objetos.add(crearPezVerde(R.drawable.pez_verde));


    }

    private Pez crearPez(int resource){
        Bitmap bm = BitmapFactory.decodeResource(getResources(),resource);
        return new Pez(this, bm);
    }
    private Tiburon crearTiburon(int resource){
        Bitmap bm = BitmapFactory.decodeResource(getResources(),resource);
        return new Tiburon(this,bm);
    }
    private PezVerde crearPezVerde(int resource){
        Bitmap bm = BitmapFactory.decodeResource(getResources(), resource);
        return new PezVerde(this, bm);
    }
    private Manta crearManta(int resource){
        Bitmap bm = BitmapFactory.decodeResource(getResources(), resource);
        return new Manta(this, bm);
    }
    private PezAzul crearPezAzul(int resource){
        Bitmap bm = BitmapFactory.decodeResource(getResources(),resource);
        return new PezAzul(this,bm);
    }
    private PezRojo crearPezRojo(int resource){
        Bitmap bm = BitmapFactory.decodeResource(getResources(), resource);
        return new PezRojo(this,bm);
    }
    private PezAmarillo crearPezAmarillo(int resource){
        Bitmap bm = BitmapFactory.decodeResource(getResources(), resource);
        return new PezAmarillo(this,bm);
    }
    private Serpiente crearSerpiente(int resource){
        Bitmap bm = BitmapFactory.decodeResource(getResources(), resource);
        return new Serpiente(this,bm);
    }



    @Override
    protected void onDraw(Canvas canvas){
        System.out.println("HOLA??");
        canvas.drawBitmap(fondo,0,0,null);
        //aqui ya llamamos al ondraw() de cada objeto creado que tendrá tanto las fisicas como el dibujo
        for(int i = 0; i<objetos.size();i++){
            personaje.setX(xJugador);
            personaje.setY(yJugador);
            System.out.println("DIBUJANDO AL PERSONAJEEEEEEEEEEEEEEEEEE");
            personaje.onDraw(canvas);

            canvas.drawText("Puntos "+puntos,100,100,lapizInterfaz);
            if(tiempo < 10){
                lapizInterfaz.setColor(getResources().getColor(R.color.design_default_color_error));
            }
            canvas.drawText("Tiempo "+tiempo,100,200,lapizInterfaz);
            canvas.drawText("Vidas "+vidas, 100, 300,lapizInterfaz);
            if(objetos.get(i) instanceof Serpiente){
                Serpiente serpiente = (Serpiente)objetos.get(i);
                serpiente.onDraw(canvas);
            }
            if(objetos.get(i) instanceof Tiburon){

                Tiburon tiburon = (Tiburon)objetos.get(i);
                tiburon.onDraw(canvas);
            }
            if(objetos.get(i)  instanceof PezVerde){
                PezVerde pez = (PezVerde)objetos.get(i);
                pez.onDraw(canvas);
            }
            System.out.println("HOLA "+ i);
            if(objetos.get(i) instanceof PezAzul){
                PezAzul pezAzul = (PezAzul)objetos.get(i);
                pezAzul.onDraw(canvas);
            }
            if(objetos.get(i) instanceof PezRojo){
                PezRojo pezRojo = (PezRojo)objetos.get(i);
                pezRojo.onDraw(canvas);
            }
            if(objetos.get(i) instanceof PezAmarillo){
                PezAmarillo pezAmarillo = (PezAmarillo)objetos.get(i);
                pezAmarillo.onDraw(canvas);
            }
            if(objetos.get(i) instanceof Manta){
                Manta manta = (Manta)objetos.get(i);
                manta.onDraw(canvas);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Toast.makeText(getContext(),"Coordenada x Personaje = "+personaje.getX(),Toast.LENGTH_SHORT).show();
        //indicamos que si la ultima pulsacion fue antes de 500 milisegundos que no entre
        if(System.currentTimeMillis() - ultimoClick > 500){
            ultimoClick = System.currentTimeMillis();
            synchronized (getHolder()){
                //recorremos toda la lista de los sprites
                for(int i = objetos.size() -1;i>=0;i--){

                    if(objetos.get(i) instanceof Serpiente){
                        Serpiente serpiente = (Serpiente)objetos.get(i);
                        if(serpiente.esCapturado(personaje.getX()+20, personaje.getY())){
                            Toast.makeText(getContext(), "OH NO, has pescado una serpiente mala",Toast.LENGTH_SHORT).show();
                            puntos += serpiente.getPuntos();
                            objetos.remove(serpiente);
                            break;
                        }
                    }
                    if(objetos.get(i) instanceof Pez){

                        Pez pez = (Pez)objetos.get(i);
                        if(pez.esCapturado(personaje.getX()+20, personaje.getY())){
                            puntos += pez.getPuntos();
                            objetos.remove(pez);
                            break;
                        }
                    }
                    if(objetos.get(i) instanceof PezAzul){
                        PezAzul pezAzul = (PezAzul)objetos.get(i);
                        if(pezAzul.esCapturado(personaje.getX(), personaje.getY())){

                            puntos += pezAzul.getPuntos();
                            objetos.remove(pezAzul);
                            break;
                        }
                    }
                    if(objetos.get(i) instanceof Manta){
                        Manta manta = (Manta)objetos.get(i);
                        if(manta.esCapturado(personaje.getX(),personaje.getY())){
                            puntos += manta.getPuntos();
                            objetos.remove(manta);
                            break;
                        }
                    }
                    if(objetos.get(i) instanceof PezRojo){
                        PezRojo pezRojo = (PezRojo)objetos.get(i);
                        if(pezRojo.esCapturado(personaje.getX(),personaje.getY())){
                            puntos += pezRojo.getPuntos();
                            objetos.remove(pezRojo);
                            break;
                        }
                    }
                    if(objetos.get(i) instanceof PezAmarillo){
                        PezAmarillo pezAmarillo = (PezAmarillo)objetos.get(i);
                        if(pezAmarillo.esCapturado(personaje.getX(),personaje.getY())){
                            puntos += pezAmarillo.getPuntos();
                            objetos.remove(pezAmarillo);
                            break;
                        }
                    }
                    if(objetos.get(i) instanceof PezVerde){
                        PezVerde pezVerde = (PezVerde)objetos.get(i);
                        if(pezVerde.esCapturado(personaje.getX(),personaje.getY())){
                            puntos += pezVerde.getPuntos();
                            objetos.remove(pezVerde);
                            break;
                        }
                    }
                    if(objetos.get(i) instanceof Tiburon){
                        Tiburon tiburon = (Tiburon)objetos.get(i);
                        if(tiburon.esCapturado(personaje.getX(),personaje.getY())){
                            vidas--;
                            puntos += tiburon.getPuntos();
                            objetos.remove(tiburon);
                            break;
                        }
                    }
                    //si hemos pulsado sobre uno se elimina
                    /*if(pez.esCapturado(personaje.getX(), personaje.getY())){
                        puntos += pez.getPuntos();
                        peces.remove(pez);
                        break;
                    }*/

                }
                MediaPlayer mediaPlayer = MediaPlayer.create(getContext(),R.raw.carrete);
                mediaPlayer.start();
            }
        }
        return true;
    }
}
