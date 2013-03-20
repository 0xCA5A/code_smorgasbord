
// http://www.karlin.mff.cuni.cz/network/prirucky/javatut/java/threads/simple.html

import java.math.BigDecimal;

class WorkerThread extends Thread
{
    public WorkerThread(String str)
    {
        super(str);
    }

    //source: http://stackoverflow.com/questions/5566187/find-the-value-of-pi-till-50-digits
    private void get_pi(int nr_of_terms)
    {
        int nr_of_decimal_places = 50;

        BigDecimal sum = new BigDecimal(0);      // final sum
        BigDecimal term = new BigDecimal(0);           // term without sign
        BigDecimal sign = new BigDecimal(1.0);     // sign on each term

        BigDecimal one = new BigDecimal(1.0);
        BigDecimal two = new BigDecimal(2.0);

        for (int k = 0; k < nr_of_terms; k++)
        {
            BigDecimal count = new BigDecimal(k);
            //term = 1.0/(2.0*k + 1.0);
            BigDecimal temp1 = two.multiply(count);
            BigDecimal temp2 = temp1.add(one);
            term = one.divide(temp2, nr_of_decimal_places, BigDecimal.ROUND_FLOOR);

            //sum = sum + sign*term;
            BigDecimal temp3 = sign.multiply(term);
            sum = sum.add(temp3);

            sign = sign.negate();
        }

        BigDecimal pi = new BigDecimal(0);
        BigDecimal four = new BigDecimal(4);
        pi = sum.multiply(four);

        System.out.println("[" + getName() + "] calculated pi (approx., " + nr_of_terms + " terms and " + nr_of_decimal_places + " Decimal Places): " + pi);
//         System.out.println("actual pi: " + Math.PI);
    }

    private void cause_high_load()
    {
        for (double i = 0; i < 1000000000; i++) 
        {
            ;;;
        }
    }

    public void run()
    {
        System.out.println("[" + getName() + "] in run...");
        get_pi(1000000000);
        System.out.println("[" + getName() + "] before exit...");
    }
}