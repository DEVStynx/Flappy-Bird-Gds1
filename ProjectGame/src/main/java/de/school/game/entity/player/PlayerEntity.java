package de.school.game.entity.player;

import de.school.game.Game;
import de.school.game.entity.RenderableObject;
import de.school.game.gui.Animation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class PlayerEntity extends RenderableObject {
    public Animation playerAnimation;
    public Animation playerLeftAnimation;
    public PlayerDirection direction;
    public float gravity;
    public float gravitySpeed;
    public int playerSpeedX;

    public PlayerEntity(int x, int y, int playerSpeedX) {
        super(x, y);
        playerAnimation = Animation.loadByDir(new File("Z:\\its\\Java\\IntellijWorkspaces\\ProjectGame\\src\\main\\resources\\textures\\player"),60, true);
        playerLeftAnimation = Animation.loadByDir(new File("Z:\\its\\Java\\IntellijWorkspaces\\ProjectGame\\src\\main\\resources\\textures\\player_left"),60, true);
        playerAnimation.resumeAnimation();
        playerLeftAnimation.resumeAnimation();
        direction = PlayerDirection.RIGHT;
        this.playerSpeedX = playerSpeedX;
        this.gravity = 0.05f;
        this.gravitySpeed = 1f;
    }
    public void handleMovement() {
        calcphysics();
        if (direction == PlayerDirection.LEFT) {
         x -= playerSpeedX;
         return;
        }
        x += playerSpeedX;

    }
    private void calcphysics() {
        this.gravitySpeed += this.gravity;
        if (!(y >= Game.gameWindow().getHeight())) {
            System.out.println("touched ground");
            return;
        }
        y += gravitySpeed;
    }

    @Override
    public BufferedImage getTexture() {
        if (direction == PlayerDirection.LEFT) {
            return playerLeftAnimation.getCurrentTexture();

        }
        return playerAnimation.getCurrentTexture();

    }
}
