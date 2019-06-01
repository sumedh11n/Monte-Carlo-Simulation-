package test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;

/**
 * @author sumedh
 *
 */
public class Communicate_with_client extends Thread {

	/**
	 * @param args
	 * 
	 * 
	 */
	private static int points, job_id;
	static File myFile = null;

	Communicate_with_client(int points, int job_id) {

		this.points = points;
		this.job_id = job_id;

	}

	public void run() {
		// TODO Auto-generated method stub
		try {

			Class.forName("com.mysql.jdbc.Driver");

			String host = "jdbc:mysql://localhost/master_slave";
			String username = "root";
			String password = "root1234";
			String sendMessage;
			Statement stmt = null;
			BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
			Connection con = DriverManager.getConnection(host, username, password);
			stmt = con.createStatement();
			PreparedStatement stmt1;
			int idle_clients = 0;
			double pi = 0.0;

			Get_results_from_client p = null;

			String query = "SELECT COUNT(distinct ip_address) AS total FROM clients where status=1";
			ResultSet rs1 = stmt.executeQuery(query);
			rs1.next();

			idle_clients = rs1.getInt("total");
			int points_per_client = points / idle_clients;

			String sql = "SELECT distinct ip_address FROM clients where status=1";
			ResultSet rs = stmt.executeQuery(sql);
			String receiveMessage;
			// System.out.println("outside while");
			while (rs.next()) {

				// Retrieve by column name
				String ip = rs.getString("ip_address");
				/*
				*/
				// int port = rs.getInt("port");
				try {
					
					
					// System.out.println("inside try");
					Socket sock = new Socket(ip, 1234);

					
					String query1 = "UPDATE clients SET status = 0 WHERE ip_address = ?  ";
					stmt1 = (PreparedStatement) con.prepareStatement(query1);

					stmt1.setString(1, ip);
					stmt1.executeUpdate();
					File myFile = new File("C:\\Users\\SUMEDH\\Google Drive\\eclipse-workspace\\DPCProject\\src\\test\\Montecarlo.jar ");
					byte[] mybytearray = new byte[(int) myFile.length()];

					FileInputStream fis = new FileInputStream(myFile);
					BufferedInputStream bis = new BufferedInputStream(fis);
					bis.read(mybytearray, 0, mybytearray.length);

					OutputStream os = sock.getOutputStream();

					os.write(mybytearray, 0, mybytearray.length);
					sock.close();

					Socket sock2 = new Socket(ip, 1234);
					OutputStream ostream = sock2.getOutputStream();
					InputStream istream = sock2.getInputStream();

					PrintWriter pwrite = new PrintWriter(ostream, true);
					sendMessage = Integer.toString(points_per_client); // keyboard reading

					pwrite.println(sendMessage); // sending to server

					BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

					/*
					 * System.out.println("before");
					 * 
					 * System.out.println(receiveRead.readLine()); // displaying at DOS prompt
					 * 
					 * System.out.println("after");
					 */
					String query2= "UPDATE clients SET status = 1 WHERE ip_address = ?  ";
					stmt1 = (PreparedStatement) con.prepareStatement(query2);

					stmt1.setString(1, ip);
					stmt1.executeUpdate();
					p = new Get_results_from_client(receiveRead);

					Thread t = new Thread(p);
					t.start();

				} catch (Exception e) {
//These clients are not connected, remove them from database
					String sql1 = "DELETE FROM clients WHERE ip_address = ?";
					PreparedStatement pstmt = con.prepareStatement(sql1);
					pstmt.setString(1, ip);
					pstmt.executeUpdate();
					idle_clients = idle_clients - 1;
					//divide points among for remaining clients
					query = "SELECT COUNT(distinct ip_address) AS total FROM clients where status=1";
					ResultSet rs3 = stmt.executeQuery(query);
					rs3.next();

					idle_clients = rs1.getInt("total"); // update points per client for remaining
				 points_per_client = points / idle_clients;
					 
				}
			}
			for (int i = 0; i < idle_clients; i++)
				pi += p.get();
			System.out.println("pi value=" + pi / idle_clients + " for " + points_per_client);// average value
			// sock.close();
		} catch (Exception e) {
			System.out.println("Server has no clients");

		}

	}
}
