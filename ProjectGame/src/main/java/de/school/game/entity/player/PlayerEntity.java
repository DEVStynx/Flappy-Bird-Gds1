package de.school.game.entity.player;

import de.school.game.Game;
import de.school.game.entity.RenderableObject;
import de.school.game.gui.Animation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class PlayerEntity extends RenderableObject {
    public Animation playerAnimation;
    public Animation playerLeftAnimation;
    public PlayerDirection direction;
    public float gravity;
    public float gravitySpeed;
    public int playerSpeedX;
    public int jumpPower;
    public float maxgravity;

    public PlayerEntity(int x, int y, int playerSpeedX) {
        super(x, y);

        URL playerAnimationURL = PlayerEntity.class.getClassLoader().getResource("textures/player/");
        URL playerLeftAnimationURL = PlayerEntity.class.getClassLoader().getResource("textures/player_left/");


        try {
            playerAnimation = Animation.loadByDir(new File(playerAnimationURL.toURI()),60, true);
            playerLeftAnimation = Animation.loadByDir(new File(playerLeftAnimationURL.toURI()),60, true);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        playerAnimation.resumeAnimation();
        playerLeftAnimation.resumeAnimation();
        direction = PlayerDirection.RIGHT;
        this.playerSpeedX = playerSpeedX;
        this.gravity = 0.01f;
        this.gravitySpeed = 1f;
        this.jumpPower =12;
        this.maxgravity = gravitySpeed + 0.25f;
    }
    public void handleMovement() {

        calcphysics();
        if (direction == PlayerDirection.LEFT) {
            if (Game.gameCollisionManager().canPlayerMove(-playerSpeedX, 0)) {
                x -= playerSpeedX;
            } else {
                direction = PlayerDirection.RIGHT;
            }
         return;
        }
        if (Game.gameCollisionManager().canPlayerMove(playerSpeedX, 0)) {
            x += playerSpeedX;
        } else {
            direction = PlayerDirection.LEFT;
        }



    }
    private void calcphysics() {
        this.gravitySpeed += this.gravity;
        if (this.gravitySpeed > maxgravity) {
            this.gravitySpeed = maxgravity;
        }
        if (y + gravitySpeed >= Game.gameWindow().getHeight() - Game.gameWindow().tileSize + 3) {
            //Touched Ground
            return;
        }
        if (Game.gameCollisionManager().canPlayerMove(0, (int) gravitySpeed)) {
            y += gravitySpeed;
            return;
        }

    }

    @Override
    public BufferedImage getTexture() {
        if (direction == PlayerDirection.LEFT) {
            return playerLeftAnimation.getCurrentTexture();

        }
        return playerAnimation.getCurrentTexture();

    }
}
