package model.card;

/**
 * A basic playing card model
 * @author Zvi
 *
 */
public class Card implements Comparable<Card> {
	public final Rank rank;
	public final Suit suit;

	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	@Override
	public int compareTo(Card o) {
		if (this.rank != o.rank)
			return this.rank.ordinal() - o.rank.ordinal();

		return this.suit.ordinal() - o.suit.ordinal();
	}

	@Override
	public String toString() {
		return rank.toString();
	}

}
