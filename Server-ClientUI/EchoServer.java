// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import java.util.Scanner;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  Scanner scanner = new Scanner(System.in);
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server for this class.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port)
  {
    super(port);
  }
  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {  
	  
	if(msg.equals("#serverstop"))
	{
		stopListening();
		return;
	}
	
	else if(msg.equals("#serverstart"))
	{
		try {
			listen();
		}
		catch(Exception exception) {
			System.out.println("Error: Unable to start up the server again.");
		}
		return;
	}
	
	else if(msg.equals("#serverclose"))
	{
		try {
			close();
		}
		catch(Exception exception) {
			System.out.println("Error: Unable to close the server.");
		}
		return;
	}
	else if(msg.equals("#serversetport"))
	{
		  int portNum;
	      System.out.println("What would you like to set the port number to? ");
	      portNum = scanner.nextInt();
	      setPort(portNum);
	      System.out.println("Server port number has been updated to: " + getPort());
	}
	
	else if(msg.equals("#servergetport"))
	{
		System.out.println("The server port is currently set to: " + getPort());
	}
	
	else if(msg.equals("#serverquit"))
	{
		try {
			close();
		}
		catch(Exception exception) {
			System.out.println("Error: Unable to close the server.");
		}
		return;
	}
	else
	{
		System.out.println("Message received: " + msg + " from " + client);
	    this.sendToAllClients(msg);
	}
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  /**
   * Changed for E49c
   * This method overrides the one in the superclass.  Called
   * when the server detects a new client connection.
   * B. T.
   */
  public void clientConnected()
  {
	  System.out.println("A new client has connected.");
  }
  
  /**
   * Changed for E49c
   * This method overrides the one in the superclass.  Called
   * when the server detects a client disconnection.
   * B. T.
   */
  public void clientDisconnected()
  {
	  System.out.println("An existing client has disconnected.");
  }
  
  //Class methods ***************************************************

  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
      System.out.println("The (EchoServer) port is: " + String.valueOf(port));
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); // Start listening for connections
    }
    catch(Exception ex)
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
//End of EchoServer class
