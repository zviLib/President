package controller.matchControllers;

import java.util.List;

import controller.Game;
import controller.matchTrackers.TurnTracker;
import model.collections.Cycle;
import model.player.Player;
import view.listeners.TurnListener;

/**
 * used to control the turns cycle. this is the only class which allowed to
 * determine who will play next.
 * 
 * @author Zvi
 *
 */
public class TurnController {

	private int turnMovement; // the position of the next player in respect of
								// the current player
	private Cycle<Player> inGame; // the player's turn cycle
	private TurnTracker tracker; // used to communicate with the turn listeners

	/**
	 * @param players
	 *            - list of the players who play the match
	 * @param starter
	 *            - the player who will play the first turn
	 */
	public TurnController(List<Player> players, Player starter) {
		inGame = new Cycle<>(players);
		tracker = new TurnTracker();
		inGame.setHead(starter);
		turnMovement = 0;
	}

	/**
	 * add a new listener to this controller's tracker
	 * 
	 * @param listener
	 */
	public void addListener(TurnListener listener) {
		tracker.addListener(listener);
	}

	/**
	 * remove listener from this controller's tracker
	 * 
	 * @param listener
	 */
	public void removeListener(TurnListener listener) {
		tracker.addListener(listener);
	}

	/**
	 * set the progress of the turns according to res.
	 * 
	 * @param res
	 *            - the result of the selected set
	 */
	public void setMovment(TurnResult res) {
		switch (res) {
		case skip:
			turnMovement = 2;
			break;
		case clear:
			turnMovement = 0;
			break;
		case none:
		case pass:
		case error:
		default:
			turnMovement = 1;
			break;
		}
	}
	public void removePlayer(Player p) {
		inGame.remove(p);
		turnMovement--;

		tracker.notifyPlayerFinished(p);
	}

	/**
	 * @return the player who's turn is it currently
	 */
	public Player getCurrentTurn() {
		return inGame.getHead();
	}

	/**
	 * @return the player who follows the current turn player
	 */
	public Player getFollowingPlayer() {
		return inGame.getNext();
	}

	/**
	 * move the turn according to the action that was selected selected
	 */
	public void confirm() {
		for (int i = 0; i < turnMovement; i++) {
			inGame.forwardHead();
		}

		Player current = inGame.getHead();
		// notify listeners
		tracker.notifyTurnChanged(current);
	}

	/**
	 * @return true if there are enough players to continue playing with
	 */
	public boolean continueGame() {
		return inGame.size() > 1;
	}
}
