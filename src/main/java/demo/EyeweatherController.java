package demo;

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
	public EyeweatherResponse get(@PathVariable String user) {
		EyeweatherResponse hits = new EyeweatherResponse();
		
		List<Latlon> latlons = null;
		String status = "OK";
		try {
			latlons = eyeweatherService.getLatlons(user);
		} catch(Exception e) {
			status = "ERROR due to " + e.getClass().getName();
		}
		
		hits.setLatlons(latlons);
		hits.setStatus(status);
		hits.setTimestamp(new Date());
		return hits;
	}
}


