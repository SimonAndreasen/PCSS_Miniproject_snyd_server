import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.io.DataOutputStream.*;
import java.util.ArrayList;

public class Session implements Runnable {
    boolean connect = true;
    private Socket player1;
    private Socket player2;
    private Socket player3;
    private int diceCount = 0;
    private int turnOrder = 1;
    private String whoLifted;
    private String winner;
    private int betAmount;
    private int betNumber;
    private boolean correctIncrease;
    private int yourTurn = 0;

    public Session(Socket player1, Socket player2, Socket player3) {
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
    }

    ArrayList<Dice> diceP1 = new ArrayList<>();
    ArrayList<Dice> diceP2 = new ArrayList<>();
    ArrayList<Dice> diceP3 = new ArrayList<>();

    int currentBetAmount = 0;
    int currentBetNumber = 0;
    boolean resultGame = false;

    public void run() {
        try {
            DataInputStream inputClient1 = new DataInputStream(player1.getInputStream());
            DataInputStream inputClient2 = new DataInputStream(player2.getInputStream());
            DataInputStream inputClient3 = new DataInputStream(player3.getInputStream());

            DataOutputStream outputClient1 = new DataOutputStream(player1.getOutputStream());
            DataOutputStream outputClient2 = new DataOutputStream(player2.getOutputStream());
            DataOutputStream outputClient3 = new DataOutputStream(player3.getOutputStream());

            for (int i = 0; i < 4; i++){
                diceP1.add(new Dice());
                diceP2.add(new Dice());
                diceP3.add(new Dice());
            }

            while (connect) {

                outputClient1.writeDouble(1);
                outputClient2.writeDouble(2);
                outputClient3.writeDouble(3);

                for (int i = 0; i < 4; i++){
                    outputClient1.writeInt(diceP1.get(i).value);
                    outputClient2.writeInt(diceP2.get(i).value);
                    outputClient3.writeInt(diceP3.get(i).value);
                }

                connect = false;
            }
            while (turnOrder != 0){
                System.out.println("we enter game");



//////////////////////////////PLAYER 1 ///////////////////////////
                if (turnOrder == 1) {
                    //player 1 is making actions

                    System.out.println("we enter player1");

                    outputClient1.writeUTF("Player 1 turn");
                    outputClient2.writeUTF("Player 1 turn");
                    outputClient3.writeUTF("Player 1 turn");

                    outputClient1.writeDouble(1);
                    outputClient2.writeDouble(0);
                    outputClient3.writeDouble(0);



                    boolean correctAction = false;
                    do {
                        System.out.println("we enter do loop in p1");
                        String actionP1 = inputClient1.readUTF();
                        System.out.println("we receive message from p1");
                        switch (actionP1) {

                            case "increase":
                                correctIncrease = false;
                                System.out.println("case increase");
                                do {
                                    //receiving number from client
                                    betAmount = inputClient1.readInt();
                                    //receiving number from client
                                    betNumber = inputClient1.readInt();
                                    if (betAmount > currentBetAmount) {
                                        currentBetAmount = betAmount;
                                        currentBetNumber = betNumber;
                                        outputClient1.writeUTF("correct increase");
                                        outputClient1.writeBoolean(false);
                                        outputClient1.flush();
                                        correctIncrease = true;

                                    }
                                    if (betAmount == currentBetAmount && betNumber > currentBetNumber) {
                                        currentBetAmount = betAmount;
                                        currentBetNumber = betNumber;
                                        outputClient1.writeUTF("correct increase");
                                        outputClient1.writeBoolean(false);
                                        correctIncrease = true;
                                    }
                                } while (!correctIncrease);
                                correctAction = true;
                                turnOrder =2;
                                break;

                            case "lift":
                                System.out.println("case lift");
                                whoLifted = "Player1";
                                outputClient2.writeUTF(whoLifted + " lifted");
                                outputClient3.writeUTF(whoLifted + " lifted");
                                outputClient2.writeDouble(2);
                                outputClient3.writeDouble(2);
                                correctAction = true;
                                turnOrder = 0;
                                break;
                        }
                    } while (!correctAction);
                }

///////////////////////PLAYER 2 ////////////////////////////////////
                if (turnOrder == 2) {
                    System.out.println("player 2 turn");

                    //player 2 is making actions
                    outputClient1.writeUTF("Player 2 turn");
                    outputClient2.writeUTF("Player 2 turn");
                    outputClient3.writeUTF("Player 2 turn");

                    outputClient1.writeDouble(0);
                    outputClient2.writeDouble(1);
                    outputClient3.writeDouble(0);


                    boolean correctAction = false;
                    do {
                        System.out.println("we enter do loop in p2");
                        String actionP2 = inputClient2.readUTF();
                        System.out.println("we receive a command in p2");
                        switch (actionP2) {

                            case "increase":
                                correctIncrease = false;
                                System.out.println("case increase");
                                do {
                                    //receiving number from client
                                    betAmount = inputClient2.readInt();
                                    //receiving number from client
                                    betNumber = inputClient2.readInt();
                                    if (betAmount > currentBetAmount) {
                                        currentBetAmount = betAmount;
                                        currentBetNumber = betNumber;
                                        outputClient2.writeUTF("correct increase");
                                        outputClient2.writeBoolean(false);
                                        outputClient2.flush();
                                        correctIncrease = true;

                                    }
                                    if (betAmount == currentBetAmount && betNumber > currentBetNumber) {
                                        currentBetAmount = betAmount;
                                        currentBetNumber = betNumber;
                                        outputClient2.writeUTF("correct increase");
                                        outputClient2.writeBoolean(false);
                                        correctIncrease = true;
                                    }
                                } while (!correctIncrease);
                                correctAction = true;
                                turnOrder =3;
                                break;


                            case "lift":
                                System.out.println("case lift");
                                whoLifted = "Player2";
                                outputClient1.writeUTF(whoLifted + " lifted");
                                outputClient3.writeUTF(whoLifted + " lifted");
                                outputClient1.writeDouble(2);
                                outputClient3.writeDouble(2);
                                correctAction = true;
                                turnOrder = 0;
                                break;
                        }
                    } while (!correctAction);
                }

////////////////////////PLAYER 3 ///////////////////////////////////////
                if (turnOrder == 3){
                    System.out.println("player 3 turn");
                    //player 3 is making actions

                    outputClient1.writeUTF("Player 3 turn");
                    outputClient2.writeUTF("Player 3 turn");
                    outputClient3.writeUTF("Player 3 turn");

                    outputClient1.writeDouble(0);
                    outputClient2.writeDouble(0);
                    outputClient3.writeDouble(1);


                    boolean correctAction = false;
                    do {
                        System.out.println("we enter do loop in p3");
                        String actionP3 = inputClient3.readUTF();
                        System.out.println("we receive a command in p2");
                        switch (actionP3) {

                            case "increase":
                            correctIncrease = false;
                            System.out.println("case increase");
                            do {
                                //receiving number from client
                                betAmount = inputClient3.readInt();
                                //receiving number from client
                                betNumber = inputClient3.readInt();
                                if (betAmount > currentBetAmount) {
                                    currentBetAmount = betAmount;
                                    currentBetNumber = betNumber;
                                    outputClient3.writeUTF("correct increase");
                                    outputClient3.writeBoolean(false);
                                    outputClient3.flush();
                                    correctIncrease = true;

                                }
                                if (betAmount == currentBetAmount && betNumber > currentBetNumber) {
                                    currentBetAmount = betAmount;
                                    currentBetNumber = betNumber;
                                    outputClient3.writeUTF("correct increase");
                                    outputClient3.writeBoolean(false);
                                    correctIncrease = true;
                                }
                            } while (!correctIncrease);
                            correctAction = true;
                            turnOrder =1;
                            break;

                            case "lift":
                                System.out.println("case lift");
                                whoLifted = "Player3";
                                outputClient1.writeUTF(whoLifted + " lifted");
                                outputClient2.writeUTF(whoLifted + " lifted");
                                outputClient1.writeDouble(2);
                                outputClient2.writeDouble(2);
                                correctAction = true;
                                turnOrder = 0;
                                break;
                        //    default:

                        }
                    } while (!correctAction);
                }

            }
            System.out.println("out of game loop");

            //sent booleans here:

            //lift resulted here:
            for (int i = 0; i<4; i++){
                if(diceP1.get(i).value == currentBetNumber){
                    diceCount ++;
                }
                if (diceP2.get(i).value == currentBetNumber){
                    diceCount++;
                }
                if (diceP3.get(i).value == currentBetNumber){
                    diceCount++;
                }
            }
            System.out.println("There were " + diceCount + " of " + currentBetNumber);

            if (diceCount>= currentBetAmount){
                winner = whoLifted + " loose. There were " + diceCount + " of " + currentBetNumber;
            }else {
                winner = whoLifted + " won. There were " + diceCount + " of " + currentBetNumber;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

            /*while (game) {
                for (int i = 0; i < getplayer.size(); i++) {
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
                                            correctIncrease = false;
                                        }
                                        if (player.userBetAmount == currentBetAmount && player.userBetNumber > currentBetNumber) {
                                            currentBetAmount = player.userBetAmount;
                                            currentBetNumber = player.userBetNumber;
                                            correctIncrease = false;
                                        } else {
                                            player.sendMessage("Incorrect increase. Try again");
                                        }
                                    } while (correctIncrease);

                                    server.sendToAll(player.getUserName() + "increased to: " + currentBetAmount + currentBetNumber, player);
                                    nextPlayer = true;
                                    break;
                                case "lift":
                                    server.sendToAll(player.getUserName() + "lifted", player);
                                    player.setLifted(true);
                                    player.sendMessage("You lifted");
                                    for (int u = 0; u < server.getUsers().size(); u++) {
                                        for (int d = 0; d < player.myDice.size(); d++) {
                                            if (player.myDice.get(d).value == currentBetNumber) {
                                                diceCount++;
                                            }
                                        }
                                    }
                                    if (diceCount >= currentBetAmount) {
                                        player.sendMessage("You lost! There were " + diceCount + " of " + currentBetNumber + ".");
                                        server.sendToAll(player.getUserName() + "lost! There were " + diceCount + " of" + currentBetNumber, player);
                                    } else {
                                        player.sendMessage("You won! There were only " + diceCount + " of " + currentBetNumber + ".");
                                        server.sendToAll(player.getUserName() + "was right! There were " + diceCount + " of " + currentBetNumber, player);
                                    }
                            }
                        } while (nextPlayer);
                    } else {

                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

*/
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

