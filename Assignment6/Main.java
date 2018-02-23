import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/*
	Author: Jordan M. Lewis
	Date: 02/22/2018

	This program attempts to split a string of characters into English words using various methods.
*/

public class Main {

	public static Set<String> dictionary = new HashSet<String>();
	public static int[] wordTable;		//Will be used to reconstruct the valid sequence
	public static String result;		//Used for producing output
	public static Set<String> memo = new HashSet<String>();
	
	public static void main(String[] args) {
		loadDictionary("diction10k.txt");
		Scanner sc = new Scanner(System.in);
		int numPhrases = sc.nextInt();
		String phrase = "";
	
		//Skip first line	
		sc.nextLine();

		//Test each phrase with iterative and memoized functions
		for(int i = 1; i <= numPhrases; i++){
			System.out.println("phrase number:" + " " + Integer.toString(i));
			phrase = sc.nextLine();
			System.out.println(phrase);
			System.out.println("");
			wordTable = new int[phrase.length()];
			
			//Can it be split?
			//===================================================================
			//===================================================================
			result = ""; //reset string
			boolean iterativeResult = iterativeSplit(phrase);
			boolean memoizedResult = memoizedSplit(phrase);
			String[] resultArray = result.split(" ");
			Collections.reverse(Arrays.asList(resultArray));
			result = String.join(" ", resultArray);
			
			System.out.println("iterative attempt:"); 
			if(iterativeResult == true){
				System.out.println("YES, can be split"); 
				System.out.println(result);
			} else {
				System.out.println("NO, cannot be split"); 
			}
			System.out.println("");
			
			//===================================================================
			//===================================================================
			System.out.println("memoized attempt:");
			if(memoizedResult == true){
				System.out.println("YES, can be split"); 
				System.out.println(result);
			} else {
				System.out.println("NO, cannot be split"); 
			}
			
			System.out.print("\n\n");
		}
		sc.close();
	}

	public static boolean memoizedSplit(String str){
		int len = str.length();
		if(len == 0) { 
			return true; 
		} else if (memo.contains(str)){
			return false;
		} else {
			int i = 0;
			String word = "";
			while(i < str.length()){
				word += str.charAt(i);
				if(dictionary.contains(word)){
					if(memoizedSplit(str.substring(i+1))){
						result += word + " ";
						return true;	
					} else {
						i++;
					}
				} else {
					i++;
				}
			}

			//memo for later
			memo.add(str);
			return false;
		}
	}
	
	public static boolean iterativeSplit(String str){
		int[] memo = new int[str.length() + 1];
		int[][] used = new int[str.length()+1][str.length()+1];
		result = "";

		Arrays.fill(memo, -1);
		memo[0] = 0;
		for(int i = 0; i < str.length(); i++){
			if(memo[i] != -1){
				for(int j = i+1; j <= str.length(); j++){
					String substr = str.substring(i, j);
					if(dictionary.contains(substr)){
						used[i][j] = 1;
						memo[j] = i;
					}
				}
			}
		}
		
		//If breakable, print result
		/*
		if(memo[str.length()] != -1){
			result = "";
			//Greedily choose the longest words first
			for(int i = 0; i <= str.length(); i++){
				for(int j = str.length(); j >= 0; j--){
					if(used[i][j] == 1){
						result += str.substring(i, j) + " ";
						i = j - 1;
					}
				}
			}
		}
		*/
		return memo[str.length()] != -1;
	}
	
	public static void loadDictionary(String dictionaryFileName){
		File infile = new File(dictionaryFileName);
		try {
			Scanner scan = new Scanner(infile);
			String line;
			while(scan.hasNext()){
				line = scan.next();
				dictionary.add(line.trim());
			}
			scan.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
}
