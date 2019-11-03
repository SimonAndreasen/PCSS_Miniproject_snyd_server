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



    //text area for displaying context
    public Users(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        Dice dice = new Dice();
        dice.createDice();
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
                    System.out.println("test");
                    server.startGame();
                    readyStatus = false;

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


