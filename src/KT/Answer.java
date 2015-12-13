package KT;


import java.util.Arrays;

public class Answer {
//--------------KT2-----10--------------------------

 /*
    public static void main (String[] args) {
        String s = "Tere, TUDENG, 1234!";
        String t = asenda (s); // "Tere**TUDENG*******"
        System.out.println (s + " > " + t);
    }

    public static String asenda (String s) {

        String asenda ="";

        for (int i = 0; i < s.length() ; i++) {
            if(Character.isWhitespace(s.charAt(i))){
                asenda += '-';
            }
            else{
                asenda += s.charAt(i);
            }

        }

        return s; // TODO!!! Your code here
    }
*/

 /*
 public static void main (String[] args) {
     String s = "Tere, TUDENG, 1234!";
     String t = asenda (s); // "Tere**TUDENG*******"
     System.out.println (s + " > " + t);
 }

    public static String asenda (String s) {

        String asenda ="";

        for (int i = 0; i < s.length() ; i++) {

            char a = s.charAt(i);
            asenda += a == ' ' ? '-' : a;
            if(Character.isWhitespace(s.charAt(i))){
                asenda += '-';
            }
            else{
                asenda += s.charAt(i);
            }

        }

        return asenda; // TODO!!! Your code here
    }

   */

    //-------------KT2 ------------11----------------------
/*
    public static void main (String[] args) {
        System.out.println (result (new double[]{0., 1., 2., 3., 4.}));
        // YOUR TESTS HERE
    }

    public static double result (double[] marks) {

        double[] mingi = new double[marks.length];
        for (int i = 0; i < marks.length; i++) {
            mingi[i] = marks[i];
        }

        Arrays.sort(mingi);
        double sum = 0;
        double vastus = 0;
        for (int i = 1; i <mingi.length -1 ; i++) {
            sum += mingi[i];

        }
        vastus = sum / (mingi.length-2);

        return vastus;  // TODO!!! YOUR PROGRAM HERE
    }
*/
/*
//------------------KT2 12------------------------------
    public static void main(String[] args) {
        int[] res = veeruSummad (new int[][] { {1,2,3}, {4,5,6} }); // {5, 7, 9}
        System.out.println(Arrays.toString(res));
        // YOUR TESTS HERE
    }

    public static int[] veeruSummad(int[][] m) {
        // TODO!!!    YOUR PROGRAM HERE

        int kõigepikkemmassiivis = 0;

        for (int i = 0; i <m.length; i++) {
            if (m[i].length > kõigepikkemmassiivis){
                kõigepikkemmassiivis = m[i].length;
            }
        }

        int uusMassiv[] = new int[kõigepikkemmassiivis];
        for (int i = 0; i <uusMassiv.length; i++) {
            uusMassiv[i] = Integer.MIN_VALUE;
        }

        for (int i = 0; i <m.length ; i++) {
            for (int j = 0; j < m[i].length; j++) {
               uusMassiv[j] = Math.max(m[i][j], uusMassiv[j]);

            }

        }

        return uusMassiv;
    }
*/
}