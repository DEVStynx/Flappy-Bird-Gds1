package de.school.game.gui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Animation Object
 * <p>Examples:</p>
 * The player {@link de.school.game.entity.player.PlayerEntity Player} has two animations for each side one
 */
public class Animation extends TimerTask {
    public BufferedImage[] keyframes;
    public int keyframeCount;
    public double keyframeDelay;
    private int currentAnimIndex;
    public Timer animationTimer;
    public boolean loop;
    private boolean running;

    /**
     * The constructor
     * @param keyframes an array of {@link BufferedImage Images} as the keyframes
     * @param duration The Animation duration/delay
     * @param loop Do we need to loop the animation?
     */
    public Animation(BufferedImage[] keyframes, int duration, boolean loop) {
        this.keyframes = keyframes;
        this.keyframeCount = keyframes.length;
        this.keyframeDelay = duration;
        this.currentAnimIndex = 0;
        this.loop = loop;

        running = false;
        animationTimer = new Timer();
    }

    /**
     * A way to stop the animation if needed
     */
    public void stopAnimation() {
        animationTimer.cancel();
        running = false;
    }

    /**
     * A way to construct/create the Animation automatically by a directory
     * @param directory The {@link File directory} where all the Images are located in
     * @param duration The duration/delay of the keyframes
     * @param loop Do we need to loop the animation?
     * @return The {@link Animation Animation} as an object
     */

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

    /**
     * skips to the next Frame
     */
    protected void nextFrame() {
        if (currentAnimIndex +1 <= keyframeCount -1) {
            this.currentAnimIndex++;
            return;
        }
        currentAnimIndex = 0;
    }

    /**
     *
     * @return Returns the current Animation keyframe as a {@link BufferedImage}
     */
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
        animationTimer.scheduleAtFixedRate(this, 0, (long) keyframeDelay);
    }

    public void resumeAnimation() {
        if (!running) {
            running = true;
            animationTimer.scheduleAtFixedRate(this, 0, (long) keyframeDelay);
        }
    }

}
