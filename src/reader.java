import java.util.*;

import java.io.*; 
import java.util.*; 
import java.lang.*; 


public class Reader {

    public static void main(String[] args) {
    	
 	    String gene1 = "ATCAGAGTC"; 
 	    String gene2 = "TTCAGTC"; 
 	      
 	    getMaxScore(gene1, gene2); 
 	    
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
    
 	public static void getMaxScore(String gene1, String gene2) { 
 	   
 		int mismatchPenalty = -1;
 		int gapPenalty = -2;
 		int matchScore = 2;
 	    int gene1Length = gene1.length(); 
 	    int gene2Length = gene2.length(); 
 	    int maxLength = gene2Length + gene1Length; // maximum possible length 
 	    
 	      
 	    int scoreMatrix[][] = new int[maxLength + 1][maxLength + 1]; 
 	      
 	    
 	    //These first two for loops initialize the matrix, filling the first row/column with "gaps" and every other cell with zeroes
 	    for (int[] filler : scoreMatrix) {
 	    	Arrays.fill(filler, 0);
 	    } 
 	    
 	    for (int i = 0; i <= (maxLength); i++) 
 	    { 
 	        scoreMatrix[i][0] = i * gapPenalty; 
 	        scoreMatrix[0][i] = i * gapPenalty; 
 	    }     	  

 	    //Fill the matrix
 	    //This loop runs through each row, filling every cell in the matrix with the largest possible value
 	    //The condition checks if there is a match or mismatch. That score is then compared against the possibility of a gap occuring
 	    for (int i = 1; i <= gene1Length; i++) { 
 	        for (int j = 1; j <= gene2Length; j++) { 
 	        	
 	        	int largest = -2147483648;
 	        	
 	            if (gene1.charAt(i - 1) == gene2.charAt(j - 1)) { 
 	            	largest = Math.max(scoreMatrix[i][j - 1] + gapPenalty , scoreMatrix[i - 1][j] + gapPenalty);
 	            	largest = Math.max(largest, scoreMatrix[i - 1][j - 1] + matchScore); 
 	            } 
 	            
 	            else{ 
 	            	largest = Math.max(scoreMatrix[i][j - 1] + gapPenalty , scoreMatrix[i - 1][j] + gapPenalty);
 	            	largest = Math.max(largest , scoreMatrix[i - 1][j - 1] + mismatchPenalty); 
 	                
 	            } 
 	            
                 scoreMatrix[i][j] = largest;
 	        } 
 	    } 
 	    
 	    String matrixString = "";
 	    
 	    //fills matrixString with the score matrix for output
 	    for (int row = 0; row < gene1Length + 1; row++) {
 	    	  
             for (int  column= 0; column < gene2Length + 1; column++) {
             	System.out.print(scoreMatrix[row][column] + " ");
             	matrixString = matrixString  + scoreMatrix[row][column] + " ";
             }
             
             matrixString = matrixString + "\n";
             
             System.out.println("\n");
             
         }
 	    
 	    
 	    //outputs the score matrix to 2.02.txt
 	   try{
			PrintWriter outputFile2 = new PrintWriter("2.o2.txt", "UTF-8");
			outputFile2.println(matrixString);
			outputFile2.close();
 	   }
 	   catch (FileNotFoundException e) {
				System.out.println("File Not Found");
 	   }
 	   catch (UnsupportedEncodingException e) {
				System.out.println("Unsupported Encoding");
 	   }
	    
 	    int i = gene1Length;
 	    int j = gene2Length;
 	    int gene1Position = maxLength; 
 	    int gene2Position = maxLength;
 	    char gene1Alignment[] = new char[maxLength + 1];  
 	    char gene2Alignment[] = new char[maxLength + 1]; 
 	    
 	      
 	    //Backtracks through the matrix to find the optimal solution
 	    while ( !(i == 0 || j == 0)) 
 	    { 
 	        if (gene1.charAt(i - 1) == gene2.charAt(j - 1)) { 
 	            gene1Alignment[gene1Position--] = gene1.charAt(i - 1); 
 	            gene2Alignment[gene2Position--] = gene2.charAt(j - 1); 
 	            i--; 
 	            j--; 
 	        } 
 	        
 	        else if (scoreMatrix[i - 1][j - 1] + mismatchPenalty == scoreMatrix[i][j]) { 
 	            gene1Alignment[gene1Position--] = gene1.charAt(i - 1); 
 	            gene2Alignment[gene2Position--] = gene2.charAt(j - 1); 
 	            i--;
 	            j--; 
 	        } 
 	        
 	        else if (scoreMatrix[i - 1][j] + gapPenalty == scoreMatrix[i][j]) { 
 	            gene1Alignment[gene1Position--] = gene1.charAt(i - 1); 
 	            gene2Alignment[gene2Position--] = '_'; 
 	            i--; 
 	        } 
 	        
 	        else if (scoreMatrix[i][j - 1] + gapPenalty == scoreMatrix[i][j]) { 
 	            gene1Alignment[gene1Position--] = '_'; 
 	            gene2Alignment[gene2Position--] = gene2.charAt(j - 1); 
 	            j--; 
 	        } 
 	    } 
 	    
 	    //Fills out the rest of alignment for gene1
 	    while (gene1Position > 0) 
 	    { 
 	        if (i > 0) {
 	        	gene1Alignment[gene1Position--] = gene1.charAt(--i); 
 	        }
 	        
 	        else {
 	        	gene1Alignment[gene1Position--] = '_'; 
 	        }
 	    } 
 	    
 	    while (gene2Position > 0) 
 	    { 
 	        if (j > 0) {
 	        	gene2Alignment[gene2Position--] = gene2.charAt(--j); 
 	        }
 	        else {
 	        	gene2Alignment[gene2Position--] = '_'; 
 	        }
 	    } 
 	  
 	    
 	    int startIndexOfAlignment = 1; 
 	    
 	    //Finds relevant part of matrix
 	    for (i = maxLength; i >= 1; i--) { 
 	        if (gene2Alignment[i] == '_'  &&  gene1Alignment[i] == '_') { 
 	            startIndexOfAlignment = i + 1; 
 	            break; 
 	        } 
 	    } 
 	  
 	    /*PRINTED SOLUTION*/ 
 	    System.out.print("Maximum score: " + scoreMatrix[gene1Length][gene2Length] + "\n"); 
 	    
 	    //writes the optimal score to 
  	   try{
 			PrintWriter outputFile1 = new PrintWriter("2.o1.txt", "UTF-8");
 			outputFile1.println(scoreMatrix[gene1Length][gene2Length]);
 			outputFile1.close();
  	   }
  	   catch (FileNotFoundException e) {
 				System.out.println("File Not Found");
  	   } 
  	   catch (UnsupportedEncodingException e) {
 				System.out.println("Unsupported Encoding");
  	   }
  	   
  	   
  	   String gene1String = "";
  	   String gene2String = "";
 	    System.out.println("Aligned genes: "); 
 	    for (i = startIndexOfAlignment; i <= maxLength; i++) { 
 	        System.out.print(gene1Alignment[i]); 
 	        gene1String = gene1String + gene1Alignment[i];
 	    } 
 	    
 	    System.out.print("\n"); 
 	    for (i = startIndexOfAlignment; i <= maxLength; i++) { 
 	        System.out.print(gene2Alignment[i]); 
 	        gene2String = gene2String + gene2Alignment[i];
 	    } 
 	    
 	    String AlignmentOutputString = gene1String + "\n" + gene2String;
 	    
 	   try{
			PrintWriter outputFile3 = new PrintWriter("2.o3.txt", "UTF-8");
			outputFile3.println(AlignmentOutputString);
			outputFile3.close();
 	   }
 	   catch (FileNotFoundException e) {
				System.out.println("File Not Found");
 	   } 
 	   catch (UnsupportedEncodingException e) {
				System.out.println("Unsupported Encoding");
 	   }
 	    
 	    return; 
 	} 
 	  
}
