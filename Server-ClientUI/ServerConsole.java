import java.io.*;

import client.ChatClient;
import common.*;

public class ServerConsole implements ChatIF{
	
	ChatClient server;
	
	private String password = "SayokoNakamoto";
	
	final public static int DEFAULT_PORT = 5555;
	
	public ServerConsole(String host, int port)
	{
		try 
	    {
	      server= new ChatClient(host, port, this);
	    } 
	    catch(IOException exception)
	    {
	      System.out.println("Error: Can't setup connection! Terminating server UI.");
	      System.exit(1);
	    }

	}
	
	public void accept()
	{
	    try
	    {
	      BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
	      String message;

	      while (true)
	      {
	    	  
	    	  message = fromConsole.readLine();
	    	
	    	  if (message.equals("#quit"))
	    	  {
	    		  if(verification()){
	    			  server.serverQuit();
	    		  }
	    		  else {
	    			  System.out.println("Error: Incorrect password. Must enter correct password"
	    			  		+ " to use server commands.");
	    		  }
			  }
	    	  
	    	  else if (message.equals("#close")) {
	    		  
	    		  if(verification()){
	    			  server.serverClose();
	    		  }
	    		  else {
	    			  System.out.println("Error: Incorrect password. Must enter correct password"
	    			  		+ " to use server commands.");
	    		  }
			  }
	    	  
	    	  else if (message.equals("#start")) {
				  if (!server.serverStarted) {
					  if(verification()){
		    			  server.serverStart();
		    		  }
		    		  else {
		    			  System.out.println("Error: Incorrect password. Must enter correct password"
		    			  		+ " to use server commands.");
		    		  }
				  }
				  else
				  {
					  System.out.println("Error: Cannot use the '#start' command if server has already started.");
				  }
			  }
	    	  
	    	  else if (message.equalsIgnoreCase("#stop")) {
	    		  
	    		  if(verification()){
	    			  server.serverStop();
	    		  }
	    		  else {
	    			  System.out.println("Error: Incorrect password. Must enter correct password"
	    			  		+ " to use server commands.");
	    		  }
			  }
	    	  
	    	  else if (message.equals("#setport")) {
				  if (!server.serverStarted)
				  {
					  if (message.equals("#setport"))
					  {
						  if(verification()){
			    			  server.serverSetPort();
			    		  }
			    		  else {
			    			  System.out.println("Error: Incorrect password. Must enter correct password"
			    			  		+ " to use server commands.");
			    		  }
					  }
				  }
				  else
				  {
					  System.out.println("Error: Must stop the server before updating the port.");
				  }
			  }
	    	  
	    	  else if (message.equals("#getport")) {
	    		  if(verification()){
	    			  server.serverGetPort();
	    		  }
	    		  else {
	    			  System.out.println("Error: Incorrect password. Must enter correct password"
	    			  		+ " to use server commands.");
	    		  }
			  }
	    	
	    	  else {
	    		  server.handleMessageFromServerUI("SERVER MSG> " + message);
	    	  }
	      }
	    }
	    catch (Exception ex)
	    {
	      System.out.println("Unexpected error while reading from the server console!");
	    }
	}

	
	public void display(String message) 
	{
		System.out.println(message);
	}
	
	private boolean verification(){
		BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("To access server commands, you must enter the correct password: ");
			String passwordGuess = fromConsole.readLine();
			if(passwordGuess.equals(password)){
				return true;
			}
			return false;
		}
		catch(Exception exception) {
			return false;
		}
	}
		
	public static void main(String[] args) 
	{
		String host = "";
		int port = 0;  //The port number
    
		/*
		* Changed for E49b
		* Set the port equal to the element at the first index of "args".
		* B. T.
		*/
    
		try
		{
			host = args[0];
			port = Integer.parseInt(args[1]);
		}
		
		catch(ArrayIndexOutOfBoundsException e)
		{
			host = "localhost";
			port = DEFAULT_PORT;
		}
		
		EchoServer echoTechnique = new EchoServer(port);
		
		ServerConsole chat = new ServerConsole(host, port);
		chat.accept();  //Wait for console data
		
		try {
			echoTechnique.listen();
		}
		
		catch(Exception exception){
			System.out.println("Error: Unable to listen for clients.");
		}
		
	}
	
}