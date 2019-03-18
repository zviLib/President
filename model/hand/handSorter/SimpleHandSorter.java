package model.hand.handSorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.card.Card;
import model.card.Rank;
import model.hand.Set;
/**
 * groups the cards so every card will have the same rank as all other cards in his set
 * @author Zvi
 *
 */
public class SimpleHandSorter implements HandSorter {

	@Override
	public List<Set> cardsToSets(List<Card> cards) {

		ArrayList<ArrayList<Card>> split = new ArrayList<>();

		// open a list for every available rank
		for (int i = 0; i < Rank.values().length; i++)
			split.add(new ArrayList<>());

		// add all ranks to corresponding list
		for (Card c : cards)
			if (c != null)
				split.get(c.rank.ordinal()).add(c);

		List<Set> sets = new ArrayList<>();

		// make set of every list, and put all twos separated
		for (List<Card> list : split)
			if (list.size() > 0)
				if (list.get(0).rank != Rank.Two) {
					sets.add(new Set(list));
				} else {
					for (Card c : list)
						sets.add(new Set(Arrays.asList(c)));
				}

		return sets;
	}

}
