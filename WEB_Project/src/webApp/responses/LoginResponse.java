package webApp.responses;

public class LoginResponse {
	public boolean success;
	public String cookie;
	public String message;

	public LoginResponse(boolean success, String cookie) {
		this.success = success;
		this.cookie = cookie;
	}

	public LoginResponse(boolean success) {
		this.success = success;
		this.cookie = null;
	}

}
