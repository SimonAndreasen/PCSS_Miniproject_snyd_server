import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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

    Session(Socket player1, Socket player2, Socket player3) {
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
    }

    private ArrayList<Dice> diceP1 = new ArrayList<>();
    private ArrayList<Dice> diceP2 = new ArrayList<>();
    private ArrayList<Dice> diceP3 = new ArrayList<>();

    private int currentBetAmount = 0;
    private int currentBetNumber = 0;
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
                //sending turn order to users
                outputClient1.writeDouble(1);
                outputClient2.writeDouble(2);
                outputClient3.writeDouble(3);
                //sending dice values to users
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
                    //sending who's turn it is
                    outputClient1.writeDouble(1);
                    outputClient2.writeDouble(0);
                    outputClient3.writeDouble(0);



                    boolean correctAction = false;
                    do {
                        System.out.println("enter do loop in p1");
                        String actionP1 = inputClient1.readUTF();
                        System.out.println("receive message from p1");
                        switch (actionP1) {

                            case "increase":
                                correctIncrease = false;
                                System.out.println("case increase");
                                do {
                                    //receiving number from client
                                    betAmount = inputClient1.readInt();
                                    //receiving number from client
                                    betNumber = inputClient1.readInt();
                                    if (betAmount > currentBetAmount && betNumber <=6) {
                                        currentBetAmount = betAmount;
                                        currentBetNumber = betNumber;
                                        outputClient1.writeUTF("correct increase");
                                        outputClient1.writeBoolean(false);
                                        outputClient1.flush();
                                        correctIncrease = true;

                                    }
                                    if (betAmount == currentBetAmount && betNumber > currentBetNumber && betNumber <=6) {
                                        currentBetAmount = betAmount;
                                        currentBetNumber = betNumber;
                                        outputClient1.writeUTF("valid increase");
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
                            case "quit":
                                System.out.println("case quit");
                                outputClient2.writeUTF("Player1 disconnected.\n Restart game");
                                outputClient3.writeUTF("Player1 disconnected.\n Restart game");
                                outputClient2.writeDouble(2);
                                outputClient3.writeDouble(2);


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
                                    if (betAmount > currentBetAmount && betNumber <=6) {
                                        currentBetAmount = betAmount;
                                        currentBetNumber = betNumber;
                                        outputClient2.writeUTF("correct increase");
                                        outputClient2.writeBoolean(false);
                                        outputClient2.flush();
                                        correctIncrease = true;

                                    }
                                    if (betAmount == currentBetAmount && betNumber > currentBetNumber && betNumber <=6){
                                        currentBetAmount = betAmount;
                                        currentBetNumber = betNumber;
                                        outputClient2.writeUTF("valid increase");
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
                            case "quit":
                                System.out.println("case quit");
                                outputClient1.writeUTF("Player2 disconnected.\n Restart game");
                                outputClient3.writeUTF("Player2 disconnected.\n Restart game");
                                outputClient1.writeDouble(2);
                                outputClient3.writeDouble(2);
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
                    //sending who's turn it is
                    outputClient1.writeDouble(0);
                    outputClient2.writeDouble(0);
                    outputClient3.writeDouble(1);


                    boolean correctAction = false;
                    do {
                        System.out.println("enter do loop in p3");
                        String actionP3 = inputClient3.readUTF();
                        System.out.println("receive a command in p2");
                        switch (actionP3) {

                            case "increase":
                            correctIncrease = false;
                            System.out.println("case increase");
                            do {
                                //receiving number from client
                                betAmount = inputClient3.readInt();
                                //receiving number from client
                                betNumber = inputClient3.readInt();
                                if (betAmount > currentBetAmount && betNumber <=6) {
                                    currentBetAmount = betAmount;
                                    currentBetNumber = betNumber;
                                    outputClient3.writeUTF("correct increase");
                                    outputClient3.writeBoolean(false);
                                    outputClient3.flush();
                                    correctIncrease = true;

                                }
                                if (betAmount == currentBetAmount && betNumber > currentBetNumber && betNumber <=6) {
                                    currentBetAmount = betAmount;
                                    currentBetNumber = betNumber;
                                    outputClient3.writeUTF("valid increase");
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
                            case "quit":
                                System.out.println("case quit");
                                outputClient1.writeUTF("Player3 disconnected.\n Restart game");
                                outputClient2.writeUTF("Player3 disconnected.\n Restart game");
                                outputClient1.writeDouble(2);
                                outputClient2.writeDouble(2);



                        }
                    } while (!correctAction);
                }

            }
            System.out.println("Game ended");

            //sent booleans here:
////////////////////////GAME RESULT ///////////////////////////////////////
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
            //determining winner
            if (diceCount>= currentBetAmount){
                winner = whoLifted + " loose. There were " + diceCount + " of " + currentBetNumber;
            }else {
                winner = whoLifted + " won. There were " + diceCount + " of " + currentBetNumber;
            }
            //sending who won message to users
            outputClient1.writeUTF(winner);
            outputClient2.writeUTF(winner);
            outputClient3.writeUTF(winner);
            //create new session
            new Thread(new  Session(player1,player2,player3)).start();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
