/** Reads a list of users from a csv database file
 *  and stores in a list in memory. Allows querying
 *  of that list.
 * @author Jesse Dahir-Kanehl
 */

package users;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	private String fileDir = "C:/Users/Administrator/Documents/users.db";
	private Map<String, User> users = new HashMap<String, User>();
	
	@PostConstruct
	public void init() {
		List<String[]> userStringList = getUsersStrings();
		for (String[] strings : userStringList) {
			User u = new User.Builder().id(strings[0]).email(strings[1]).firstName(strings[2])
					.lastName(strings[3]).username(strings[4]).password(strings[5]).build();
			users.put(u.getUsername(), u);
		}
	}
	
	private List<String[]> getUsersStrings() {
		List<String[]> userStringList = new ArrayList<String[]>();
		try {
			//inputStream = servletContext.getResourceAsStream("/WEB-INF/content/users.db");
			BufferedReader br = new BufferedReader(new FileReader(fileDir));
			String line = br.readLine();
			while(line != null) {
				String[] userString = line.split(",");
				userStringList.add(userString);
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return userStringList;
	}
	
	public User getUserByUsername(String username) {
		return users.get(username);
	}

}
