import java.net.Socket;

public class Session implements Runnable {

    private Server server;
    private Socket socket;

    public Session(Server server){
        this.server = server;
    }


    @Override
    public void run() {
        Users player = new Users(server,socket);


        boolean game = true;
        while (game){
            int currentBetAmount = 0;
            int currentBetNumber = 0;

            boolean nextPlayer = false;

            do{
                String command = player.readString();
                switch (command) {
                    case "increase":
                        player.sendMessage("amount");
                        currentBetAmount = player.readInt();
                        player.sendMessage("of a kind");
                        currentBetNumber = player.readInt();
                        server.sendToAll(player.getUserName() + "increased to: " + currentBetAmount + currentBetNumber,player);
                        nextPlayer = true;
                        break;
                    case "lift":
                        server.sendToAll(player.getUserName() + "lifted",player);
                        //handleResult
                        break;
                    default:
                        player.sendMessage("Incorrect command");
                        System.out.println("Incorrect command");
                        break;



                }

            }while (!nextPlayer);



           // server.getUsers().get(server.getUsers().size()-1).

            //shuffle the dice here


        }


    }
}