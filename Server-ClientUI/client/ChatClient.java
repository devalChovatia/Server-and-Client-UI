// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import common.*;
import ocsf.client.AbstractClient;

import java.io.*;
import java.util.Scanner;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI;
  
  /*
   * Changed for E50a
   * The loggedIn boolean variable. Determines whether a client is
   * connected to the server. If true, the client is connected and
   * the client is disconnected otherwise.
   * B. T., D. C.
   */
  public boolean loggedIn;
  
  /*
   * Changed for E50c
   * The serverStarted boolean variable. Determines whether a server has
   * been launched. Used in the serverStart() and serverStop() methods.
   * D. C.
   */
  
  public boolean serverStarted;
  
  /*
   * Changed for E50a and E50c
   * The scanner variable. Used to obtain user input from the console.
   * User input is then used in the setters for the client and server.
   * D. C.
   */
  
  Scanner scanner = new Scanner(System.in);
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    this.loggedIn = true;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
      sendToServer(message);
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server. Terminating client.");
      quit();
    }
  }
  
  public void handleMessageFromServerUI(String message)
  {
    try
    {
      sendToServer(message);
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to the main server. Terminating server UI.");
      quit();
    }
  }
  
  /*
   * Changed for E49a:
   * Implementation of the "connectionClosed()" method declared in the superclass.
   * B. T.
   */
  public void connectionClosed()
  {
	  clientUI.display("Server has been shut down.");
	  System.exit(0);
  }

  /* 
   * Changed for E49a:
   * Implementation of the "connectionException(Exception exception) method declared
   * in the superclass.
   * B. T.
   */
  public void connectionException (Exception exception)
  {
	  try {
		  closeConnection();
	  }
	  catch(IOException e) {
		  clientUI.display("Faulty input/output.");
	  }
  }
  
  /*
   * Changed for E50a:
   * Method used to handle the "#quit" command inputed by the user in the client UI.
   * Only called when "#quit" is called.
   * B. T., D. C.
   */
  public void clientQuit(){ // ****** Changed for E50 DC
	  clientUI.display("Connection to server has been terminated.");
	  System.exit(0);
  }
  
  /*
   * Changed for E50a:
   * Method used to handle the "#logoff" command inputed by the user in the client UI.
   * Only called when "#logoff" is called.
   * B. T., D. C.
   */
  public void clientLogOff(){ // ****** Changed for E50 DC
	  clientUI.display("Logged off; must log back in to send messages.");
	  loggedIn = false;
  }
  
  /*
   * Changed for E50a:
   * Method used to handle the "#login" command inputed by the user in the client UI.
   * Only called when "#login" is called.
   * B. T., D. C.
   */
  
  public void clientLogin(){
      clientUI.display("Logged in; can now send messages.");
      loggedIn = true;
  }
  
  /*
   * Changed for E50a:
   * Method used to handle the "#setport" command inputed by the user in the client UI.
   * Only called when "#setport" is called.
   * B. T., D. C.
   */
  
  public void clientSetPort(){
	  int portNum;
  	  System.out.println("What would you like to set the port number to? ");
  	  portNum = scanner.nextInt();
	  setPort(portNum);
	  System.out.println("Port number has been updated to: " + String.valueOf(getPort()));
  }
  
  /*
   * Changed for E50a:
   * Method used to handle the "#sethost" command inputed by the user in the client UI.
   * Only called when "#sethost" is called.
   * B. T., D. C.
   */
  
  public void clientSetHost(){
	  String hostName;
	  System.out.println("What would you like to set the host name to? ");
	  hostName = scanner.next();
	  setHost(hostName);
	  System.out.println("Host name has been updated to: " + getHost());
  }
  
  /*
   * Changed for E50a:
   * Method used to handle the "#getport" command inputed by the user in the client UI.
   * Only called when "#getport" is called.
   * B. T., D. C.
   */
  
  public void clientGetPort(){ //******Changed for E50 DC
	  System.out.println("The current port number is: " + String.valueOf(getPort()));
  }
  
  /*
   * Changed for E50a:
   * Method used to handle the "#gethost" command inputed by the user in the client UI.
   * Only called when "#gethost" is called.
   * B. T., D. C.
   */
  
  public void clientGetHost(){ // ******Changed for E50 DC
	  System.out.println("The current host is: " + getHost());
  }
  
  /*public void connectionEstablished() {
	  clientUI = new ClientConsole();
  }*/
  
  public void serverQuit(){
      System.out.println("Server has been terminated.");
      System.exit(0);
      handleMessageFromServerUI("#serverquit");
  }
  
  public void serverClose(){
	  handleMessageFromServerUI("#serverclose");
  }
    
  public void serverSetPort(){
      int portNum;
      handleMessageFromServerUI("#serversetport");
      System.out.println("What would you like to set the port number to? ");
      portNum = scanner.nextInt();
      setPort(portNum);
      System.out.println("Port number has been updated to: " + getPort());
  }
    
  public void serverGetPort()
  {
      System.out.println("The current port number is: " + getPort());
      handleMessageFromServerUI("#servergetport");
  }
    
  public void serverStart()
  {
	  System.out.println("Server has started");
	  serverStarted = true;
	  handleMessageFromServerUI("#serverstart");
  }
    
  public void serverStop()
  {
	  System.out.println("Server has been closed");
	  serverStarted = false;
	  handleMessageFromServerUI("#serverstop");
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class
