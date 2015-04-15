package demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class EyeweatherRepository {
	List<Latlon> latlons;
	
	public void addLatlon(Latlon newLatlon) {
		latlons.add(newLatlon);
	}
	
	public List<Latlon> getLatlons(String userId) {
		List<Latlon> returnLatlons = new ArrayList<>();
		for (Latlon latlon : latlons) {
			if (latlon.getUserId().equals(userId))
				returnLatlons.add(latlon);
		}
		return returnLatlons;
	}

	public void delete(String userId, String latlonId) {
		for (Latlon latlon : latlons) {
			if(latlon.getUserId().equals(userId) && latlon.getId().equals(latlonId))
				latlons.remove(latlon);
		}
	}
	
}

