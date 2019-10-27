import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Users extends Thread {
    private Server server;
    private Socket socket;
    private String userName;
    private DataOutputStream output;
    private DataInputStream input;
    //number of clients
    private int clientNo = 0;

    //text area for displaying context
    public Users(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            userName = input.readUTF();
            System.out.println(userName + " joined the server");
            // Display the client number
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
