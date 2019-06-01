package test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Get_results_from_client extends Thread {

	/**
	 * @param args
	 */
	static Socket sock;
	static double final_val = 0;
	static BufferedReader receiveRead;

	Get_results_from_client(BufferedReader receiveRead) {
		this.receiveRead = receiveRead;
		// this.sock=sock;
		// this.ip=ip;
	}

	public void run() {
		// TODO Auto-generated method stub
		try {

			//System.out.println("BEFORE");
			System.out.println(receiveRead.readLine());
			double a = Double.parseDouble(receiveRead.readLine());

			final_val = a;
			System.out.println(final_val);

			//System.out.println("AFTER");

		} catch (Exception e) {
			//System.out.println("Server has no clients");
		}
		synchronized (this) {
			notifyAll();
		}

	}

	public synchronized double get() throws InterruptedException {
		while (final_val == 0)
			wait();

		return final_val;
	}

}
