package demo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
	private String id;
	private int winds;
	private int temp;
	private int relh;
	private String weather;
	@JsonProperty("text")
	private List<String> forecast;
	private Date date;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getWinds() {
		return winds;
	}
	public void setWinds(int winds) {
		this.winds = winds;
	}
	public int getTemp() {
		return temp;
	}
	public void setTemp(int temp) {
		this.temp = temp;
	}
	public int getRelh() {
		return relh;
	}
	public void setRelh(int relh) {
		this.relh = relh;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public void setForecast(List<String> forecast) {
		this.forecast = forecast;
	}
	public List<String> getForecast() {
		return forecast;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
