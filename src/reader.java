import java.util.*;

import java.io.*; 
import java.util.*; 
import java.lang.*; 
import java.io.PrintWriter;
import java.io.File;

public class reader {

    public static void main(String[] args) {
    	
 	    String gene1 = "ATCAGAGTC"; 
 	    String gene2 = "TTCAGTC"; 
		
		 if(gene1.length() < gene2.length()){ //I just want gene1 to always be the biggest gene
			String k = gene1;
			gene1 = gene2;
			gene2 = k;
		 }
		 try{
		 PrintWriter outOne = new PrintWriter("2_01.txt", "UTF-8");
			outOne.println( getOptimalScore(gene1, gene2));
			outOne.close();
		 }
		 catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported Encoding");
		}

		 getMaxScore(gene1, gene2); 
		 
		 //Get Optimal Score, optimal alignment, and if there are multiple optimal alignments
		 //getOptimalScore(gene1, gene2);
		 getOptimalAlignment(gene1, gene2);
		  //getMoreAlignments(gene1, gene2);
		  
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
 	    String m = "";
 	      
 	    int scoreMatrix[][] = new int[maxLength + 1][maxLength + 1]; 
 	      
 	    for (int[] filler : scoreMatrix) {
 	    	Arrays.fill(filler, 0);
 	    } 
 	    
 	    for (int i = 0; i <= (maxLength); i++) 
 	    { 
 	        scoreMatrix[i][0] = i * gapPenalty; 
 	        scoreMatrix[0][i] = i * gapPenalty; 
 	    }     	  

