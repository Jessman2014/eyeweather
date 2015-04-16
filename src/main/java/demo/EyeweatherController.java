package demo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/latlon")
public class EyeweatherController {
	
	@Autowired
	private EyeweatherService eyeweatherService;
	
	@RequestMapping(value = "/users/{userid}/locations", produces = MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.GET)
	@ResponseBody
	public EyeweatherResponse get(@PathVariable String userid) {
		EyeweatherResponse hits = new EyeweatherResponse();
		
		try {
			eyeweatherService.createLatlon("Billy", "43.81", "-91.23");
			eyeweatherService.createLatlon("Billy", "39.9", "-75.2");
		} catch (URISyntaxException | IOException e1) {
			e1.printStackTrace();
		}
		
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
		eyeweatherService.deleteLatlon(userid, "43.81");
		eyeweatherService.deleteLatlon(userid, "39.9");
		return hits;
	}
	
	@RequestMapping(value="/users/{userid}/locations", method=RequestMethod.POST) 
	public void create(@PathVariable String userid, @RequestBody String lat, @RequestBody String lon) {
		try {
			eyeweatherService.createLatlon(userid, lat, lon);
		} catch (URISyntaxException | IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@RequestMapping(value="/users/{userid}/locations/{lid}", method=RequestMethod.DELETE)
	public void delete(@PathVariable String userid, @PathVariable String lid) {
		eyeweatherService.deleteLatlon(userid, lid);
	}
	
}


