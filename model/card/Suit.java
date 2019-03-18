package model.card;

public enum Suit {
	Heart, Spade, Diamond, Club;

	@Override
	public String toString() {
		return this.name().substring(0, 1);
	}
}
