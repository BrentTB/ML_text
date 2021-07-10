
import java.io.*;
import java.util.HashMap;
import java.util.Random;

/*
 * A random text generator. The program "trains" itself by reading and analysing
 * character probabilitie in a given text, and then generates random text that
 * is "similar" to the given text.
 */
public class TextGen {

	// Length of the moving window
	private static int windowLength;

	// A map for managing the (window, list) mappings
	private static HashMap<String, List> map;

	// Random number generator, used by the getRandomChar method.
	private static Random randomGenerator;

	private static void type() throws IOException {

		BufferedReader b = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Sample text name (eg: harry.txt):\n");
		String fileName = b.readLine();

		System.out.println("Window length:\n");
		int windowLength = Integer.parseInt(b.readLine());

		System.out.println("The way the output should start (please make sure the input is as long as or longer than the window length):\n");
		String initialText = b.readLine();

		System.out.println("Generated text length (50 means 50 characters):\n");
		int generatedTextLength = Integer.parseInt(b.readLine());

		System.out.println("Do you want to use a seed (allows for repeating of outcome) (y/n):\n");
		String rand = b.readLine();

		while (!rand.equalsIgnoreCase("y") && !rand.equalsIgnoreCase("n")) {
			System.out.println("please enter y or n:\n");
			rand = b.readLine();
		}

		int seed;
		if (rand.equalsIgnoreCase("y")) {

			System.out.println("Please type the seed:\n");
			seed = Integer.parseInt(b.readLine());
		} else {
			seed = 0;
		}
		init(windowLength, (rand.equalsIgnoreCase("y")), fileName, seed);

		// Creates the map (implemented as a global, class-level variable).
		train();

		// Uses the map for generating random text, and prints the text.
		String generatedText = generate(initialText, generatedTextLength);
		System.out.println(generatedText);
	}

	private static void setHere() {
//		String fileName = "Harry 1-4.txt";
		String fileName = "soccer.txt";

		String initialText = "Zonal Marking";

		int windowLength = Math.min(initialText.length(), 10);
//		int windowLength = initialText.length();

		int generatedTextLength = 2500;

		String useSeed = "n";

		int seed = 20;

		init(windowLength, (useSeed.equalsIgnoreCase("y")), fileName, seed);

		// Creates the map (implemented as a global, class-level variable).
		train();

		// Uses the map for generating random text, and prints the text.
		String generatedText = generate(initialText, generatedTextLength);
		System.out.println(generatedText);

//		System.out.println("");
//		System.out.println(mapString());
	}

	private static void commandLine(String[] args) {
		String fileName = args[0];

		int windowLength = Integer.parseInt(args[1]);

		String initialText = args[2];

		int generatedTextLength = Integer.parseInt(args[3]);

		String rand = args[4];

		int seed = Integer.parseInt(args[5]);

		init(windowLength, (rand.equalsIgnoreCase("y")), fileName, seed);

		// Creates the map (implemented as a global, class-level variable).
		train();

		// Uses the map for generating random text, and prints the text.
		String generatedText = generate(initialText, generatedTextLength);
		System.out.println(generatedText);
	}

	// implement a way to start without setting the startcommand, by looking through the windows and choosing a random word after a full stop and space (start of a new line
	public static void main(String[] args) throws IOException {

//		type();
		setHere();
//		commandLine(args);
	}

	// Initializes the training and text generation processes
	private static void init(int length, boolean useSeed, String fileName, int seed) {
		windowLength = length;
		map = new HashMap<String, List>();
		StdIn.setInput("texts/" + fileName);
		if (useSeed) {

			randomGenerator = new Random(seed);
		} else {
			randomGenerator = new Random();
		}
	}

	/**
	 * Trains the model, creating the map.
	 */
	public static void train() {

		String window = "";
		char c;

		//create the first window
		for (int i = 0; i < windowLength; i++) {
			window += StdIn.readChar();
		}
		window = window.replace("\n", " ");
		List temp = new List();//because its the first window, it will need a new entry in map
		c = StdIn.readChar();
		temp.update(c);

		map.put(window, temp);

		// Processes the entire text, one character at a time
		while (!StdIn.isEmpty()) {

			//window shifts one character, so it will be itself minus its first character, plus the next character, which will be c
			window = window.substring(1, windowLength) + c;
//			window = window.replace("\n", " ");
			c = StdIn.readChar();

			if (c == '\n') {
				c = ' ';
			}

			temp = (map.get(window) != null) ? map.get(window) : new List();

			temp.update(c);
			map.put(window, temp);

		}

		// Computes and sets the p and pp fields of all the
		// CharData objects in each list in the map.
		for (List list : map.values()) {
			calculateProbabilities(list);
		}
	}

	// Computes and sets the probabilities (p and pp fields) of all the
	// characters in the given list. */
	private static void calculateProbabilities(List list) {

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

	public static String generate(String initialText, int textLength) {

		StringBuilder build = new StringBuilder();
		int count = 0;
		int returnPoint = 70;

		String window = initialText;

		build.append(window);
		count += window.length();
		window = window.substring(window.length() - windowLength, window.length());//in case the initial text is too long
		char c = 'a';
		int length = 0;
		while (length < textLength || c != '.') {
			c = getRandomChar(map.get(window));
			build.append(c);
			window = window.substring(1, windowLength) + c;

			count++;

			if (count > returnPoint && c == ' ') {
				count = 0;
				build.append("\n");
			}
			length++;
		}

		/// Replace the following statement with your code
		return build.toString();
	}

	// Returns a random character from a given list of CharData objects,
	// using Monte Carlo.
	private static char getRandomChar(List list) {

		double r = randomGenerator.nextDouble();

		//Using binary search - better with large sizes of list, but because the max list will be around 60 with it usually at 1, this isnt worth it
//		int n = (int) Math.ceil(Math.log(list.getSize()) / Math.log(2));
//
//		int left = 0, right = list.getSize() - 1;
//		int mid;
//
//		for (int i = 0; i < n; i++) {
//			mid = (left + right) / 2;
//
//			if (list.get(mid).pp < r) {
//
//				left = mid;
//
//			} else {
//				right = mid;
//
//			}
//
//		}
//		return list.get(right).chr;
		try {

			for (int i = 0; i < list.getSize(); i++) {
				if (list.get(i).pp > r) {
					return list.get(i).chr;
				}
			}

			return list.get(0).chr;

		} catch (NullPointerException e) {
			return ' ';

		}
	}

	/**
	 * Generates a string representation of the map, for debugging purposes.
	 */
	public static String mapString() {
		StringBuilder ans = new StringBuilder();
		for (String key : map.keySet()) {
			ans.append(key + ": " + map.get(key) + "\n");
		}
		return ans.toString();
	}

}
