package model.hand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import model.card.Card;
import model.card.Rank;

/**
 * a simple model that is used for maintaining a list of cards
 * 
 * @author Zvi
 *
 */
public class Hand implements Iterable<Card> {
	private List<Card> cards;

	public Hand() {
		this.cards = new LinkedList<>();
	}

	/**
	 * @return a copy of the cards in hand
	 */
	public List<Card> getCardsCopy() {
		while (cards.remove(null))
			;
		return new ArrayList<>(cards);
	}

	/**
	 * @return true if the hand holds no cards
	 */
	public boolean isEmpty() {
		return cards.isEmpty();
	}

	/**
	 * @param c - a card to add to the hand
	 */
	public void addCard(Card c) {
		cards.add(c);
	}

	/**
	 * @param c - a card to remove from the hand
	 */
	public void removeCard(Card c) {
		cards.remove(c);
	}

	/**
	 * @param r - the rank of the wanted card
	 * @return a card from the hand carrying the wanted rank, null if no such card
	 *         found
	 */
	public Card getRank(Rank r) {
		if (r == null)
			return null;

		for (Card c : cards)
			if (c.rank == r)
				return c;
		
		return null;
	}

	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}

	/**
	 * sort the cards according to their rank
	 */
	public void sort() {
		while (cards.remove(null))
			;
		Collections.sort(cards);
	}

	@Override
	public String toString() {

		ArrayList<Card> cardsCopy = new ArrayList<>(cards);
		Collections.sort(cardsCopy);

		if (cards.size() == 0)
			return "empty";

		StringBuilder sb = new StringBuilder("");
		for (Card c : cardsCopy)
			sb.append(c.toString() + ",");

		return sb.toString();
	}
}
