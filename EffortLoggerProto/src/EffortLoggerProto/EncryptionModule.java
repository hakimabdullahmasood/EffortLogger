package EffortLoggerProto;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

// whole file made by JT McClellan
public class EncryptionModule {

	// sets up private scanner
	private static Scanner x;

	// function that checks inputted password (which becomes hashed in driver class) to stored hashed passwords and
	// stores it into tmp.txt before placing it back in file with original credentials.txt name 
	public static void storeHashedPasswordToFile(String filepath, String username, String password, String hash) {
		String tempFile = "temp.txt";
		File oldFile = new File(filepath);
		File newFile = new File(tempFile);

		// attempts to read file for hashed password
		try {
			FileWriter fw = new FileWriter(tempFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			x = new Scanner(new File(filepath));
			x.useDelimiter("[,\n]");

			// while loop that continues till file has no more data
			while (x.hasNext()) {
				String t1 = x.next();
				String testPass = x.next().trim();

				// if hashed-inputted-password = 
				if(password.trim().equals(testPass)) {
					pw.println(t1 + "," + hash);
				} else {
					pw.println(t1 + "," + testPass);
				}
			}

			x.close();
			pw.flush();
			pw.close();
			oldFile.delete();
			File dump = new File(filepath);
			newFile.renameTo(dump);
		} catch (IOException e) {
			System.err.println("An error occurred while writing to the file: " + e.getMessage());
		}
	}

	// completely works // made by JT McClellan //
	public static boolean fileReadAndValidation(String username, String password, String filepath) {
		boolean found = false;
		String tempUser = "";
		String tempPassword = "";

		// trys to find inputted password in credentials.txt
		// if it isnt found (doesnt exist), returns false
		try {
			x = new Scanner(new File(filepath));
			x.useDelimiter("[,\n]");

			while(x.hasNext() && !found) {
				tempUser = x.next();
				tempPassword = x.next();
				if(tempUser.trim().equals(username.trim()) && tempPassword.trim().equals(password.trim())) {
					found = true;
				}
			}
			x.close();
		} catch(Exception e) {
			System.out.println("An error has occured, please try again.");
		}
		return found;
	}
}