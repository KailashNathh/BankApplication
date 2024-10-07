
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

public class Balance extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Balance() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			String aNum = request.getParameter("acc");
			String apwd = request.getParameter("pwd");
			String nme = request.getParameter("name");

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "Kailash@140");
			PreparedStatement ps = con.prepareStatement(
					"select acnumber,name,amount,address,mbno from account where acnumber=? and Password=?");
			ps.setString(1, aNum);
			ps.setString(2, apwd);

			ResultSet rs = ps.executeQuery();
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			int n = rsmd.getColumnCount();
			out.print("<h1 style='color:green'>Welcome" + " " + nme + "</h1>");
			out.print("<html><table border='2'>");
			for (int i = 1; i <= n; i++)
				out.print("<td>" + rsmd.getColumnName(i));
			out.print("<tr>");
			if (rs.next()) {
				for (int j = 1; j <= n; j++)
					out.print("<td><br>" + rs.getString(j));
			}
			out.print("</tr>");
			out.print("</table></html>");
			con.close();

		} catch (Exception e) {
			out.print(e);
		}
	}
}