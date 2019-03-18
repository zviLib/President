package controller;

import java.util.ArrayList;
import java.util.List;

import model.player.Player;
import view.listeners.CenterListener;
import view.listeners.TurnListener;

/**
 * an upper class that holds information that effect on several matches.
 * this class is used for starting a new match
 * @author Zvi
 *
 */
public class Game {

	private ArrayList<Player> players; // list of attending players
	private Match match; // current running match
	private List<TurnListener> turnListeners; // listeners that listening on the turn cycle
	private List<CenterListener> centerListeners; // listeners that listening on the center set

	public Game() {
		players = new ArrayList<>();
		turnListeners = new ArrayList<>();
		centerListeners = new ArrayList<>();
	}

	/**
	 * add new player to the next matches
	 * @param p - the new player's information
	 */
	public void addPlayer(Player p) {
		players.add(p);
	}

	/**
	 * start a new match with the registered players
	 */
	public void startNewMatch() {
		match = new Match(players, turnListeners, centerListeners);
		match.start();
	}

	/**
	 * add a new listener to the turn cycle
	 * @param t
	 */
	public void registerTurnListener(TurnListener t) {
		turnListeners.add(t);
	}

	/**
	 * add a new listener for the center set
	 * @param c
	 */
	public void registerCenterListener(CenterListener c) {
		centerListeners.add(c);
	}

	/**
	 * @return the number of players currently enlisted
	 */
	public int numOfPlayers(){
		return players.size();
	}
}
