import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Users extends Thread {
    private Server server;
    private Socket socket;
    private String userName;
    private DataOutputStream output;
    private DataInputStream input;
    //number of clients
    private int clientNo = 0;
    ArrayList<Dice> myDice = new ArrayList<>();

    //text area for displaying context
    public Users(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        myDice.add(new Dice());
        myDice.add(new Dice());
        myDice.add(new Dice());
        myDice.add(new Dice());
    }

        public void shuffle(){
            for (int i=0; i<myDice.size(); i++){
                myDice.get(i).roll();

            }

    }
    public void printDice(){
        System.out.println("Your dice: ");
        myDice.forEach( (i) -> System.out.print(i.value + ", "));
        System.out.println(" ");
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
