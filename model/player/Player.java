package model.player;

import model.card.Card;
import model.hand.CardSelectionModel;
import model.hand.Hand;
import model.hand.Set;
import model.hand.handSorter.HandSorter;

/**
 * a model that holds the player's strategy and information
 * @author Zvi
 *
 */
public abstract class Player implements Comparable<Player> {
	private byte score; // the player's position in the last game
	public final String name; // the player's name

	public Player(String name) {
		this.name = name;

		// new players will be ranked as last
		score = Byte.MAX_VALUE;
	}

	/**
	 * @param c - a card to add to the player's hand
	 */
	public void addCard(Card c) {
		getHand().addCard(c);
	}

	/**
	 * @return the player's position in the last game
	 */
	public byte getScore() {
		return score;
	}

	/**
	 * @param score - the position of the player in the current game
	 */
	public void setScore(byte score) {
		this.score = score;
	}

	/**
	 * @return the player's hand
	 */
	public abstract Hand getHand();

	/**
	 * @return the policy of how the player arranges his hand
	 */
	protected abstract HandSorter getHandSorter();

	/**
	 * let the player pick cards from the opponent's hand
	 * @param opp
	 */
	public abstract void switchCards(CardSelectionModel opp);

	/**
	 * @param center - the current center set
	 * @return the set that player wants to put at the center, null for pass.
	 */
	public abstract Set selectNextMove(Set center);

	@Override
	public int compareTo(Player o) {
		return score - o.score;
	}

}
