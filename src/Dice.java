import java.util.ArrayList;

public class Dice {
    ArrayList<Dice> myDice = new ArrayList<>();

    public int value;

    public Dice(){
        value = 0;
    }
    public void roll(){
        value = (int) (Math.random() * ((6 - 1) + 1)) + 1;
    }



    public void printDice() {
        System.out.println("Your dice: ");
        myDice.forEach((i) -> System.out.print(i.value + ", "));
        System.out.println(" ");
    }

    public void createDice(){
        for (int i = 0; i < 3; i++) {
            myDice.add(new Dice());
        }
    }

    public void shuffle() {
        for (int i = 0; i < myDice.size(); i++) {
            myDice.get(i).roll();
        }

    }
}
