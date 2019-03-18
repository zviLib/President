import java.util.Scanner;

import controller.Game;
import model.player.SimpleAIPlayer;
import view.console.ConsolePlayer;
import view.console.ConsoleView;

/**
 * a main method to start a new game on console. reads the players information
 * from players.txt file.
 * @author Zvi
 *
 */
public class ConsoleGame {

	/**
	 * @param args - not in use
	 */
	public static void main(String[] args) {

		Scanner reader = new Scanner(System.in);

		Game game = new Game();

		// add players from file "players.txt"
		Scanner playersReader = new Scanner(ClassLoader.getSystemResourceAsStream("players.txt"));
		String line;
		String[] split;
		while (playersReader.hasNextLine()) {
			line = playersReader.nextLine();
			// skip comment lines
			if (line.charAt(0) == '*')
				continue;

			split = line.split(",");
			if (split.length < 2)
				continue;
			if (split[0].equals("PC"))
				game.addPlayer(new ConsolePlayer(split[1], reader));
			else if (split[0].equals("AI"))
				game.addPlayer(new SimpleAIPlayer(split[1]));
		}
		playersReader.close();

		if (game.numOfPlayers() < 3) {
			reader.close();
			return;
		}

		// register console to listen to game events
		ConsoleView cv = new ConsoleView();
		game.registerCenterListener(cv);
		game.registerTurnListener(cv);

		String ans = "";
		// menu loop
		while (true) {
			System.out.println("Choose Your Option:");
			System.out.println("1.Start Match");
			System.out.println("2.Exit");
			ans = reader.nextLine();

			switch (ans) {
			case "1":
				game.startNewMatch();
				break;
			case "2":
				reader.close();
				System.exit(0);
			default:
			}
		}

	}

}
