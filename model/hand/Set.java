package model.hand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import model.card.Card;
import model.card.Rank;

/**
 * used for creating sub-sets of cards
 * 
 * @author Zvi
 *
 */
public class Set implements Iterable<Card>, Comparable<Set> {
	public final Rank rank; // the ranks of the cards - can be set to error if there is no leading rank
	private List<Card> cards; // the cards in the set

	/**
	 * @param cards - the cards of the set
	 */
	public Set(List<Card> cards) {

		this.cards = cards;
		this.rank = findRank(cards);
	}

	private Set() {
		rank = null;
		cards = new ArrayList<>();
	}

	/**
	 * duplicate method.
	 * 
	 * @param other - the set to duplicate
	 */
	public Set(Set other) {
		this.rank = other.rank;
		cards = new ArrayList<>(other.cards);
	}

	/**
	 * @return an empty set
	 */
	public static Set emptySet() {
		return new Set();
	}

	/**
	 * @param cards - the cards in question
	 * @return the leading rank of the list, error if there is no such rank
	 */
	private Rank findRank(List<Card> cards) {

		if (cards == null || cards.size() == 0)
			return Rank.Error;

		// make sure that normal cards appear before jokers
		Collections.sort(cards);

		Rank rank = cards.get(0).rank;

		for (Card c : cards) {
			if (c.rank != rank && c.rank != Rank.Joker)
				return Rank.Error;

		}

		return rank;
	}

	/**
	 * @return the number of cards in this set
	 */
	public int size() {
		return cards.size();
	}

	/**
	 * @param center - current center set
	 * @return whether putting this set on center is a valid move
	 */
	public boolean covers(Set center) {
		// if center is empty
		if (center.rank == null)
			return true;

		// if this is a cut set
		if (this.rank == Rank.Two)
			return true;

		if (this.size() != center.size() || this.rank.ordinal() < center.rank.ordinal())
			return false;

		return true;
	}

	/**
	 * used to buff a set with jokers
	 * 
	 * @param other - set of jokers
	 * @param size  - wanted set size
	 */
	public void addToSize(Set other, int size) {

	}

	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}

	@Override
	public int compareTo(Set arg0) {
		return rank.compareTo(arg0.rank);
	}

	@Override
	public String toString() {

		if (cards.size() == 0)
			return "empty";

		StringBuilder sb = new StringBuilder("");
		for (Card c : cards)
			sb.append(c.toString() + ",");

		return sb.toString();
	}

}
