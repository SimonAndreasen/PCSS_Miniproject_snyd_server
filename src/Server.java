import javafx.application.Application;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{

    public static void main(String[] args){
        new Thread(()-> {
            //used to show how many clients that are playing
            int clientNo = 0;
            //the port used to connect to the server, the number could be between 1025 - 65536
            int port = 8000;

            try {
                //Creates a socket to connect to the client, through the port
                ServerSocket serverSocket = new ServerSocket(port);
                System.out.println("Server is running");


                while (true){
                    //Accept the first player
                    Socket player1 = serverSocket.accept();
                    clientNo++;
                    System.out.println("Client 1 connected");

                    InetAddress inetAddress1 = player1.getInetAddress();
                    System.out.println("Client no " + clientNo);
                    System.out.println("host name " + inetAddress1.getHostName());
                    System.out.println("IP address " + inetAddress1.getHostAddress());

                   //Accept the second player
                    Socket player2 = serverSocket.accept();
                    clientNo++;
                    System.out.println("Client 2 connected");

                    InetAddress inetAddress2 = player2.getInetAddress();
                    System.out.println("Client no " + clientNo);
                    System.out.println("host name " + inetAddress2.getHostName());
                    System.out.println("IP address " + inetAddress2.getHostAddress());

                    //Accept the third player
                    Socket player3 = serverSocket.accept();
                    clientNo++;
                    System.out.println("Client 3 connected");

                    InetAddress inetAddress3 = player3.getInetAddress();
                    System.out.println("Client no " + clientNo);
                    System.out.println("host name " + inetAddress3.getHostName());
                    System.out.println("IP address " + inetAddress3.getHostAddress());

                    //creates a thread of HandleASession when two players are available
                    new Thread(new Session(player1,player2,player3)).start();
                    System.out.println("Thread created");

                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }).start();
    }
}
