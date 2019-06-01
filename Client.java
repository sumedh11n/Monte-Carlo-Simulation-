package test;
import java.net.*;   
import java.io.*;   
/**
 * @author sumedh
 *
 */
public class Client{    
   
  public static void main (String[] args ) throws IOException {   
     Socket sock = new Socket("localhost", 2345);//Enter Server IP here

		sock.close();

    int bytesRead;
    int current = 0;
 
    ServerSocket serverSocket = null;
    serverSocket = new ServerSocket(1234);
 
        Socket clientSocket = null;
		 Socket clientSocket1 = null;
		 		 Socket clientSocket2 = null;
		 		 Socket clientSocket3= null;

		 while(true){
        clientSocket = serverSocket.accept();
         
        InputStream in = clientSocket.getInputStream();
         
        // Writing the file to disk
        // Instantiating a new output stream object
        OutputStream output = new FileOutputStream("C:\\Users\\mariu\\Desktop\\MonteCarlo.jar");
           
        byte[] buffer = new byte[1024];
        while ((bytesRead = in.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        // Closing the FileOutputStream handle
		
		clientSocket.close();
		String n;

		clientSocket2 = serverSocket.accept();

		InputStream istream = clientSocket2.getInputStream();
			OutputStream ostream = clientSocket2.getOutputStream();
PrintWriter pwrite=new PrintWriter (ostream,true);
		BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

		if ((n = receiveRead.readLine()) != null) // receive from server
		{
			System.out.println(n); // displaying at DOS prompt
		}

        try
        {
            String a="56546";
		String command1[] = {"java", "-cp", "C:\\Users\\SUMEDH\\Desktop\\MonteCarlo.jar" ,"Montecarlo",n};

	ProcessBuilder processBuilder = new ProcessBuilder(command1);

	Process process = processBuilder.start();

	BufferedReader in1 = new BufferedReader(new InputStreamReader(process.getInputStream()));
	
	String line = null;
	while((line = in1.readLine()) != null ){
	System.out.println(line);
	pwrite.println(line); 
	}
	in1.close();

/*
	InputStream istream1 = clientSocket2.getInputStream();
		BufferedReader receiveRead1 = new BufferedReader(new InputStreamReader(istream));
String g;
		if ((g = receiveRead.readLine()) != null) // receive from server
		{
			System.out.println(g); // displaying at DOS prompt
		}
*/			
        }
 
        catch (IOException e)
        {
            e.printStackTrace();
        }
  }
    
  }
}