
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class wthdraw extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public wthdraw() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			int addamt = Integer.parseInt(request.getParameter("wdamt"));
			String aNum = request.getParameter("acc");
			String apwd = request.getParameter("pwd");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "Kailash@140");
			// --------------------^printing current and deposite
			// current-->amount----------------------------->
//			PreparedStatement ps = con.prepareStatement("select  amount from account where acnumber=? and Password=?");
//			ps.setString(1, aNum);
//			ps.setString(2, apwd);
//
////			ResultSet rs =
//			ps.executeQuery();
////			java.sql.ResultSetMetaData rss = rs.getMetaData();
////			int n = rss.getColumnCount();
////			if (rs.next()) {
////				for (int j = 1; j <= n; j++)
////					out.println("<h2 >Existing balance is: " + rs.getString(j) + "<br>");
////			}
			out.print("Your With-Draw amount  is :" + addamt);
//    			----------update amount----------------------->
			PreparedStatement update = con
					.prepareStatement("update account set amount=amount-? where acnumber=? and password=?");
			update.setInt(1, addamt);
			update.setString(2, aNum);
			update.setString(3, apwd);
			update.executeUpdate();
//    			----------^after update printing the amount---->
			PreparedStatement depdis = con
					.prepareStatement("select amount from account where acnumber=? and password=?");
			depdis.setString(1, aNum);
			depdis.setString(2, apwd);
			ResultSet currentBlc = depdis.executeQuery();
			java.sql.ResultSetMetaData display = currentBlc.getMetaData();
			int cnt = display.getColumnCount();
			if (currentBlc.next()) {
				for (int j = 1; j <= cnt; j++)
					out.println("<br>Your current  account balance is: " + currentBlc.getString(j) + "</h2>");
			}

			con.close();

		} catch (Exception e) {
			out.print(e);
		}
	}

}
