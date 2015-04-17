package users;

public class User {
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String id;
	private String password;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	private User(Builder b) {
		this.firstName = b.firstName;
		this.lastName = b.lastName;
		this.username = b.username;
		this.email = b.email;
		this.id = b.id;
		this.password = b.password;
	}
	
	@Override
	public String toString() {
		return "User [name=" + firstName + " " + lastName + ", id=" + id + ", username="+ username + ", email="+ email + ", password=" + password + "]";
	}

	public static class Builder {
		private String firstName;
		private String lastName;
		private String email;
		private String username;
		private String id;
		private String password;
		
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		
		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		
		public Builder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		
		public Builder username(String username) {
			this.username = username;
			return this;
		}
		
		public Builder email(String email) {
			this.email = email;
			return this;
		}
		
		public Builder password(String password) {
			this.password = password;
			return this;
		}
		
		public User build() {
			return new User(this);
		}
	}
	
}
