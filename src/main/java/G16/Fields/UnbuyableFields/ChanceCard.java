package G16.Fields.UnbuyableFields;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class ChanceCard {
    static int[] numchance = IntStream.range(1,41).toArray();

    public ChanceCard(int[] toArray) {
    }
    /* shuffles the values in the array. Code found on https://www.digitalocean.com/community/tutorials/shuffle-array-java
     */
    public int[] Shufflechancecard(){
        Random rand = new Random();

        for (int i = 0; i < numchance.length; i++) {
            int randomIndexToSwap = rand.nextInt(numchance.length);
            int temp = numchance[randomIndexToSwap];
            numchance[randomIndexToSwap] = numchance[i];
            numchance[i] = temp;
        }
        System.out.println(Arrays.toString(numchance));
        return numchance;
    }

    public int[] leftshiftarray(){
        int[] proxy = new int[numchance.length];
        for (int i = 0; i < numchance.length-1; i++) {
            proxy[i] = numchance[i + 1];
        }
        proxy[numchance.length-1] = numchance[0];
        return proxy;
    }

    public int[] getNumchance(){
        return numchance;
    }

    public void setNumchance(int[] proxy){
        numchance=proxy;
    }
}
