package EffortLoggerProto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class PasswordValidation {

	// hashes inputted password with SHA-256 algorithm // made by JT McClellan
	public static String hashPassword(String userId, String password) {
		try {
			// Create a MessageDigest instance for the SHA-256 algorithm
			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			// Update the digest with the password bytes
			byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

			// Convert the hash bytes to a hexadecimal representation
			StringBuilder hexString = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			// inputs hashed password as data value in to storeHashedPasswordToFile function
			EncryptionModule.storeHashedPasswordToFile("credentials.txt", userId, password, hexString.toString());
			
			// returns hashed password
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			// Handle the exception appropriately (e.g., log or rethrow)
			throw new RuntimeException("SHA-256 algorithm is not available.", e);
		}
	}

	// boolean operator to output whether password is strong enough
	public static boolean isPasswordValid(String password) {
		final int MIN_LENGTH = 6;
		boolean hasUppercase = false;
		boolean hasLowercase = false;
		boolean hasDigit = false;
		boolean hasSpecialChar = false;

		if (password.length() < MIN_LENGTH) {
			return false;
		}


		for (char c : password.toCharArray()) {
			if (Character.isUpperCase(c)) {
				hasUppercase = true;
			} else if (Character.isLowerCase(c)) {
				hasLowercase = true;
			} else if (Character.isDigit(c)) {
				hasDigit = true;
			} else if (isSpecialCharacter(c)){
				hasSpecialChar = true;
			}
		}

		return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
	}

	// boolean operator to check if element of array is a special character
	private static boolean isSpecialCharacter(char c) {
		String specialCharacters = "!@#$%^&*()";
		return specialCharacters.indexOf(c) != -1;
	}

}