import javafx.application.Application;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class Server {
    private int port;
    private ArrayList<Users> users;
    private boolean gameWait;
    private ServerSocket serverSocket;

    public Server(int port) {
        //Set port value to inputted augment, set gameWait to true and create an arrylist of the class "Users"
        this.port = port;
        gameWait = true;
        users = new ArrayList<>();
    }

    public static void main(String[] args) {
        //Create scanner object, scan the next inputted line and set that to the server port.
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter server port you want the game to be hosted at, default is; 8000. Ports underneath 1024 are reserved and thus unavailable");
        int serverPort = scan.nextInt();
        if (serverPort <= 1023){
            System.out.println("Reserved port, input a new value");
            System.out.println("Enter server port you want the game to be hosted at, default is; 8000. Ports underneath 1024 are reserved and thus unavailable");
            serverPort = scan.nextInt();
        }
        Server server = new Server(serverPort);
        scan.close();
        server.initiateServer();
    }

    public void initiateServer() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");

            while (gameWait) {
                Socket socket = serverSocket.accept();
                Users newUser = new Users(this, socket);
                users.add(newUser);
                newUser.start();
                System.out.println(users.size()); //Prints current amount of users connected
            }

        } catch (IOException e) {
            System.out.println("Unable to start server IOException");
            e.printStackTrace();
        }
    }
}