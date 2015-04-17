package latlon;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import users.User;
import users.UserDao;

@Repository
public class EyeweatherRepository {
	@Autowired
	private UserDao userDao;
	
	List<Latlon> latlons = new ArrayList<>();
	
	public void addLatlon(Latlon newLatlon) {
		User u = userDao.getUserByUsername(newLatlon.getUserName());
		if (u != null){
			latlons.add(newLatlon);
		}
	}
	
	public List<Latlon> getLatlons(String userName) {
		User u = userDao.getUserByUsername(userName);
		if (u != null){
			List<Latlon> returnLatlons = new ArrayList<>();
			for (Latlon latlon : latlons) {
				if (latlon.getUserName().equals(userName))
					returnLatlons.add(latlon);
			}
			return returnLatlons;
		}
		return null;
	}

	public boolean delete(String userName, String latlonId) {
		User u = userDao.getUserByUsername(userName);
		if (u != null){
			for (Latlon latlon : latlons) {
				if(latlon.getUserName().equals(userName) && latlon.getId().equals(latlonId)) {
					return latlons.remove(latlon);
				}
			}
		}
		return false;
	}
	
}

