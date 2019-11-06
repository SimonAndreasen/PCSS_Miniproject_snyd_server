import java.io.IOException;
import java.net.Socket;

public class Session implements Runnable {

    private Server server;
    private Socket socket;

    public Session(Server server) {
        this.server = server;
    }


    @Override
    public void run() {
        Users player;
        Dice dice = new Dice();

        boolean game = true;
        while (game) {
        System.out.println("game test");
            int currentBetAmount = 0;
            int currentBetNumber = 0;


            for (int i = 0; i < server.getUsers().size(); i++) {
                player = server.getUsers().get(i);
                if (player.isLifted()) {
                    player.sendBoolean(true);
                    for (Users u : server.getUsers()) {
                        if (u != player) {
                            System.out.println("user test");
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
                                dice.printDice();
                                break;
                            case "increase":
                                boolean correctAmount = false;
                                boolean correctNumber = false;
                                //loop for typing amount of dice
                                do {
                                    player.sendMessage("enter amount");
                                    player.userBetAmount = player.readInt();
                                    if (player.userBetAmount >= currentBetAmount) {
                                        currentBetAmount = player.userBetAmount;
                                        correctAmount = true;
                                    } else {
                                        player.sendMessage("Incorrect amount. It needs to be higher or equal than current.");
                                    }
                                }while (!correctAmount);

                                do {
                                    player.sendMessage("enter number");
                                    player.userBetNumber = player.readInt();
                                        if (player.userBetNumber>= currentBetNumber || player.userBetNumber>6 || player.userBetNumber<2){
                                            currentBetNumber = player.userBetNumber;
                                            correctNumber=true;
                                        }
                                }while (!correctNumber);

                                server.sendToAll(player.getUserName() + "increased to: " + currentBetAmount + currentBetNumber, player);
                                nextPlayer = true;
                                break;
                            case "lift":
                                server.sendToAll(player.getUserName() + "lifted", player);
                                player.setLifted(true);
                                player.sendMessage("You lifted");
                                //break the loop here -> proceed to result
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