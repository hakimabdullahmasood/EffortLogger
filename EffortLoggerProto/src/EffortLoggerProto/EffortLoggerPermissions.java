package EffortLoggerProto;


public class EffortLoggerPermissions {

	private static int[] validIds = {12345678, 33601203, 52022324, 72030293};

	// outputs boolean if the inputted id is valid
	public static boolean isIdValid(String id) {
		int inputId = 0;

		// if inputId != integer
		if (id.length() == 8) {
			try {
				inputId = Integer.parseInt(id);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false; 
		}
		for (int validId : validIds) {
			if (inputId == validId) {
				return true;
			}
		}
		return false;
	}
	
	// pulls information from PasswordValidation class
	public static boolean isPasswordValid(String password) {
        return PasswordValidation.isPasswordValid(password);
    }
	
	// outputs boolean value based on whether or not user is employer/supervisor
	public static boolean isSupervisor(String id) {
		int inputId = Integer.parseInt(id)/18;
		int superKey = 685871;

		if (inputId == superKey) {
			return true;
		} else {
			return false; 
		}
	}	
}