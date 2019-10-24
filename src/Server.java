import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
public class Server extends Application {

    //text area for displaying context
    private TextArea ta = new TextArea();

    //number of clients
    private int clientNo = 0;


    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new ScrollPane(ta), 450, 200);
        primaryStage.setTitle("MultiThreadServer");
        primaryStage.setScene(scene);
        primaryStage.show();


        new Thread(() -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(8000);
                ta.appendText("MultiThread Server started at " + new Date() + '\n');


                while (true) {
                    Socket socket = serverSocket.accept();
                    //increment clientNO
                    clientNo++;

                    Platform.runLater(() -> {
                        // Display the client number
                        ta.appendText("Starting thread for client" + clientNo + " at " + new Date() + '\n');
                        //Find client's host name and IP
                        InetAddress inetAddress = socket.getInetAddress();
                        ta.appendText("Client" + clientNo + "'s host name is " + inetAddress.getHostName() + "\n");
                        ta.appendText("Client" + clientNo + "'s IP address is " + inetAddress.getHostAddress() + "\n");
                    });

                    //create and start a new thread for the connection
                    new Thread(new HandleAClient(socket)).start();
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }).start();
    }

    //class for handling nwe connection
    class HandleAClient implements Runnable {
        private Socket socket; // A connected socket

         //Construct a thread
        public HandleAClient(Socket socket) {
            this.socket = socket;
        }

       //Run a thread
        public void run() {
            try {
                //create data input and output streams
                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

                // Continuously serve the client
                while (true) {
                    String message = inputFromClient.readUTF();
                    System.out.println("Name gotten was " + message);
                    ta.appendText(clientNo + " Name is " + message + '\n');
                    }

                    } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}