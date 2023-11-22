This project dives in depth into using OCSF. 

EchoServer, ClientConsole, and ServerConsole are operating at the same time and whenever a client 
sends a message, it is repeated on the server console. 

There are many commands which both server and client can enter into their own console to change 
certain parameters like port number, host name, logoff, login, and more

List of Client commands:

    #quit causes the client to terminate
    #logoff causes the client to disconnect from server, but not quit
    #sethost allows client to set host name, only if logged off
    #setport allows client to set a new port number, only if logged off
    #login causes client to connect to server. error if already logged in
    #gethost displays current host name
    #getport displays current port number

List of Server commands: 

    #quit causes server to quit
    #stop causes server to stop listening to new clients 
    #close causes server to not only stop listening to new clients, but terminates existing clients
    #setport allows server to set a new port number, only if server is closed
    #start causes server to start listening to new clients, error if server already started
    #getport displays current port number 