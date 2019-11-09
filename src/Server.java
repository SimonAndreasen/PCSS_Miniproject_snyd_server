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
/*public class Server {
    private int port;
    private ArrayList<Users> users;
    private boolean lobby;
    private ServerSocket serverSocket;

    public Server(int port) {
        //Set port value to inputted augment, set lobby to true and create an arraylist of the class "Users"
        this.port = port;
        lobby = true;
        users = new ArrayList<>();
    }

    public static void main(String[] args) {
        //Create scanner object, scan the next inputted line and set that to the server port.
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter server port you want the game to be hosted at, default is; 8000. Ports underneath 1024 are reserved and thus unavailable");
        int serverPort = scan.nextInt();
        if (serverPort <= 1023) {
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

            while (lobby) {
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

    public ArrayList<Users> getUsers() {
        return users;
    }

    public void startGame() {
        System.out.println("start");
        boolean start = true;
        for (Users u : users) {
            if (u.isReady() == true) {
                start = false;
            }
        }
        if (!start) {
            lobby = false;
            for (Users u : users) {
                u.sendMessage("Game started");
                System.out.println("game started");
            }
            new Thread(new Session(this)).start();
        }
    }

    public void removeUser(Users u) {
        users.remove(u);
    }

    public void sendToAll(String message, Users u) {
        for (Users usersThread : users) {
            if (usersThread != u) {
                usersThread.sendMessage(message);
            }
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}

*/