package cloudcode.guestbook.frontend;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest rq, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("text/html");

            try {
                String idToken = rq.getParameter("id_token");
                GoogleIdToken.Payload payload = IDVerifier.getPayload(idToken);
                String name = (String) payload.get("Full Name");
                String email = payload.getEmail();
                System.out.println("User name: " + name);
                System.out.println("User email: " + email);

                HttpSession session = rq.getSession(true);
                session.setAttribute("Full Name", name);
                rq.getServletContext().getRequestDispatcher("home").forward(rq, resp);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }
}