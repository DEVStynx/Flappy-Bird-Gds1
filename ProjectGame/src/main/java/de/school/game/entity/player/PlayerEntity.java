package de.school.game.entity.player;

import de.school.game.Game;
import de.school.game.entity.RenderableObject;
import de.school.game.entity.TileObject;
import de.school.game.gui.Animation;
import de.school.game.gui.world.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
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
    public float jumpvelocity;
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

        //Constants
        direction = PlayerDirection.RIGHT;
        this.playerSpeedX = playerSpeedX;
        this.gravity = 0.01f;
        this.gravitySpeed = 1f;
        this.jumpPower =12;
        this.maxgravity = gravitySpeed + 0.25f;

    }

    public void deletePlayer() {
        playerAnimation.stopAnimation();
        playerLeftAnimation.stopAnimation();
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(x,y,playerAnimation.getCurrentTexture().getWidth(),playerAnimation.getCurrentTexture().getHeight());
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
    public void jump() {
        int totalJumpHeight = jumpPower * 10;

        for (int i = 0; i < totalJumpHeight; i++) {
            // Checke für jeden Pixel, ob eine Bewegung möglich ist
            if (Game.gameCollisionManager().canPlayerMove(0, -1)) {
                y -= 1;
            } else {
                // Oben kollidiert – abbrechen
                break;
            }
        }

        // Reset gravity, damit er wieder ordentlich fällt
        gravitySpeed = 1f;
    }


    @Override
    public BufferedImage getTexture() {
        if (direction == PlayerDirection.LEFT) {
            return playerLeftAnimation.getCurrentTexture();

        }
        return playerAnimation.getCurrentTexture();

    }
}
