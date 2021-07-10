
import java.io.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
/**
 *
 * @author brentbutkow
 */
public class makeFile {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {

		BufferedReader b[] = new BufferedReader[4];
		b[0] = new BufferedReader(new FileReader("texts/" + "Harry Potter 1 - Sorcerer's Stone.txt"));
		b[1] = new BufferedReader(new FileReader("texts/" + "Harry Potter 2 - The Chamber Of Secrets.txt"));
		b[2] = new BufferedReader(new FileReader("texts/" + "Harry Potter 3 - Prisoner of Azkaban.txt"));
		b[3] = new BufferedReader(new FileReader("texts/" + "Harry Potter 4 - The Goblet of Fire.txt"));

		BufferedWriter out = new BufferedWriter(new FileWriter("texts/" + "Harry 1-4.txt"));

		StringBuilder build = new StringBuilder();

		for (int i = 0; i < 4; i++) {

			int count = 0;
			boolean done = false;
			while (!done) {

				int c = b[i].read();

				if (c == -1) {
					done = true;
				} else {
					build.append((char) c);
					count++;
				}

				if (count >= 10000) {
					count = 0;

					out.append(build.toString());
					build.setLength(0);
				}

			}

			out.append(build.toString());
			out.append("\n\n\n");
			build.setLength(0);

		}
		out.close();
	}

}
