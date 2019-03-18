package model.card;

public enum Rank {
	Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace, Joker, Error;

	@Override
	public String toString() {
		// single letter number
		if (this.ordinal() < Ten.ordinal()) {
			return Character.toString((char) (this.ordinal() + 50));
		}
		// double letter number
		if (this == Ten)
			return "10";
		// royalty
		if (this.ordinal() < Joker.ordinal())
			return this.name().substring(0, 1);
		// joker
		if (this == Joker)
			return this.name().substring(0, 2);
		// error
		return "ER";
	}

	/**
	 * reverse method of toString
	 * @param sign - the toString representation of the wanted rank
	 * @return the wanted rank
	 */
	public static Rank fromSign(String sign) {
		for (Rank r : values()) {
			if (r.toString().equalsIgnoreCase(sign))
				return r;
		}

		return null;
	}

	/**
	 * @param num - the numeric value of the card in game
	 * @return the appropriate enum
	 */
	public static Rank fromNum(byte num) {
		try {
			return values()[num - 2];
		} catch (ArrayIndexOutOfBoundsException e) {
			return Error;
		}
	}
}
