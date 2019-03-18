package model.hand;

import model.card.Card;
import model.card.Rank;
/**
 * lets the selecting player switch card(s) with his underlying player without revealing the entire opponent's hand
 * @author Zvi
 *
 */
public class CardSelectionModel {

	private Hand hand; // the opponent's hand
	private int numOfPicks; // the number of switches that the player can still use

	/**
	 * 
	 * @param hand - the hand to select from
	 * @param numOfPicks - the number of switches the selecting player is entitled of
	 */
	public CardSelectionModel(Hand hand, int numOfPicks) {
		this.hand = hand;
		this.numOfPicks = numOfPicks;
	}

	/**
	 * 
	 * @param r - the rank in question
	 * @return true if the hand has this rank
	 */
	public boolean hasRank(Rank r) {
		for (Card c : hand)
			if (c.rank == r)
				return true;
		return false;
	}

	/**
	 * @param r - the rank of the pair in question
	 * @return true if the hand has a pair of the rank
	 */
	public boolean hasPair(Rank r) {
		boolean first = true;
		for (Card c : hand)
			if (c.rank == r) {
				if (first)
					first = false;
				else
					return true;
			}

		return false;
	}

	/**
	 * @param r - the rank of the wanted card
	 * @param replacement - the card that will replace the selected card on the opponent's hand
	 * @return card from the opponent's hand with the requested rank
	 */
	public Card getCard(Rank r, Card replacement) {
		// if already used all picks
		if (numOfPicks == 0)
			return null;

		Card ret = null;

		// get wanted card
		for (Card c : hand)
			if (c.rank == r) {
				ret = c;
				break;
			}

		if (ret != null) {
			hand.removeCard(ret);
			hand.addCard(replacement);
			numOfPicks--;
		}

		return ret;
	}
	
	/**
	 * @return the number of switches that the player can still use
	 */
	public int getNumOfPicks(){
		return numOfPicks;
	}
}
