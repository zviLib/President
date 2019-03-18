package model.hand.handSorter;

import java.util.List;

import model.card.Card;
import model.hand.Set;
/**
 * used for creating a policy of how to arrange the cards in the player's hand
 * @author Zvi
 *
 */
public interface HandSorter {
	/**
	 * 
	 * @param cards - the cards to group
	 * @return the cards grouped according to the policy
	 */
	public List<Set> cardsToSets(List<Card> cards);
}
