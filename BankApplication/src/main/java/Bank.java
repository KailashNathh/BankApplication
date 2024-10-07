import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Bank extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Bank() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			String aNum = request.getParameter("acc");
			String aName = request.getParameter("name");
			String apwd = request.getParameter("pwd");
			String aAmt = request.getParameter("amount");
			String aadrs = request.getParameter("adrs");
			String aMbl = request.getParameter("mbln");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "Kailash@140");
			PreparedStatement ps = con.prepareStatement("insert into account values(?,?,?,?,?,?)");
			ps.setString(1, aNum);
			ps.setString(2, aName);
			ps.setString(3, apwd);
			ps.setString(4, aAmt);
			ps.setString(5, aadrs);
			ps.setString(6, aMbl);

			int i = ps.executeUpdate();
			if (i >= 0) {
				out.print("<h2>New Account has been created</h2>");
			} else {
				out.print("Regristration is Failed");
			}
			con.close();
		} catch (Exception e) {
			out.print(e);
		}
	}
}
