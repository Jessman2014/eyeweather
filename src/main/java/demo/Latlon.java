package demo;

public class Latlon {
	private String id;
	private String userId;
	private double latitude;
	private double longitude;
	private Weather weather;
	private Address address;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Weather getWeather() {
		return weather;
	}
	public void setWeather(Weather weather) {
		this.weather = weather;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	private Latlon(Builder b) {
		id = b.id;
		userId = b.userId;
		latitude = b.latitude;
		longitude = b.longitude;
		weather = b.weather;
		address = b.address;
	}
	
	public static class Builder {
		private String id;
		private String userId;
		private double latitude;
		private double longitude;
		private Weather weather;
		private Address address;
		
		public Builder id (String id) {
			this.id = id;
			return this;
		}
		public Builder userId (String userId) {
			this.userId = userId;
			return this;
		}
		public Builder latitude (double latitude) {
			this.latitude = latitude;
			return this;
		}
		public Builder longitude (double longitude) {
			this.longitude = longitude;
			return this;
		}
		public Builder weather (Weather weather){
			this.weather = weather;
			return this;
		}
		public Builder address (Address address){
			this.address = address;
			return this;
		}
		public Latlon build() {
			return new Latlon(this);
		}
	}
	
}
