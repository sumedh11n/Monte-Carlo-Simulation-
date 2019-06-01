package test;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * @author sumedh
 *
 */

public class Create_a_client extends Thread
{
	 public void run()
  {
		 try {
int port = 2345;

Class.forName("com.mysql.jdbc.Driver");

	String host="jdbc:mysql://localhost/master_slave";
String username="root";
String password="root1234";
PreparedStatement stmt = null;
Socket client=null;
Connection con = DriverManager.getConnection( host, username, password );
// Create a server socket associated with port 1234
ServerSocket server = new ServerSocket(port,10);
while(true) {
 client = server.accept();
 InetAddress ip=client.getInetAddress();
 String ip_adr=ip.toString();
 //String client_socket=client.toString();
 ip_adr= ip_adr.replaceAll("/","");
 int client_port=client.getPort();
 String query = " insert into clients (ip_address, status,port)"
	        + " values (?, ?,?)";
 stmt = (PreparedStatement) con.prepareStatement(query);
 // A client connected++
 System.out.println("Client " + client.getInetAddress() + " added successfully!!");
 // Server receives bytes from client
  stmt.setString(1,ip_adr);
  stmt.setInt(2,1);
  stmt.setInt(3, client_port);
  //stmt.setString(4,client_socket);  
		stmt.executeUpdate();
	}
}
		 catch(Exception e){
			    //Handle errors for Class.forName
				System.out.println("");
			}
  }
}


