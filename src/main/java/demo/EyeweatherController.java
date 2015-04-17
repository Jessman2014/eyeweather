package demo;

import java.util.Date;
import java.util.List;

import latlon.EyeweatherService;
import latlon.Latlon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EyeweatherController {
	
	@Autowired
	private EyeweatherService eyeweatherService;
	
	@RequestMapping(value = "/users/{userid}/locations", produces = MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.GET)
	@ResponseBody
	public EyeweatherResponse get(@PathVariable String userid) {
		EyeweatherResponse hits = new EyeweatherResponse();
		
		List<Latlon> latlons = null;
		String status = "OK";
		try {
			latlons = eyeweatherService.getLatlons(userid);
		} catch(Exception e) {
			status = "ERROR due to " + e.getClass().getName();
		}
		
		hits.setLatlons(latlons);
		hits.setStatus(status);
		hits.setTimestamp(new Date());
		return hits;
	}
	
	@RequestMapping(value="/users/{userid}/locations", produces = MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.POST) 
	public EyeweatherResponse create(@PathVariable String userid, @RequestBody String lat, @RequestBody String lon) {
		String[] latLonArray = lat.split("&");
		lat = latLonArray[0].split("=")[1];
		lon = latLonArray[1].split("=")[1];
		
		eyeweatherService.createLatlon(userid, lat, lon);
		
EyeweatherResponse hits = new EyeweatherResponse();
		
		List<Latlon> latlons = null;
		String status = "OK";
		try {
			latlons = eyeweatherService.getLatlons(userid);
		} catch(Exception e) {
			status = "ERROR due to " + e.getClass().getName();
		}
		
		hits.setLatlons(latlons);
		hits.setStatus(status);
		hits.setTimestamp(new Date());
		return hits;
	}
	
	@RequestMapping(value="/users/{userid}/locations/{lid}", produces = MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.DELETE)
	public EyeweatherResponse delete(@PathVariable String userid, @PathVariable String lid) {
		eyeweatherService.deleteLatlon(userid, lid);
		
EyeweatherResponse hits = new EyeweatherResponse();
		
		List<Latlon> latlons = null;
		String status = "OK";
		try {
			latlons = eyeweatherService.getLatlons(userid);
		} catch(Exception e) {
			status = "ERROR due to " + e.getClass().getName();
		}
		
		hits.setLatlons(latlons);
		hits.setStatus(status);
		hits.setTimestamp(new Date());
		return hits;
	}
	
}


