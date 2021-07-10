
/** Tests the construction of a list of CharData objects. */
public class Test2 {

	public static void main(String args[]) {
//		testBuildList();
//		testBuildListWithProbabilities();

		testRandomCharGeneration1("qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM", 1000000);
	}

	public static void testBuildList() {
		System.out.println("Testing the construction of a list of CharData objects "
				+ "from a given string input.");
		System.out.println("The probability fields of the CharData objects will be initialized to 0.");
		String input = "committee ";
		System.out.println("Input = \"" + input + "\"");
		List q = buildList(input);
		System.out.println("List = " + q);
		System.out.println();
	}

	public static void testBuildListWithProbabilities() {
		System.out.println("Testing the construction of a list of CharData objects "
				+ "from a given string input.");
		System.out.println("This time, the probability fields will be computed and set correctly.");
		String input = "committee";
		System.out.println("Input = \"" + input + "\"");
		List q = buildList(input);
		// Calcualates the probalities
		calculateProbabilities(q);
		// Prints the list with the calculates probabilities
		System.out.println("List =  " + q);
		System.out.println();
	}

	// Builds a list of CharData objects from a given string.
	// BB
	private static List buildList(String input) {
		List q = new List();

		for (int i = 0; i < input.length(); i++) {
			q.update(input.charAt(i));
		}

		return q;

	}

	// Computes and sets the probabilities (p and pp fields) in all the
	// CharData objects in the given list.
	// BB
	public static void calculateProbabilities(List list) {

		double count = 0;

		//get the total count of characters
		for (int i = 0; i < list.getSize(); i++) {
			count += list.get(i).count;
		}

		//set p and pp
		double pphelp = 0;
		double temp;
		for (int i = 0; i < list.getSize(); i++) {
			temp = list.get(i).count / count;
//			list.get(i).p = temp;

			pphelp += temp;

			pphelp *= 100000;
			pphelp = Math.ceil(pphelp);
			pphelp /= 100000;

			list.get(i).pp = pphelp;
		}

	}

	public static void testRandomCharGeneration1(String input, int T) {
		System.out.println("Testing the generation of random characters from "
				+ "the input " + "\"" + input + "\":");
		System.out.println("Total number of trials: " + T);

		// Builds a list of CharData elements from the given input
		List q = buildList(input);
		// Calcualates the probalities
		calculateProbabilities(q);

		// Builds an array of all the CharData elements in the list
		CharData[] cds = q.toArray();

		int n = cds.length;
		int[] count = new int[n];

		for (int i = 0; i < T; i++) {
			char c = getRandomChar1(q);

			for (int j = 0; j < n; j++) {
				if (c == cds[j].chr) {
					count[j]++;
				}
			}
		}

		for (int i = 0; i < n; i++) {
			System.out.println("Number of trials that generated " + cds[i].chr + ": " + count[i]);
		}

	}

	public static char getRandomChar1(List list) {

		double r = Math.random();

		for (int i = 0; i < list.getSize(); i++) {
			if (list.get(i).pp > r) {
				return list.get(i).chr;
//				System.out.println(list.get(i).chr + "   f");
			}
		}
		return list.get(0).chr;

	}

	public static char getRandomChar2(List list) {
//		System.out.println("");

		double r = Math.random();

		int n = (int) Math.ceil(Math.log(list.getSize()) / Math.log(2));

		int left = 0, right = list.getSize() - 1;
		int mid;

		for (int i = 0; i < n; i++) {
			mid = (left + right) / 2;

			if (list.get(mid).pp < r) {

				left = mid;

			} else {
				right = mid;

			}

		}
//		System.out.println(list.get(right).chr + "   s");
		return list.get(right).chr;

//		double r = Math.random();
//
//
	}

}
