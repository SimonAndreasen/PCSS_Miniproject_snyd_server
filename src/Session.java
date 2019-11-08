import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session implements Runnable {

    private Socket player1;
    private Socket player2;
    private Socket player3;
    private int diceCount = 0;

    public Session(Socket player1, Socket player2, Socket player3) {
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
    }

    public void run(){
        try{
            DataInputStream inputClient1 = new DataInputStream(player1.getInputStream());
            DataInputStream inputClient2 = new DataInputStream(player2.getInputStream());
            DataInputStream inputClient3 = new DataInputStream(player3.getInputStream());

            DataOutputStream outputClient1 = new DataOutputStream(player1.getOutputStream());
            DataOutputStream outputClinet2 = new DataOutputStream(player2.getOutputStream());
            DataOutputStream outputClient3 = new DataOutputStream(player3.getOutputStream());

        }catch (IOException e){
            e.printStackTrace();
        }
    }


   /* @Override
    public void run() {
        Users player = new Users(server,socket);

        boolean game = true;
        while (game) {
        System.out.println("game test");

            int currentBetAmount = 0;
            int currentBetNumber = 0;
            boolean resultGame = false;

            for (int i = 0; i < server.getUsers().size(); i++) {
                player = server.getUsers().get(i);
                if (!player.isLifted() && !resultGame) {
                    player.sendBoolean(true);
                    for (Users u : server.getUsers()) {
                        if (u != player) {
                            System.out.println("user test");
                            System.out.println(player.getMyDice());
                            u.sendBoolean(false);
                        }
                    }
                    String turnMessage = "It is " + player.getUserName() + " turn.";
                    server.sendToAll(turnMessage, player);
                    String messageToYou = "It is your turn. Current estimate is: " + currentBetAmount + " of " + currentBetNumber + "." + '\n'
                            + "You can use commands: print dice, increase, lift";
                    player.sendMessage(messageToYou);

                    boolean nextPlayer = false;

                    do {
                        String command = player.readString();
                        switch (command) {
                            case "print dice":
                                player.sendMessage("Your dice: ");
                                break;
                            case "increase":
                                //loop for typing amount of dice
                                boolean correctIncrease = true;

                                do {
                                    player.sendMessage("enter amount");
                                    player.userBetAmount = player.readInt();
                                    player.sendMessage("enter number");
                                    player.userBetNumber = player.readInt();
                                    if (player.userBetAmount > currentBetAmount) {
                                        currentBetAmount = player.userBetAmount;
                                        currentBetNumber = player.userBetNumber;
                                        correctIncrease=false;
                                    }
                                    if (player.userBetAmount == currentBetAmount && player.userBetNumber > currentBetNumber){
                                        currentBetAmount = player.userBetAmount;
                                        currentBetNumber = player.userBetNumber;
                                        correctIncrease=false;
                                    }else {
                                        player.sendMessage("Incorrect increase. Try again");
                                    }
                                }while (correctIncrease);

                                server.sendToAll(player.getUserName() + "increased to: " + currentBetAmount + currentBetNumber, player);
                                nextPlayer = true;
                                break;
                            case "lift":
                                server.sendToAll(player.getUserName() + "lifted", player);
                                player.setLifted(true);
                                player.sendMessage("You lifted");
                                for (int u = 0; u<server.getUsers().size(); u++){
                                    for (int d =0; d<player.myDice.size(); d++){
                                        if(player.myDice.get(d).value == currentBetNumber){
                                        diceCount++;
                                        }
                                    }
                                }
                                if (diceCount>=currentBetAmount){
                                    player.sendMessage("You lost! There were " + diceCount + " of " + currentBetNumber + ".");
                                    server.sendToAll(player.getUserName() + "lost! There were " + diceCount + " of" + currentBetNumber,player);
                                }else{
                                    player.sendMessage("You won! There were only " + diceCount+ " of " + currentBetNumber + ".");
                                    server.sendToAll(player.getUserName() + "was right! There were " + diceCount + " of " + currentBetNumber,player);
                                }
                                //break the loop here ->
                                resultGame=true;
                                break;
                            default:
                                player.sendMessage("Incorrect command");
                                System.out.println("Incorrect command");
                                break;
                        }

                    } while (!nextPlayer);

                } else {
                    for (Users u : server.getUsers()) {
                        u.sendBoolean(false);
                    }
                    server.sendToAll(player.getUserName() + "lifted", player);
                    //
                }
            }

            //result game
            //HERE

            //continue or quit
            for (Users u: server.getUsers()){
                u.sendMessage("type ready for continue, anything else to quit");
                if (!u.readString().equalsIgnoreCase("ready")){
                    for (Users u1:server.getUsers()) {
                        if (u1 != u) {
                            u1.sendMessage(u.getUserName() + "quited");
                        }
                        u1.sendBoolean(false);
                    }
                    game = false;
                    break;
                }else {
                    for (Users u1:server.getUsers()){
                        if (u1 !=u){
                            u1.sendMessage(u.getUserName() + "is ready.");
                        }
                    u1.sendBoolean(true);
                    }
                }
            }
        }

        System.out.println("Session ended");
        try {
            server.getServerSocket().close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
   */

}