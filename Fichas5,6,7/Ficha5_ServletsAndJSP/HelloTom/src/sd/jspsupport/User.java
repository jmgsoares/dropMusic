package sd.jspsupport;

public class User {
	private String name;
	private String password;
	public User(String n, String p) {
		name = n;
		password = p;
	}
	
	public void setName(String n) {
		name = n;
	}
	public void setPassword(String p) {
		password = p;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String toString() {
		return name;
	}
}
