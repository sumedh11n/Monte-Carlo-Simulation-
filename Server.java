package test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.concurrent.TimeUnit;
/**
 * @author sumedh
 *
 */
 
public class Server {

	private static int points;
	static int  job_type;

	Server(int points, int jobtype) {
		this.points = points;
	
		job_type = jobtype;

	}

	public static void main(String args[]) throws InterruptedException, IOException {
		int job_type;
		int total_jobs;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		/*
		 * System.out.2
		 * 
		 * println("Do you want to add new clients to the Framework? Enter 1- Yes, 2- No"
		 * ); String client = reader.readLine(); add_a_client=Integer.parseInt(client);
		 */

		System.out.println("Enter the number of jobs to be done");
		String number_of_jobs = reader.readLine();
		total_jobs = Integer.parseInt(number_of_jobs);

		int jobs[] = new int[total_jobs];
		int points_for_MC[] = new int[total_jobs];

		int i;
		String points;

		for (i = 0; i < total_jobs; i++) {
			System.out.println("Enter the job type:  1-MonteCarlo Simulation, 2-Stock Price Prediction");
			String job = reader.readLine();
			job_type = Integer.parseInt(job);
			jobs[i] = job_type;
			if (job_type == 1) {
				System.out.println("Enter number of points for MonteCarlo Simulation");
				points = reader.readLine();
				points_for_MC[i] = Integer.parseInt(points);
			} else
				points_for_MC[i] = 0;
		}
		for (i=0; i<total_jobs;i++) {
		Create_a_client T1 = new Create_a_client();
		
			T1.start();
			TimeUnit.SECONDS.sleep(3);

			Communicate_with_client T2 = new Communicate_with_client(points_for_MC[i], jobs[i]);
			T2.start();
		}
		
		
		
	}
}