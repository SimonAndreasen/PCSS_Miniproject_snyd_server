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
    private boolean lifted;
    private boolean readyStatus;
    //number of clients

    private int clientNo = 0;
    ArrayList<Dice> myDice = new ArrayList<>();


    //text area for displaying context
    public Users(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        for (int i = 0; i < 3; i++) {
            myDice.add(new Dice());
        }
    }

    public void shuffle() {
        for (int i = 0; i < myDice.size(); i++) {
            myDice.get(i).roll();

        }

    }

    public class Dice {

        public int value;

        public Dice(){
            value = 0;
        }
        public void roll(){
            value = (int) (Math.random() * ((6 - 1) + 1)) + 1;
        }

    }


    public void printDice() {
        System.out.println("Your dice: ");
        myDice.forEach((i) -> System.out.print(i.value + ", "));
        System.out.println(" ");
    }

    @Override
    public void run() {
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            userName = input.readUTF();
            System.out.println(userName + " joined the server");

            readyStatus = true;
            while (readyStatus) {
                String clientMessage = input.readUTF();
                if (clientMessage.equalsIgnoreCase("quit")) {
                    server.removeUser(this);
                    socket.close();
                    readyStatus = false;
                }
                if (clientMessage.equalsIgnoreCase("ready")) {
                    readyStatus = false;
                    server.startGame();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //getters and setter
    public boolean isReady() {
        return readyStatus;
    }

    public String getUserName(){
        return userName;
    }

    public boolean isLifted(){
        return lifted;
    }

    public void setLifted(boolean lifted){
        this.lifted=lifted;
    }

    //setter for estimate

    public void sendMessage(String message) {
        try {
            output.writeUTF(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readString() {
        String string = "default";
        try {
            string = input.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    public int readInt(){
        int value =0;
        try{
            value = input.readInt();
        }catch (IOException e){
            e.printStackTrace();
        }
        return value;
    }

    public void sendBoolean(boolean condition) {
        try {
            output.writeBoolean(condition);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


