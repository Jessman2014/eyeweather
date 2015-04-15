package demo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
			eyeweatherService.createLatlon("billy", 43.81, -92.23);
			eyeweatherService.createLatlon("billy", 39.9, -75.2);
		} catch (URISyntaxException | IOException e1) {
			// TODO Auto-generated catch block
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
}


