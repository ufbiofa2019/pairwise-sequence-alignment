import java.util.*;

public class Reader {

    public static void main(String[] args) {
        Runtime run = Runtime.getRuntime();
        //calculate starting memory use and start time
        long startmem = run.totalMemory()- run.freeMemory();
        long starttime = System.nanoTime();
	    String randomSeq = generateSeq(600000000);
        //calculate ending memory use and end time
        long endtime = System.nanoTime();
        long endmem = run.totalMemory()- run.freeMemory();

        //calculate total memory/runtime
        long duration = endtime - starttime;
        long usedmem = endmem - startmem;

        System.out.println("RUNTIME IN NANOSEC: " + duration);
        System.out.println("MEMORY USE IN GB: " + (double)usedmem/1000000000);
        long seconds = duration/1000000000;
        long minutes = seconds/60;
        long hours = minutes/60;
        System.out.println("HOURS: " + hours + "\nMINUTES: " + minutes + "\nSECONDS: " + seconds);
    }

    /*Takes in an integer n and generates
    * a random sequence string of A,T,C,G of length n*/
    private static String generateSeq(int length) {
        StringBuilder str = new StringBuilder();
        int max =4,min=1;
        int range = max - min + 1;

        for (int i = 0; i < length; i++) {
            int rand = (int)(Math.random() * range) + min;
            if (rand == 1) {
                str.append("A");
            }
            if (rand == 2) {
                str.append("T");
            }
            if (rand == 3) {
                str.append("C");
            }
            if (rand == 4) {
                str.append("G");
            }
        }
        return str.toString();
    }
}
