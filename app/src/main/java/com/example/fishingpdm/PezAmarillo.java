package com.example.fishingpdm;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class PezAmarillo {
    int[] DIRECCIONES = {3,1,0,2};

    private int x = 0;
    private int y = 0;
    private int speedY;
    private int speedX = 5;
    private juegoView view;//tenemos que pasarle el "gameView" unico del juego donde se va a dibujar
    private Bitmap bm;
    private static final int COLUMNAS_SPRITE = 3;
    private static final int FILAS_SPRITE = 4;
    private static final int VELOCIDAD_MAX= 10;
    private int puntos;

    private int frameActual = 0;
    private int ancho;
    private int alto;

    public PezAmarillo(juegoView view, Bitmap bm){
        this.view = view;
        this.bm = bm;
        this.ancho = bm.getWidth()/COLUMNAS_SPRITE;
        this.alto = bm.getHeight()/FILAS_SPRITE;
        this.puntos = 15;
        //realmente esto no haria falta ya que creariamos objetos diferentes que tendran una velocidad definida en funcion de los puntos
        //del pescado
        //en el constructor tambien le añadimos un random para que tenga velocidad diferente.
        Random random = new Random(System.currentTimeMillis());
        speedX = random.nextInt(VELOCIDAD_MAX)+1;
        speedY = random.nextInt(VELOCIDAD_MAX)+1;
        System.out.println("SPEED X AMARILLO"+ speedX);
        System.out.println("SPEED Y AMARILLO"+ speedY);;
        //speedX = VELOCIDAD_MAX;
        //speedY = VELOCIDAD_MAX;
        // Y lo mismo para una posicion diferente
        x = random.nextInt(VELOCIDAD_MAX *2) - VELOCIDAD_MAX;
        y = random.nextInt(VELOCIDAD_MAX * 2) - VELOCIDAD_MAX;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    private void actualizar(){//fisica de movimiento, es decir, los calculos
        if(x>=view.getWidth()-ancho - speedX || x + speedX <= 0){
            speedX = -speedX;
        }
        x = x + speedX;
        if(y >= view.getHeight() - alto - speedY || y + speedY <= 0){
            speedY = -speedY;
        }
        y = y + speedY;
        frameActual = ++frameActual % COLUMNAS_SPRITE;
    }

    private int obtenerAnimacion(){
        double dir = (Math.atan2(speedX,speedY)/(Math.PI / 2) + 2);
        int direccion = (int) Math.round(dir) % COLUMNAS_SPRITE;
        return DIRECCIONES[direccion];
    }

    public boolean esCapturado(float x2,float y2){
        return x2>x && x2<x+ancho && y2 > y && y2<y +alto;
    }

    public void onDraw(Canvas canvas){//su metodo ondraw que lo dibujara
        actualizar();

        int mostrarX = frameActual * ancho;
        int mostrarY = obtenerAnimacion() * alto;

        Rect mostrar = new Rect(mostrarX, mostrarY, mostrarX+ancho, mostrarY + alto); // lo que ocupa el que quiero mostrar en concreto

        Rect total = new Rect(x,y,x+ancho, y+alto);//todo lo que ocupa el sprite

        canvas.drawBitmap(bm,mostrar,total,null);
    }
}

