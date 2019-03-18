package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.matchControllers.CenterController;
import controller.matchControllers.TurnController;
import controller.matchControllers.TurnResult;
import model.card.Card;
import model.deck.Deck;
import model.hand.CardSelectionModel;
import model.hand.Set;
import model.player.Player;
import view.listeners.CenterListener;
import view.listeners.TurnListener;

/**
 * This class is in charge of running a single match
 * @author Zvi
 *
 */
public class Match {

	private List<Player> players; // list of competing players
	private byte score; // the score of the next player who will empty his hand
	private TurnController turn; // in charge of controlling the turn cycle
	private CenterController center; // in charge of the center set

	/**
	 * @param players - the players competing in this match
	 * @param turnListeners - listeners for the turn cycle
	 * @param centerListeners - listeners for the center set
	 */
	protected Match(List<Player> players, List<TurnListener> turnListeners, List<CenterListener> centerListeners) {
		this.players = players;
		score = 1;

		// initialize turn controller
		turn = new TurnController(players, findStarter());
		// register listeners
		for (TurnListener tl : turnListeners)
			turn.addListener(tl);

		// initialize center controller
		center = new CenterController();
		// register listeners
		for (CenterListener tl : centerListeners)
			center.addListener(tl);
	}

	/**
	 * start the match.
	 */
	public void start() {
		// split cards to players
		splitCards();

		// switch cards according to rank
		if (!firstMatch())
			switchCards();

		// reset last game places
		for (Player p : players)
			p.setScore(Byte.MAX_VALUE);

		// play match
		runMatch();
	}

	/**
	 * give the players their initial cards
	 */
	private void splitCards() {
		Deck deck = new Deck();
		deck.shuffle();

		int count = 0;
		int playersNum = players.size();
		Card draw;
		while (!deck.isEmpty()) {
			draw = deck.draw();
			players.get(count % playersNum).addCard(draw);
			count++;
		}
	}

	/**
	 * The player who've ranked last on the previous game will start the new game.
	 * @return this match's starting player
	 */
	private Player findStarter() {
		/// sort players according to last game positions
		ArrayList<Player> sorted = new ArrayList<>(players);
		Collections.sort(sorted);

		// return the last placed player
		return sorted.get(sorted.size() - 1);
	}

	/**
	 * The player who've finished first is taking two cards from the last ranked player,
	 * The player who've finished second is taking one card from the second-to-last ranked player.
	 */
	private void switchCards() {
		/// sort players according to last game positions
		ArrayList<Player> sorted = new ArrayList<>(players);
		Collections.sort(sorted);

		int size = sorted.size();
		// first and last places switching two cards
		sorted.get(0).switchCards(new CardSelectionModel(sorted.get(size - 1).getHand(), 2));
		// vice from both sides switching one card
		if (size > 3)
			sorted.get(1).switchCards(new CardSelectionModel(sorted.get(size - 1).getHand(), 1));
	}

	/**
	 * run the match until only one player is left with cards on his hand
	 */
	private void runMatch() {
		Set selected;
		Player current;
		score = 1;
		while (turn.continueGame()) {
			// move turn to next player
			turn.confirm();
			current = turn.getCurrentTurn();

			// check if center piece has completed a cycle
			if (!center.isEmpty())
				center.checkForCycle(current);

			// let player select next move
			selected = current.selectNextMove(center.copyCenter());

			// validate and apply move
			TurnResult tr = center.put(selected, current);

			// set next player according to the turn result
			turn.setMovment(tr);

			// check if current player has finished his cards
			if (current.getHand().isEmpty()) {
				// set the player's position
				current.setScore(score);
				score++;
				// remove player from the turn cycle
				turn.removePlayer(current);
				// move the center ownership to the next player
				center.changeCenterOwner(turn.getFollowingPlayer());
			}
		}

		// set remaining player's score
		turn.getCurrentTurn().setScore(score);
	}

	/**
	 * if this is the first match, no card switching happens
	 * @return true if this is the first match
	 */
	private boolean firstMatch() {
		for (Player p : players)
			if (p.getScore() != Byte.MAX_VALUE)
				return false;

		return true;
	}
}
