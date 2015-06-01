package Classes;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IsLoggedIn {
	public static long getUserId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Object userid = null;
		// prvo baraj go vo session
		userid = request.getSession().getAttribute("user");
		// ako nema baraj go vo cookie
		if (userid == null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					Cookie cookie = cookies[i];
					if (cookie.getName().equals("user")) {
						userid = cookie.getValue();
					}
				}
			}
		}
		if (userid == null)
			return -1;
		else
			return Long.parseLong(userid.toString());
	}
}