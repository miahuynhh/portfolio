import java.math.BigInteger;

public class Guesser {
    /**
     * This method must return the number Chooser c has chosen. c can guess() any
     * number and tell you whether the number is "correct", "higher", or "lower".
     *
     * @param c The "chooser" that has chosen a number you must guess.
     * @return The number that the "chooser" has chosen
     */
    public static BigInteger findNumber(Chooser c) {
        // Tip: If you're not sure how to work with BigInteger numbers, we encourage
        // you to look up its Javadoc online

        BigInteger high = BigInteger.ONE;
        String answer = c.guess(high);

        while (answer.equals("higher")) {
            high = high.multiply(BigInteger.TWO);
            answer = c.guess(high);
        }

        BigInteger low = high.divide(BigInteger.TWO);
        BigInteger mid = high;

        while (!answer.equals("correct")) {
            mid = high.add(low).divide(BigInteger.TWO);
            answer = c.guess(mid);
            if (answer.equals("lower")) {
                high = mid.subtract(BigInteger.ONE);
            } else if (answer.equals("higher")) {
                low = mid.add(BigInteger.ONE);
            }
        }
        return mid;
    }
}
