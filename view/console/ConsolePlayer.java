package view.console;

import java.util.ArrayList;
import java.util.Scanner;

import model.card.Card;
import model.card.Rank;
import model.hand.CardSelectionModel;
import model.hand.Hand;
import model.hand.Set;
import model.hand.handSorter.HandSorter;
import model.hand.handSorter.SimpleHandSorter;
import model.player.Player;

/**
 * a player that gets input through the console
 * 
 * @author Zvi
 *
 */
public class ConsolePlayer extends Player {

	private HandSorter handSorter;
	private Hand hand;
	private Scanner reader; // reads input from console

	/**
	 * @param name        - the player's name
	 * @param inputReader - scanner that reads input from the console
	 */
	public ConsolePlayer(String name, Scanner inputReader) {
		super(name);
		handSorter = new SimpleHandSorter();
		hand = new Hand();
		reader = inputReader;
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

		// save number of swithes entitled
		int switches = opp.getNumOfPicks();

		// print hand
		System.out.println("Your hand: " + hand.toString());

		Rank r1 = null, r2 = null;

		// get from the users the cards that he wants to pick from the opponent
		while (true) {
			if (switches == 1) { // request to input a wanted card
				System.out.print("Enter wanted card:");
				r1 = Rank.fromSign(reader.nextLine());
				// Validate input
				if (r1 == null)
					System.out.println("Invaild input");
				else if (opp.hasRank(r1))
					break;
				else
					System.out.println("Opponent do not have such card");
			} else if (switches == 2) { // request to input a wanted pair
				System.out.print("Enter wanted cards:");
				String[] split = reader.nextLine().split(",");
				r1 = Rank.fromSign(split[0].trim());
				r2 = Rank.fromSign(split[1].trim());
				// Validate input
				if (r1 == null || r2 == null)
					System.out.println("Invaild input");
				else if ((r1 == r2 && opp.hasPair(r1)) || (r1 != r2 && opp.hasRank(r1) && opp.hasRank(r2)))
					break;
				else
					System.out.println("Opponent do not have such pair");
			}
		}

		// replace the selected cards with cards from the user's hand
		Card c1 = null, c2 = null;
		while (true) {
			if (switches == 1) {
				// read the replacement card from user
				System.out.print("Enter card to send:");
				c1 = hand.getRank(Rank.fromSign(reader.nextLine()));
				// Validate input
				if (c1 == null)
					System.out.println("Invaild input");
				else
					break;
			} else if (switches == 2) {
				// read the replacement cards from user
				System.out.print("Enter cards to send:");
				String[] split = reader.nextLine().split(",");
				c1 = hand.getRank(Rank.fromSign(split[0].trim()));
				c2 = hand.getRank(Rank.fromSign(split[1].trim()));

				// Validate input
				if (c1 == null || c2 == null)
					System.out.println("Invaild input");
				else
					break;
			}
		}

		// switch cards
		if (c1 != null && r1 != null) {
			hand.removeCard(c1);
			hand.addCard(opp.getCard(r1, c1));

		}
		if (c2 != null && r2 != null) {
			hand.removeCard(c2);
			hand.addCard(opp.getCard(r2, c2));
		}
	}

	@Override
	public Set selectNextMove(Set center) {
		hand.sort();
		String input;
		String[] split;
		while (true) {
			// read wanted move
			System.out.print("Enter next move:");
			input = reader.nextLine();
			if (input.equals("pass"))
				return null;
			split = input.split(",");
			ArrayList<Card> cards = new ArrayList<>();
			// search for cards in hand
			boolean invalid = false;
			for (int i = 0; i < split.length; i++) {
				Card c = hand.getRank(Rank.fromSign(split[i].trim()));
				// if input is invalid \ card not found
				if (c == null) {
					invalid = true;
					cards.forEach(hand::addCard);
					break;
				} else {
					cards.add(c);
					hand.removeCard(c);
				}
			}

			// if input is invalid
			if (invalid) {
				System.out.println("Invalid Input");
				continue;
			}
			// if input is not a legal move
			Set move = new Set(cards);
			if (move.rank == Rank.Error || !move.covers(center)) {
				System.out.println("Illegal Move");
			} else
				return move;
		}

	}

}
