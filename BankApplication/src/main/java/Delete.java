
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Delete() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			String acc = request.getParameter("acc");
			String name = request.getParameter("name");
			String pwd = request.getParameter("pwd");

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "Kailash@140");
			PreparedStatement psmt = con.prepareStatement("Delete from account where acnumber=? and Password=?");
			psmt.setString(1, acc);
			psmt.setString(2, pwd);
			psmt.executeUpdate();
			out.print("<h1 style='color:red;'>Mr./Ms " + name + "Your Account has been CLOSED" + "</h1>");
		} catch (Exception e) {
			out.print(e);
		}
	}

}
