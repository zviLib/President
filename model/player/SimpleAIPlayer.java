package model.player;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import model.card.Card;
import model.card.Rank;
import model.hand.CardSelectionModel;
import model.hand.Hand;
import model.hand.Set;
import model.hand.handSorter.HandSorter;
import model.hand.handSorter.SimpleHandSorter;

/**
 * A simple logic player
 * 
 * @author Zvi
 *
 */
public class SimpleAIPlayer extends Player {

	private HandSorter handSorter;
	private Hand hand;

	public SimpleAIPlayer(String name) {
		super(name);
		handSorter = new SimpleHandSorter();
		hand = new Hand();
	}

	@Override
	public Hand getHand() {
		return hand;
	}

	@Override
	protected HandSorter getHandSorter() {
		return handSorter;
	}

	@Override
	public void switchCards(CardSelectionModel opp) {

		// set to send lowest ranked cards to the opponent
		LinkedList<Card> send = new LinkedList<>();
		hand.sort();
		for (Card c : hand) {
			if (c.rank == Rank.Two)
				continue;
			send.add(c);
			if (send.size() > opp.getNumOfPicks())
				break;
		}

		while (opp.getNumOfPicks() > 0) {
			// try get opponent's 2's
			if (opp.hasRank(Rank.Two)) {
				Card replacement = send.pop();
				hand.removeCard(replacement);
				hand.addCard(opp.getCard(Rank.Two, replacement));
			}
			// else - get highest card available
			else {
				for (byte j = (byte) Rank.Joker.ordinal(); j > Rank.Two.ordinal(); j--) {
					if (opp.hasRank(Rank.fromNum(j))) {
						Card replacement = send.pop();
						hand.removeCard(replacement);
						hand.addCard(opp.getCard(Rank.Two, replacement));
						break;
					}
				}
			}

		}
	}

	private Set calcNextMove(Set center) {
		List<Card> cards = hand.getCardsCopy();
		List<Set> sets = handSorter.cardsToSets(cards);

		Collections.sort(cards);
		Collections.sort(sets);

		// get lowest set that fits, also collect jokers and twos
		Set jokers = null, two = null;
		for (Set s : sets) {
			if (s.rank == Rank.Two)
				two = s;
			else if (s.rank == Rank.Joker)
				jokers = s;
			else if (s.covers(center))
				return s;
		}

		// try to use joker to complete set
		if (jokers != null) {
			for (Set s : sets) {
				// check if added jokers are enough to cover current center set
				if (s.rank.compareTo(center.rank) >= 0 && s.size() + jokers.size() >= center.size()) {
					s.addToSize(jokers, center.size());
					return s;
				}
			}
		}

		// try to use twos to clear board
		if (two != null)
			return two;

		// no possible move - return null
		return null;

	}

	@Override
	public Set selectNextMove(Set center) {
		// get next move
		Set selected = calcNextMove(center);

		if (selected != null)
			for (Card c : selected)
				hand.removeCard(c);

		return selected;
	}
}