 	    //Fill the matrix
 	    for (int i = 1; i <= gene1Length; i++) { 
 	        for (int j = 1; j <= gene2Length; j++) { 
 	        	
 	        	int largest = - 2147483648;
 	        	
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
 	    
 	    //Display filled matrix
 	    System.out.println("\n\n");
 	    for (int row = 0; row < scoreMatrix.length; row++) {
 	    	  
             for (int  column= 0; column < scoreMatrix[row].length; column++) {
				m = m + scoreMatrix[row][column] + " " ;
				 //System.out.print(scoreMatrix[row][column] + " ");
				 //System.out.println(m);
             }
             
             System.out.println("\n");
             m = m + "\n";
		 }
		
		 try{
			PrintWriter outTwo = new PrintWriter("2_02.txt", "UTF-8");
			outTwo.println(m);
			outTwo.close();
			 }catch (FileNotFoundException e) {
				System.out.println("File Not Found");
			}catch (UnsupportedEncodingException e) {
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
 	    
 	    //Fills out the rest of alignment
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
 	    System.out.println("Aligned genes: "); 
 	    for (i = startIndexOfAlignment; i <= maxLength; i++) { 
 	        System.out.print(gene1Alignment[i]); 
 	    } 
 	    
 	    System.out.print("\n"); 
 	    for (i = startIndexOfAlignment; i <= maxLength; i++) { 
 	        System.out.print(gene2Alignment[i]); 
 	    } 
 	    
 	    return; 
	 } 
	     
    //Get the optimal score
    public static String getOptimalScore(String g1, String g2){
		int o = 0;
		//check for shortest length and print it out
        if(g1.length() >= g2.length()){
			 o = g2.length();
        }
        else if(g1.length() <= g2.length()){
            o = g1.length();
        }
		return "The Optimal Score is: " + o;
	}
	
	public static void getOptimalAlignment(String g1, String g2){
		//check for optimal alignment
		//use for loop to iterate through, have a starting position and ending position to determine where equal substrings are located in the strings
		//Can just check for multiple alignents as well
		String collection = "";
		int si = 0; int ji = 0; int c = 0; int d = 0; int e = 0; int f = 0;
		int oneLen = g1.length(); int twoLen = g2.length();
		for(int i = 0; i < g1.length();){
			if(i == oneLen){
				break;
			}
			for(int j = 0; j < twoLen; j++){
				//keep c to count the number of equal chars in a row
				if(c != 0 && i != oneLen && e == 0){
					//System.out.println("i is: " + i);
					i++;	
				}
				if(i != oneLen && g1.charAt(i) == g2.charAt(j) && c == 0){
					si = i;
					ji = j;
					e = 0;
					f = 1;
					c++;
					//System.out.println("1st match with " + g1.charAt(i) + " and " + g2.charAt(j));
					continue;
				}
				else if(i != oneLen && g1.charAt(i) != g2.charAt(j) && c < 3){
					c = 0;
					//System.out.println("3 if is reached, g1 at: " +g1.charAt(i) + " g2 at: " + g2.charAt(j));
					//get it to stay on the same iteration
					if(f == 1){
						j = 0;
						f = 0;
					}
					
				}
				else if(c > 2){
					//System.out.println("d is reached");
					//System.out.println("c is: " + c);
					//if the characters don't equal eachother and there are 3 or more equal characters or the end of the String has been reached and the characters don't equal
					//eachother and there are at least 3 equal chars in a row it inserts dashes into the appropriate string at the appropriate place
					 //int se = i + c -1;
					 int je = ji + c - 1;
					 int diff = oneLen-twoLen;
					 String n1 = g1; String n2 = g2; String dash = "";
					d++; //keep the number of alignments
					//System.out.println(si);
					//System.out.println(ji);
					for(int k = 0; k < diff; k++){
						dash = dash + "-";
					}
							
							if(si > ji) {
							n2 = g2.substring(0, ji) + dash + g2.substring(ji);
							}
							else if(si < ji){
								n1 = g1.substring(0, si) + dash + g1.substring(si);
							}
							else{
								if(je != g2.length()-1){
									n2 = g2.substring(0, je+1) + dash + g2.substring(je+1); 
									}
									else{
										n2 = g2.substring(0, je+1) + dash + g2.substring(je); 
									}
							}
					//System.out.println(n1 + "\n" + n2 + "\n");	
					if(d == 1){
						try{
						PrintWriter outThree = new PrintWriter("2_03.txt", "UTF-8");
							outThree.println(n1 + "\n" + n2 + "\n");
							outThree.close();
						}catch (FileNotFoundException a) {
							System.out.println("File Not Found");
						}catch (UnsupportedEncodingException a) {
							System.out.println("Unsupported Encoding");
						}
					}
					else {
						try{
						PrintWriter outFour = new PrintWriter("2_04.txt", "UTF-8");
							outFour.println("YES");
							outFour.close();
						}
						catch (FileNotFoundException b) {
							System.out.println("File Not Found");
						}catch (UnsupportedEncodingException b) {
							System.out.println("Unsupported Encoding");
						}
					}
					
					collection = collection + n1 + "\n" + n2 + "\n";

					if(i <= oneLen-2 && j <= twoLen - 2){
						//System.out.println("in");
						if(g1.charAt(i) != g2.charAt(j)){
							//System.out.println("ccccccccccccccin");
							//System.out.println(g1.charAt(i) + " and " + g2.charAt(j));
							c = 0;
						}
						else{c++;}
					}
					else if(j == twoLen-1){
						c = 0;
						continue;
					}
					//else{continue;}
					// else if (i == oneLen-1 && j == twoLen - 1){
					// 	if(g1.charAt(i) != g2.charAt(j+1)){
					// 		c = 0;
					// 	}
					// }
				}
				else if(i != g1.length() && g1.charAt(i) == g2.charAt(j) && c != 0 && i != g1.length()){
					c++;
					e = 0;
					f = 1;
					//System.out.println("more matches with " + g1.charAt(i) + " and " + g2.charAt(j));
					continue;
				}
				
			}
			
		}
		//if d is incremented, there has been one or more assignments 
		if(d > 1) {
			System.out.println("\n" + "\n" + "Yes, there are " + d + " different alignments");
		}
		else if(d == 1){
			System.out.println("\n" + "\n" + "No, there is only one Alignment");
		}
		else{
			System.out.println("\n" + "\n" + "There are no alignments"); //Just in case
		}
		

		try{
		PrintWriter outFive = new PrintWriter("2_05.txt", "UTF-8");
		outFive.println(d);
		outFive.println(collection);
		outFive.close();
		}
		catch (FileNotFoundException z) {
			System.out.println("File Not Found");
		}catch (UnsupportedEncodingException z) {
			System.out.println("Unsupported Encoding");
		}
 	  
	}
}
