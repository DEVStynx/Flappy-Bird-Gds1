package de.school.game.gui;

import de.school.game.Game;
import de.school.game.clock.GameClock;

import javax.imageio.ImageIO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Timer;
import java.util.TimerTask;

public class Animation extends TimerTask {
    public BufferedImage[] keyframes;
    public int keyframeCount;
    public double keyframeduration;
    private int currentAnimIndex;
    public Timer animationTimer;
    public boolean loop;
    private boolean running;


    public Animation(BufferedImage[] keyframes, int duration, boolean loop) {
        this.keyframes = keyframes;
        this.keyframeCount = keyframes.length;
        this.keyframeduration = duration;
        this.currentAnimIndex = 0;
        this.loop = loop;

        running = false;
        animationTimer = new Timer();
    }

    public void stopAnimation() {
        animationTimer.cancel();
        running = false;
    }


    public static Animation loadByDir(File directory, int duration, boolean loop) {
       if (!directory.exists() || directory == null) {
           return null;
       }
       if (directory.isFile()) {
           try {
               BufferedImage[] texture = {ImageIO.read(directory)};
               return new Animation(texture,duration, loop);
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

       int length = directory.listFiles().length;
       BufferedImage[] textures = new BufferedImage[length];
       BufferedImage temptex;
       int ctr = 0;
       for (File texture : directory.listFiles()) {
           try {
               temptex = ImageIO.read(texture);
               textures[ctr] = temptex;
               ctr++;
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       return new Animation(textures,duration, loop);
    }

    protected void nextFrame() {
        if (currentAnimIndex +1 <= keyframeCount -1) {
            this.currentAnimIndex++;
            return;
        }
        currentAnimIndex = 0;
    }

    public BufferedImage getCurrentTexture() {
        if (currentAnimIndex > keyframeCount -1) {
            currentAnimIndex = 0;
        }
        return this.keyframes[currentAnimIndex];
    }





    @Override
    public void run() {
        nextFrame(); // Nächsten Frame aufrufen
        //System.out.println("Current Texture: " + currentAnimIndex);
        if (!loop && this.currentAnimIndex == this.keyframeCount - 1) {
            stopAnimation(); // Animation stoppen, wenn nicht im Loop
        }
    }

    public void startAnimation() {
        currentAnimIndex = 0;
        running = true;
        // Timer regelmäßig alle keyframeduration Millisekunden auslösen
        animationTimer.scheduleAtFixedRate(this, 0, (long) keyframeduration);
    }

    public void resumeAnimation() {
        if (!running) {
            running = true;
            animationTimer.scheduleAtFixedRate(this, 0, (long) keyframeduration);
        }
    }

}
