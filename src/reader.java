import java.util.*;

public class Reader {

    public static void main(String[] args) {
	    System.out.println("Hello world");
	    String randomSeq = generateSeq(1000);
    }

    public static String generateSeq(int length) {
        String str = "";
        int max =4,min=1;
        int range = max - min + 1;

        for (int i = 0; i < length; i++) {
            int rand = (int)(Math.random() * range) + min;
            if (rand == 1) {
                str += "A";
            }
            if (rand == 2) {
                str += "T";
            }
            if (rand == 3) {
                str += "C";
            }
            if (rand == 4) {
                str += "G";
            }
            //System.out.println(rand);
        }
        System.out.println(str);
        return str;
    }
}
