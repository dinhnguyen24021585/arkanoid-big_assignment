package com.example.arkanoid;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Level {
    private int lvl;
    private int[][] isBricksShown = new int[4][10];
    private int numOfBricksToLvlUp;

    public Level() {}

    public Level(int lvl) {
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

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                if (isBricksShown[i][j] == 1) {
                    Brick brick = new NormalBrick(GameConst.BrickWidth * j, 50 + GameConst.BrickHeight * i);
                    bricks.add(brick);
                    numOfBricksToLvlUp++;
                } else if (isBricksShown[i][j] > 1 && isBricksShown[i][j] <= 3) {
                    Brick brick = new StrongBrick(GameConst.BrickWidth * j, 50 + GameConst.BrickHeight * i,
                            isBricksShown[i][j], isBricksShown[i][j]);
                    bricks.add(brick);
                    numOfBricksToLvlUp++;
                } else if (isBricksShown[i][j] == -1) {
                    Brick brick = new ExplosiveBrick(GameConst.BrickWidth * j,
                            50 + GameConst.BrickHeight * i);
                    bricks.add(brick);
                    numOfBricksToLvlUp++;
                } else if (isBricksShown[i][j] == Integer.MAX_VALUE) {
                    Brick brick = new UnbreakableBrick(GameConst.BrickWidth * j,
                            50 + GameConst.BrickHeight * i);
                    bricks.add(brick);
                }
            }
        }

        Integer numOfPowers = (int) Math.floor(Math.random() * 40);
        for (int i = 0; i < numOfPowers; i++) {
            Integer typeOfPower = (int) Math.floor(Math.random() * 2) + 1;

            Integer location = (int) Math.floor(Math.random() * 40);
            if (isBricksShown[location / 10][location % 10] != 0
                    && isBricksShown[location / 10][location % 10] != Integer.MAX_VALUE) {
                int x = (GameConst.BrickWidth - GameConst.PowerWidth) / 2
                        + GameConst.BrickWidth * (location % 10);
                int y = 50 + GameConst.BrickHeight * (location / 10);
                if (typeOfPower == 1) {
                    PowerUp powerUp = new ExpandPaddlePowerUp(x,y);
                    //kiem tra power bi chong len nhau
                    Optional.ofNullable(powerUp)
                            .filter(e -> e.getX() == x && e.getY() == y)
                            .ifPresent(powerUps::add);
                } else if (typeOfPower == 2) {
                    PowerUp powerUp = new FastBallPowerUp(x,y);
                    Optional.ofNullable(powerUp)
                            .filter(e -> e.getX() == x && e.getY() == y)
                            .ifPresent(powerUps::add);
                }
            }
        }
    }
}
