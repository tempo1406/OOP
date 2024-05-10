package Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Validation {
	
	public static boolean isValidDate(String dateString, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            sdf.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
	
	public static Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = dateFormat.parse(dateString);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public static boolean isAgeValid(String birthDateStr, String testDateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date birthDate = dateFormat.parse(birthDateStr);
            Date testDate = dateFormat.parse(testDateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(birthDate);
            int birthYear = calendar.get(Calendar.YEAR);
            calendar.setTime(testDate);
            int testYear = calendar.get(Calendar.YEAR);
            return (testYear - birthYear) >= 18;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValidGender(String gender) {
        return gender != null && (gender.equals("NAM") || gender.equals("NU"));
    }

    public static boolean isValidExamCode(String code) {
    	if (!code.matches("^TSDH-\\d{4}-KV-\\d{3}$")) {
            return false;
        }
        String examNumber = code.substring(code.length() - 3);
        return examNumber.equals("001") || examNumber.equals("002");
    }

    public static boolean isValidBlock(String block) {
    	return block.equals("A0") || block.equals("B0") || block.equals("C0") || block.equals("D0");
    }


    public static boolean isValidSubject(String block, String s1, String s2, String s3, String s4, String s5, String s6,
			String s7, String s8) {
		if (block.equals("A0")) {
			return Validation.isNumericDouble(s1) && Validation.isNumericDouble(s2) && Validation.isNumericDouble(s3)
					&& s4.equals("N/A") && s5.equals("N/A") && s6.equals("N/A") && s7.equals("N/A") && s8.equals("N/A");
		} else if (block.equals("B0")) {
			return Validation.isNumericDouble(s1) && Validation.isNumericDouble(s3) && Validation.isNumericDouble(s6)
					&& s2.equals("N/A") && s4.equals("N/A") && s5.equals("N/A") && s7.equals("N/A") && s8.equals("N/A");
		} else if (block.equals("C0")) {
			return Validation.isNumericDouble(s4) && Validation.isNumericDouble(s7) && Validation.isNumericDouble(s8)
					&& s1.equals("N/A") && s2.equals("N/A") && s3.equals("N/A") && s5.equals("N/A") && s6.equals("N/A");
		} else if (block.equals("D0")) {
			return Validation.isNumericDouble(s1) && Validation.isNumericDouble(s4) && Validation.isNumericDouble(s5)
					&& s2.equals("N/A") && s3.equals("N/A") && s6.equals("N/A") && s7.equals("N/A") && s8.equals("N/A");
		} else {
			return false;
		}
	}

    public static boolean isNumericDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isNumericInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
