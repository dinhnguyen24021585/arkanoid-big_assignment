package com.example.arkanoid;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Level {
    private int level;
    private int[][] isBricksShown = new int[4][10];

    public Level(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void loadLevel(ArrayList<Brick> bricks) throws IOException, URISyntaxException {
        List<String> lines = Files.readAllLines(Paths.get(getClass().getResource("/com/example/arkanoid/Levels/level" + this.level + ".txt").toURI()));
        for (int  i = 0; i < lines.size(); i++) {
            Scanner scanner = new Scanner(lines.get(i));
            int k = 0;
            while (scanner.hasNextInt()) {
                isBricksShown[i][k] = scanner.nextInt();
                k++;
            }
        }

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 10; j++) {
                if (isBricksShown[i][j] >= 1) {
                    Brick brick = new Brick(80 * j, 30 * i, 80, 30, isBricksShown[i][j], isBricksShown[i][j]);
                    bricks.add(brick);
                }
            }
        }
    }
}
