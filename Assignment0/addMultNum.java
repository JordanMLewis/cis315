/*
Author: Jordan M. Lewis
Class: CIS 315, Spring 2017
Date: 04/05/17
*/

import java.util.Scanner;

public class addMultNum {
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		String[] operands;

		int numCommands = Integer.parseInt(sc.nextLine());
		int A;
		int B;
		int sum;
		int product;
		int i = 0;

		while(i < numCommands){
			//get array of 2 numbers
			operands = sc.nextLine().split(" ");
			//convert to ints
			if(operands.length == 2){
				A = Integer.parseInt(operands[0]);
				B = Integer.parseInt(operands[1]);
			
				//Calculate
				sum = A+B;
				product = A*B;
			
				System.out.println(String.valueOf(sum) + " " + String.valueOf(product));
			}
			i++;
		}
		sc.close();
	}
}
