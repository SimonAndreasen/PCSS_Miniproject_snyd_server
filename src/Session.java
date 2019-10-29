public class Session implements Runnable {

    private Server server;

    public Session(Server server){
        this.server = server;
    }


    @Override
    public void run() {


        boolean game = true;
        while (game){
            int currentBetAmount = 0;
            int currentBetNumber = 0;


            //here the increase() shoudl be handled

            // server.getUsers().get(server.getUsers().size()-1).

            //shuffle the dice here


        }


    }
}