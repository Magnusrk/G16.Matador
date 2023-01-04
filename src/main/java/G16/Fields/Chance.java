package G16.Fields;

import java.util.stream.IntStream;

public class Chance extends Field {
    //static ChanceCard chancecard = new ChanceCard(IntStream.range(1,21).toArray());


    public Chance(String name) {
        super(name);
    }

    /**
     * Puts the chancecard you've drawn on the bottom pile
     * @return The array that contains the order of chancecards
     */
    /*public int[] drawChancecard() {
        chancecard.getNumchance();
        chancecard.setNumchance(chancecard.leftshiftarray());
        return chancecard.getNumchance();
    }*/
}
