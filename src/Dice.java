public class Dice {

    public int value;

    public Dice() {
        value = (int) (Math.random() * ((6 - 1) + 1)) + 1;}
}