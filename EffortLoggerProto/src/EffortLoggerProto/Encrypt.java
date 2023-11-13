package EffortLoggerProto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.scene.control.TextField;
public class Encrypt {

	public static void encrypt(String input) {

		int discardaverage=0;
		String filePath="test1.txt";
		try {
			int inputValue = Integer.parseInt(input);

			FileWriter fw = new FileWriter(filePath, true);
			fw.write(inputValue + "\n");
			fw.close();

		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public static String discradaverage()
	{ int sum = 0;
	int count = 0;
	String filepath="test1.txt";
	try {
		Scanner scan = new Scanner(new File(filepath));
		scan.useDelimiter("\n");

		while (scan.hasNext()) {
			int number = scan.nextInt();
			if (number > 10) {
				sum += number;
				count++;
			}
		}

		scan.close();
	} catch (FileNotFoundException e) {
		System.err.println("File not found: " + filepath);
	}

	int discardAverage = count > 0 ? sum / count : 0;
	return String.valueOf(discardAverage);
	}


	public static String average()
	{
		int count = 0;
		double sum = 0;
		String filepath="test1.txt";
		try {
			Scanner scan = new Scanner(new File(filepath));
			scan.useDelimiter("\n");

			while (scan.hasNext()) {
				int number = scan.nextInt();
				if (number <= 10) {
					sum += number;
					count++;
				}
			}

			// Close the scanner
			scan.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + filepath);
		}

		double average = count > 0 ? sum / count : 0;
		int finalaverage = (int) average;
		return String.valueOf(finalaverage);
	}

	public static String minimum()

	{
		String filepath = "test1.txt";

		int minimum = Integer.MAX_VALUE; // Initialize with a large value

	    try {
	        Scanner scan = new Scanner(new File(filepath));
	        scan.useDelimiter("\n");

	        while (scan.hasNext()) {
	            int number = scan.nextInt();
	            if(number >= 1 && number <= 10) {
	            minimum = Math.min(minimum, number);
	            
	            }
	        }
	        // Close the scanner
	        scan.close();
	    } catch (FileNotFoundException e) {
	        System.err.println("File not found: " + filepath);
	    }

	    return String.valueOf(minimum);
			}

	public static String maximum()

	{
		String filepath = "test1.txt";

		int maximum = 0;

		try {

			Scanner scan = new Scanner(new File(filepath));
			scan.useDelimiter("\n");


			// Read numbers from the file, line by line, and update the maximum
			while (scan.hasNext()) {
				int number = scan.nextInt();
				if(number >= 1 && number <= 10) {
				maximum = Math.max(maximum, number);
				}
			}

			// Close the scanner
			scan.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + filepath);
		}

		return String.valueOf(maximum);
	}

	public static String range()

	{
		String minimum = minimum();
		String maximum = maximum();
		return minimum + "," + maximum; 
	}

	public static void generate(TextField rangeField, TextField averageField,TextField discardAvgField, TextField lowField, TextField highField)
	{

		averageField.setText(average());
		discardAvgField.setText(discradaverage());
		lowField.setText(minimum());
		highField.setText(maximum());
		averageField.setText(average());
		rangeField.setText(range());

	}
}

