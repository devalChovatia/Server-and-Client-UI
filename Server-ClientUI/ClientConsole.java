// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;

import client.ChatClient;
import common.*;
import ocsf.client.*;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole implements ChatIF 
{
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  ChatClient client;
  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientConsole(String host, int port) 
  {
    try 
    {
      client= new ChatClient(host, port, this);
    } 
    catch(IOException exception)
    {
      System.out.println("Error: Can't setup connection! Terminating client.");
      System.exit(1);
    }
  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept() 
  {
    try
    {
      BufferedReader fromConsole = 
      new BufferedReader(new InputStreamReader(System.in));
      String message;

      while (true)
      {
    	message = fromConsole.readLine();
    	
    	/*
    	 * Lines 84 to 130 changed for E50a
    	 * Detects and performs commands requested from the user in the client UI.
    	 * B. T., D. C.
    	 */
    	
    	if(message.equals("#quit")) {
			client.clientQuit();
		}
    	
    	else if(message.equals("#logoff")) {
			client.clientLogOff();
		}
    	
    	else if(message.equals("#login")){
    		if(!client.loggedIn) {
    			client.clientLogin();
    		}
    		else{
    			System.out.println("Error: Cannot use the '#login' command if already logged in.");
    		}
		}
    	
    	else if(message.equals("#setport") || message.equals("#sethost")) {
    		if(!client.loggedIn) {
    			if(message.equals("#setport")){
    	    		client.clientSetPort();
    	    	}
    			else if(message.equals("#sethost")){
    	    		client.clientSetHost();
    	    	}
    		}
    		else{
    			System.out.println("Error: Must log out before updating the port or host.");
    		}
    	}
    	
    	else if(message.equals("#getport")){
    		client.clientGetPort();
    	}
    	
    	else if(message.equals("#gethost")){
    		client.clientGetHost();
    	}
    	
    	else {
    		if(client.loggedIn){
        		client.handleMessageFromClientUI(message);
        	}
        	
        	else{
        		System.out.println("Error: Cannot send message because client is offline!");
        	}
    	}
      }
    } 
    catch (Exception ex)
    {
      System.out.println
        ("Unexpected error while reading from the client console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
    System.out.println("> " + message);
  }

  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The host to connect to.
   */
  public static void main(String[] args) 
  {
    String host = "";
    int port = 0;  //The port number
    
    /*
     * Changed for E49b
     * Set the port equal to the element at the first index of "args"
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
    
    ClientConsole chat= new ClientConsole(host, port);
    chat.accept();  //Wait for console data
  }
}
//End of ConsoleChat class
