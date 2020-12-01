package Operator;

import java.util.regex.Pattern;

public class Password {

	private static final int LENGTH = 8;
	
	private String password;
	
	public Password(String password) {
		this.password = password;
	}
	
	public void change(String new_password) {
		
		if (canChange(new_password))
			password = new_password;
	}
	
	public boolean equals(String value) {
		return password.equals(value);
	}
	
	public boolean canChange(String new_password) {
		if (new_password.length() < LENGTH)
			return false;
		System.out.printf("%b %b\n", checkNumber(new_password), checkSpecial(new_password));
		if (!checkNumber(new_password))
			return false;
		if (!checkSpecial(new_password))
			return false;
		return true;
	}
	
	private boolean checkNumber(String value) {
		Pattern pattern = Pattern.compile("[0-9]");
		return pattern.matcher(value).find();
	}
	
	private boolean checkSpecial(String value) {
		Pattern pattern = Pattern.compile("[~!@#$%^&*()_+|<>?:{}]");
		return pattern.matcher(value).find();
	}
}
