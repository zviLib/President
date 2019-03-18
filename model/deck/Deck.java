package model.deck;

import java.util.ArrayList;
import java.util.Collections;

import model.card.Card;
import model.card.Rank;
import model.card.Suit;

/**
 * a class that handles a simple playing cards deck
 * @author Zvi
 *
 */
public class Deck {
	private ArrayList<Card> deck;

	/**
	 * initialize a normal 54 cards deck.
	 */
	public Deck() {
		deck = new ArrayList<>();

		Rank[] ranks = Rank.values();
		Suit[] suits = Suit.values();

		// add four types of all cards except joker and error
		for (int i = 0; i < ranks.length - 2; i++) {
			for (int j = 0; j < suits.length; j++) {
				deck.add(new Card(ranks[i], suits[j]));
			}
		}
		// add two jockers
		deck.add(new Card(Rank.Joker, Suit.Heart));
		deck.add(new Card(Rank.Joker, Suit.Spade));
	}

	/**
	 * shuffles the deck.
	 */
	public void shuffle() {
		Collections.shuffle(deck);
	}

	/**
	 * @return the card that on top of the deck
	 */
	public Card draw() {
		if (deck.isEmpty())
			return new Card(Rank.Error, Suit.Club);

		Card card = deck.get(0);
		deck.remove(card);
		return card;
	}

	/**
	 * @return true if all the cards were taken
	 */
	public boolean isEmpty() {
		return deck.isEmpty();
	}
}
