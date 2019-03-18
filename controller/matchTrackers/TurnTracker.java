package controller.matchTrackers;

import java.util.ArrayList;
import java.util.List;

import model.player.Player;
import view.listeners.TurnListener;

/**
 * used for observation on the turn cycle.
 * @author Zvi
 *
 */
public class TurnTracker {

	private List<TurnListener> listeners;

	public TurnTracker() {
		listeners = new ArrayList<>();
	}

	public void addListener(TurnListener listener) {
		listeners.add(listener);
	}

	public void removeListener(TurnListener listener) {
		listeners.add(listener);
	}

	/**
	 * notify listeners the a player has finished his cards
	 * @param p - the player who finished  his cards
	 */
	public void notifyPlayerFinished(Player p) {
		for (TurnListener tl : listeners)
			tl.notifyPlayerFinished(p);
	}

	/**
	 * notify listeners that the turn has changed
	 * @param newTurn - the player who is about to start his turn
	 */
	public void notifyTurnChanged(Player newTurn) {
		for (TurnListener tl : listeners)
			tl.notifyTurnChanged(newTurn);
	}
}
