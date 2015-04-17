package demo;

import java.util.Date;
import java.util.List;

import latlon.Latlon;

public class EyeweatherResponse {
	private Date timestamp;
	private String status;
	private List<Latlon> latlons;
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Latlon> getLatlons() {
		return latlons;
	}
	public void setLatlons(List<Latlon> latlons) {
		this.latlons = latlons;
	}
}
