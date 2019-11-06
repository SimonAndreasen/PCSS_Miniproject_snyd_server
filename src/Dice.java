public class Dice {

    public int value;

    public Dice() {value = (int) (Math.random() * ((6 - 1) + 1)) + 1;}
}

   /* public void printDice() {
        System.out.println("Your dice: ");
        myDice.forEach((i) -> System.out.print(i.value + ", "));
      System.out.println(" ");
   }

    public void createDice(){
        //Create three instance of dice class
        for (int i = 0; i < 4; i++) {
          //  myDice.add(new Dice());
        }
    }

    public void shuffle() {
        //for (int i = 0; i < myDice.size(); i++) {
       //     myDice.get(i).roll();
        }

    }
*/
