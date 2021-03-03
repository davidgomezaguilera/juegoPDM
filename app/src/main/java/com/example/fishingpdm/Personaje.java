package com.example.fishingpdm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Personaje {
    private int x = 0;
    private int y = 0;
    private int speedY;
    private int speedX = 5;
    private juegoView view;//tenemos que pasarle el "gameView" unico del juego donde se va a dibujar
    private Bitmap bm;
    public int movX = 0;
    public int movY = 0;
    public Personaje(juegoView view) {
        this.view = view;
        this.bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.personaje);
    }

    public void cambiar(float x2, float y2){
        System.out.println("VALORES FUNCION CAMBIAR "+ x2 + " "+ y2);
        if(x>=0 && x<1080-bm.getWidth()){
            //x = x + speedX;
            //y += y2;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void onDraw(Canvas canvas){
        System.out.println("valor x " + this.getX());
        System.out.println("valor y "+ this.getY());
        System.out.println("ejex onDraw "+ x);
        System.out.println("ejey onDraw "+ y);
        canvas.drawBitmap(bm,x,y,null);
    }
}
