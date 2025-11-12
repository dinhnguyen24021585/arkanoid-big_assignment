package com.example.arkanoid.GameElements;

import com.example.arkanoid.*;
import com.example.arkanoid.GameElements.Bricks.*;
import com.example.arkanoid.GameElements.Bricks.factories.*;
import com.example.arkanoid.PowerUps.PortalPowerUp;
import com.example.arkanoid.PowerUps.BalancedMultiballPowerUp;
import com.example.arkanoid.PowerUps.ExpandPaddlePowerUp;
import com.example.arkanoid.PowerUps.FastBallPowerUp;
import com.example.arkanoid.PowerUps.HeartPowerUp;
import com.example.arkanoid.PowerUps.PowerUp;
import com.example.arkanoid.PowerUps.ReverseControlPowerUp;
import com.example.arkanoid.PowerUps.ShootingPowerUp;
import com.example.arkanoid.PowerUps.factories.*;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Level {
    private int lvl;
    private int[][] isBricksShown = new int[8][10];
    private int numOfBricksToLvlUp;
    private Map<Integer, BrickFactory> brickFactories = new HashMap<>();
    private Map<Integer,PowerUpFactories> powerFactories = new HashMap<>();

    public Level() {}

    public Level(int lvl) {
        initializeFactories();
        this.lvl = lvl;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getNumOfBricksToLvlUp() {
        return numOfBricksToLvlUp;
    }

    public void setNumOfBricksToLvlUp(int numOfBricksToLvlUp) {
        this.numOfBricksToLvlUp = numOfBricksToLvlUp;
    }

    private void initializeFactories() {
        //brick factory
        brickFactories.put(1, new NormalBrickFactory());
        brickFactories.put(2, new StrongBrickFactory());
        brickFactories.put(3, new StrongBrickFactory());
        brickFactories.put(-1, new ExplosiveBrickFactory());
        brickFactories.put(Integer.MAX_VALUE, new UnbreakableBrickFactory());

        //power up factory
        powerFactories.put(1,new ExpandPaddleFactory());
        powerFactories.put(2,new FastBallFactory());
        powerFactories.put(3,new HeartFactory());
        powerFactories.put(4,new MultiBallFactory());
        powerFactories.put(5,new ShootingFactory());
        powerFactories.put(6,new ReverseFactory());
        powerFactories.put(7,new PortalFactory());

    }


    public void loadLevel(ArrayList<Brick> bricks, ArrayList<PowerUp> powerUps)
            throws IOException, URISyntaxException {
        List<String> lines = Files.readAllLines(Paths.get(getClass().
                getResource("/com/example/arkanoid/Levels/level" + this.lvl + ".txt").toURI()));
        for (int i = 0; i < lines.size(); i++) {
            Scanner scanner = new Scanner(lines.get(i));
            int k = 0;
            while (scanner.hasNextInt()) {
                isBricksShown[i][k] = scanner.nextInt();
                k++;
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                if (brickFactories.containsKey(isBricksShown[i][j])) {
                    bricks.add(brickFactories.get(isBricksShown[i][j]).createBrick(GameConst.BrickWidth * j,
                            50 + GameConst.BrickHeight * i, isBricksShown[i][j]));
                }
            }
        }

        int numOfPowers = (int) Math.floor(Math.random() * 10) + 10;
        for (int i = 0; i < numOfPowers; i++) {
            int typeOfPower = (int) Math.floor(Math.random() * 7) + 1;

            int location = (int) Math.floor(Math.random() * 80);
            if (isBricksShown[location / 10][location % 10] != 0
                    && isBricksShown[location / 10][location % 10] != Integer.MAX_VALUE) {
                int x = (GameConst.BrickWidth - GameConst.PowerWidth) / 2
                        + GameConst.BrickWidth * (location % 10);
                int y = 50 + GameConst.BrickHeight * (location / 10);

                AtomicBoolean overlap = new AtomicBoolean(false);
                powerUps.forEach(powerUp -> {
                    if (powerUp.getX() == x && powerUp.getY() == y) {
                        overlap.set(true);
                    }
                });

                if (!overlap.get() && powerFactories.containsKey(typeOfPower)) {
                    System.out.println(typeOfPower);
                    powerUps.add(powerFactories.get(typeOfPower).createPowerUp(x, y));
                }
            }
        }
    }
}
