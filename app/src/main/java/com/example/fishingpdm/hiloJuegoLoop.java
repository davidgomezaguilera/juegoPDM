package com.example.fishingpdm;

import android.graphics.Canvas;

public class hiloJuegoLoop extends Thread{

    private juegoView view;
    private boolean running = false;
    private final long FPS = 30;
    public hiloJuegoLoop(juegoView view){
        this.view = view;
    }
    public void setRunning(boolean run){
        running = run;
    }

    @Override
    public void run() {

        long ticks = 1000/FPS;
        long start;
        long sleepT;

        while(running){
            Canvas c = null;
            start = System.currentTimeMillis();//cogemos el tiempo antes de empezar
            try{
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()){
                    view.onDraw(c);
                }
            }catch(Exception e){

            }finally{
                if(c!=null){
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepT = ticks - (System.currentTimeMillis()-start);// tiempo que pasa hasta que se vuelva a dibujar otra vez

            try{
                if(sleepT > 0 ){
                    sleep(sleepT);
                }else{
                    sleep(10);
                }
            }catch(Exception e){

            }
        }
    }
}
