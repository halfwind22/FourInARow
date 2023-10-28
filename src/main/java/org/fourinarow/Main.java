package org.fourinarow;

import org.fourinarow.entity.Game;
import org.fourinarow.enums.GridPiece;
import org.fourinarow.entity.Player;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Yaml yaml = new Yaml();
        InputStream stream;
        List<Player> listOfPlayers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        try {
            stream = new FileInputStream("src/main/resources/config.yml");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> gameParameterMap = yaml.load(stream);

        Game game = new Game(
                ((Number) gameParameterMap.get("noOfRounds")).intValue(),
                ((Number) gameParameterMap.get("noOfRows")).intValue(),
                ((Number) gameParameterMap.get("noOfColumns")).intValue(),
                ((Number) gameParameterMap.get("connectN")).intValue()
        );

        for (int i = 0; i < ((Number) gameParameterMap.get("noOfPlayers")).intValue(); i++) {
            System.out.println("Please enter the name for Player " + (i + 1) + " :");
            listOfPlayers.add(new Player(scanner.next(), GridPiece.values()[listOfPlayers.size() + 1], new ArrayList<Integer>()));

        }
        game.initGame(listOfPlayers);
        game.startGame();

    }

}