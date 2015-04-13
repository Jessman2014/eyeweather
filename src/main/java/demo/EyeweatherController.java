package demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/latlon")
public class EyeweatherController {
	
	@Autowired
	private EyeweatherService eyeweatherService;
	
	@RequestMapping(value = "/repos", produces = MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.GET)
	@ResponseBody
	public Latlon get(@RequestParam String user) {
		Latlon hit = new Latlon();
		
		List<Latlon> respositories = null;
		String status = "OK";
		try {
			respositories = eyeweatherService.getRepos(user);
		} catch(Exception e) {
			status = "ERROR due to " + e.getClass().getName();
		}
		
		response.setRepositories(respositories);		
		response.setStatus(status);
		response.setTimestamp(new Date());
		return response;
	}
}


