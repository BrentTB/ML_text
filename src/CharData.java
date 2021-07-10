
/** Represents a character data object.
 *  A character data object has a char value,
 *  a counter, and two probability fields. */
public class CharData {

	// a character
	char chr;

	// a counter
	int count;

	// a commulative probability (number between 0 and 1)
	double pp;

	/**
	 * Creates and initializes a character data object.
	 */
	public CharData(char chr) {
		this.chr = chr;
		this.count = 1;
		this.pp = 0;
	}

	/**
	 * Checks if the character of this CharData object equals the given
	 * character.
	 */
	public boolean equals(char chr) {
		return this.chr == chr;
	}

	/**
	 * Returns a textual representation of this CharData object.
	 */
	public String toString() {
		return "(" + chr + " " + count + " " + pp + ")";
	}

}