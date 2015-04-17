/** Allows all enabled users to log in and routes
 * them based on their roles. Previously logged in
 * users will not have to log in again if session
 * is saved.
 * @author Jesse Dahir-Kanehl
 */

package users;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/usercredentials", method=RequestMethod.POST)
	public String userCredentials(@RequestParam(value="username", required=true) String username, 
						@RequestParam(value="password", required=true) String password,
						HttpSession session,
						HttpServletResponse response
						) throws IOException {
		User user = userService.getUser(username, password);
		if (user != null) {
			session.setAttribute("user", user);
			return "redirect:/eyeweather.html?username=" + user.getUsername();
		} else {
			return "redirect:/login.html?error=true";
		}
	}
			
	@RequestMapping(value={"/"}, method=RequestMethod.GET)
	public String redirectLogin(HttpSession session,
			@RequestParam(value="error", required=false, defaultValue="false") Boolean error) {
		User user = (User)session.getAttribute("user");
		if (user == null) {
			return "redirect:/login.html?error=" + error;
		}
		return "redirect:/eyeweather.html?username=" + user.getUsername();
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		session.setAttribute("user",  null);
		return "redirect:/login.html";
	}
}
