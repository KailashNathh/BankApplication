
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

public class TransAmt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TransAmt() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			String aNum = request.getParameter("acc");
			String apwd = request.getParameter("pwd");
			String tacno = request.getParameter("trgt");
			int tamt = Integer.parseInt(request.getParameter("tamt"));

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "Kailash@140");
			out.print("<h1 style='color red;'>" + " " + "your transfor amount is : " + tamt + "<br>");
			PreparedStatement update = con
					.prepareStatement("update account set amount=amount-? where acnumber=? and password=?");
			update.setInt(1, tamt);
			update.setString(2, aNum);
			update.setString(3, apwd);
			update.executeUpdate();
//    			----------^after update printing the amount---->
			PreparedStatement AfterTrans = con
					.prepareStatement("select amount from account where acnumber=? and password=?");
			AfterTrans.setString(1, aNum);
			AfterTrans.setString(2, apwd);
			ResultSet currentBlc = AfterTrans.executeQuery();
			java.sql.ResultSetMetaData display = currentBlc.getMetaData();
			int cnt = display.getColumnCount();
			if (currentBlc.next()) {
				for (int j = 1; j <= cnt; j++)
					out.println("Your current  account balance is: " + currentBlc.getString(j) + "</h1>");
			}
//			----------------Adding the amount to next person account-----------------
			PreparedStatement TransferAmt = con.prepareStatement("update account set amount=amount+? where acnumber=?");
			TransferAmt.setInt(1, tamt);
			TransferAmt.setString(2, tacno);

//			int updt =
			TransferAmt.executeUpdate();
//			----------^after update printing the amount---->
//			PreparedStatement depdis = con
//					.prepareStatement("select amount from account where acnumber=? and password=?");
//			depdis.setString(1, aNum);
//			depdis.setString(2, apwd);
//			ResultSet diposit = depdis.executeQuery();
//			java.sql.ResultSetMetaData display = diposit.getMetaData();
//			int cnt = display.getColumnCount();
//			if (diposit.next()) {
//				for (int j = 1; j <= cnt; j++)
//					out.println("<br>After deposit balance balance is: " + diposit.getString(j) + "</h2>");
//			}

			con.close();
		} catch (Exception e) {
			out.print(e);
		}
	}

}
