package view.console;

import model.hand.Set;
import model.player.Player;
import view.listeners.CenterListener;
import view.listeners.TurnListener;

/**
 * prints the game events to the console
 * @author Zvi
 *
 */
public class ConsoleView implements CenterListener, TurnListener {

	@Override
	public void notifyTurnChanged(Player newTurn) {
		System.out.println(String.format("It's %s's turn", newTurn.name));

		if (newTurn instanceof ConsolePlayer)
			System.out.println(newTurn.getHand());

	}

	@Override
	public void notifyChange(Set newCenter) {
		System.out.print("Current center piece:");
		System.out.println(newCenter.toString());
	}

	@Override
	public void notifyPass() {
		System.out.println("Pass");
	}

	@Override
	public void notifyPlayerFinished(Player p) {
		System.out.print(p.name);
		System.out.println(" has finished his cards!");
	}
}
