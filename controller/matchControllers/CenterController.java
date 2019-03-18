package controller.matchControllers;

import controller.matchTrackers.CenterTracker;
import model.card.Rank;
import model.hand.Set;
import model.player.Player;
import view.listeners.CenterListener;

/**
 * Controls the center set. It's the only class that allowed to change it
 * 
 * @author Zvi
 */
public class CenterController {

	private Set current; // current center
	private Player lastPlayed; // the player who've put the set
	private byte stright; // the sum of straight cards from the same rank that
							// was played on a row
	private CenterTracker tracker;
	private boolean removed; // if the player that put the center set has
								// finished his cards

	public CenterController() {
		current = Set.emptySet();
		stright = 0;
		lastPlayed = null;
		tracker = new CenterTracker();
		removed = false;
	}

	/**
	 * add a new listener to this controller's tracker
	 * 
	 * @param listener
	 */
	public void addListener(CenterListener listener) {
		tracker.addListener(listener);
	}

	/**
	 * remove listener from this controller's tracker
	 * 
	 * @param listener
	 */
	public void removeListener(CenterListener listener) {
		tracker.removeListener(listener);
	}

	/**
	 * @param other
	 *            - a requested set
	 * @return true if the set can be put on the center piece
	 */
	public boolean coveredBy(Set other) {
		return other.covers(current);
	}

	/**
	 * Used to set a new center piece. change only made is the move is valid.
	 * 
	 * @param s
	 *            - new suggested set
	 * @param owner
	 *            - the player who've offered this set
	 * @return the result of this move on the turns cycle
	 */
	public TurnResult put(Set s, Player owner) {
		TurnResult tr;
		// check if move is valid
		if (s == null || !s.covers(current)) {
			tracker.notifyPass();
			tr = TurnResult.pass;
		}
		// check for a four set completion
		else if (s.rank == current.rank || s.size() == 4) {
			if (stright + s.size() == 4) {
				current = s;
				clear();
				tr = TurnResult.clear;
			} else { // Accumulate input
				stright += s.size();
				current = s;
				tr = TurnResult.skip;
			}
			// if move is a clear card
		} else if (s.rank == Rank.Two || s.rank == Rank.Joker) {
			current = s;
			clear();
			tr = TurnResult.clear;
		} else { // normal move
			current = s;
			stright = (byte) s.size();
			tr = TurnResult.none;
		}

		if (tr != TurnResult.pass) {
			// notify listeners about the set change
			tracker.notifySetChanged(current);
			// set the owner of the center
			lastPlayed = owner;
		}

		return tr;
	}

	/**
	 * clear the center piece.
	 */
	private void clear() {
		current = Set.emptySet();
		stright = 0;
		tracker.notifySetChanged(current);
	}

	/**
	 * clear the board if the center piece was put by the current player
	 * 
	 * @param turn
	 *            - the player who's turn is about to start
	 */
	public void checkForCycle(Player turn) {
		// if center piece completed a cycle
		if (lastPlayed != null && turn == lastPlayed && !removed)
			clear();
		else if (removed)
			removed = false;
	}

	/**
	 * @return a copy of the center piece
	 */
	public Set copyCenter() {
		return new Set(current);
	}

	/**
	 * @return true if the center is currently empty
	 */
	public boolean isEmpty() {
		return current.rank == null;
	}

	/**
	 * moves the ownership of the center set to player p.
	 * used when the current owner is out of the game.
	 * 
	 * @param next
	 *            - the next player on the cycle
	 */
	public void changeCenterOwner(Player p) {
		lastPlayed = p;
		removed = true;
	}
}
