import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
/*
	Author: Jordan M. Lewis
	Date: 02/22/2018	

 * This program gives:
 * 
 *(i)   the length of the shortest path from node 1 to node n
 *(ii)  the length of the longest path from 1 to n
 *(iii) the number of distinct paths from 1 to n.
 */

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int numGraphs = Integer.parseInt(sc.nextLine());
		int numNodes = 0;
		int numEdges = 0;
		int u = 0;
		int v = 0;
		
		boolean[][] adjMatrix;
		String[] edge;
		
		//Make graphs and calculate
		for(int i = 1; i <= numGraphs; i++){
			
			numNodes = Integer.parseInt(sc.nextLine());     // N >= 1
			numEdges = Integer.parseInt(sc.nextLine());	    // M >= 0
			adjMatrix = new boolean[numNodes+1][numNodes+1];	//Large enough for all vertices (+1 because of index)
			
			//Add all edges to the adjacency matrix
			for(int j = 1; j <= numEdges; j++){
				edge = sc.nextLine().split(" ");  				//['x', 'y']
				u = Integer.parseInt(edge[0]);
				v = Integer.parseInt(edge[1]);
				adjMatrix[u][v] = true;			 				//Add outgoing edge to the matrix
			}
			
			//Report to user:
			System.out.println("graph number: " + i);
			System.out.println("Shortest path: " + shortestPathFrom1toN(adjMatrix, numNodes));
			System.out.println("Longest path: " + longestPathFrom1toN(adjMatrix, numNodes));
			System.out.println("Number of paths: " + distinctPathsFrom1toN(adjMatrix, numNodes));
			System.out.println();
		}
		sc.close();
	}
	
	static int shortestPathFrom1toN(boolean[][] adj, int numNodes){
		//Modified version of Dijkstra's Algorithm
		Queue<Integer> Q = new LinkedList<Integer>();
		int[] distances = new int[numNodes+1];
		int min;
		int newPathDistance;
		
		for(int i = 1; i <= numNodes; i++){
			distances[i] = Integer.MAX_VALUE; 		//represents infinity, 2^31 - 1
			Q.add(i);
		}
		distances[1] = 0;
		while(!Q.isEmpty()){
			min = Q.poll();
			for(int i = min; i <= numNodes; i++){
				newPathDistance = distances[min] + 1;
				if(adj[min][i] == true && newPathDistance < distances[i]){
					distances[i] = newPathDistance;
				}
			}
		}
		
		return distances[numNodes];
	}
	
	static int longestPathFrom1toN(boolean[][] adj, int numNodes){
		//conduct modified breadth-first search
		int ans = 0;
		Set<Integer> set = new HashSet<Integer>();
		Queue<Integer> Q = new LinkedList<Integer>();
		
		set.add(1);
		Q.add(1);
		int current;
		
		while(!Q.isEmpty()){
			current = Q.poll();
			if(current == numNodes){
				break;
			}
			for(int i = 1; i <= numNodes; i++){
				if(adj[current][i] == true && !set.contains(i)){
					set.add(i);
					ans++;
					Q.add(i);
				}
			}
		}
		return ans;
	}
	
	static int distinctPathsFrom1toN(boolean[][] adj, int numNodes){
		//If a there are n paths from u to v,
		//adding another edge from v to w has n+1 paths
		int[] counts = new int[numNodes+1];
		
		for(int i = 1; i <= numNodes; i++){
			counts[i] = 0;
		}
		counts[1] = 1;
		
		for(int i = 1; i <= numNodes; i++){
			for(int j = i; j <= numNodes; j++){
				if(adj[i][j] == true){
					counts[j] = counts[j] + counts[i];
				}
			}
		}
		return counts[numNodes];
	}
}
