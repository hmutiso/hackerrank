/*
 * Solution to the HackerRank problem: "Cities and Libraries":
 * https://www.hackerrank.com/challenges/torque-and-development/problem
 *
 * Problem:
 *
 * The Ruler of HackerLand believes that every citizen of the country should have
 * access to a library. Unfortunately, HackerLand was hit by a tornado that destroyed
 * all of its libraries and obstructed its roads! As you are the greatest programmer
 * of HackerLand, the ruler wants your help to repair the roads and build some new
 * libraries efficiently.
 *
 * HackerLand has n cities numbered from 1 to n. The cities are connected by m
 * bidirectional roads. A citizen has access to a library if:
 *    1) Their city contains a library, or
 *    2) They can travel by road from their city to a city containing a library.
 * The cost of repairing any road is "roadCost" dollars, and the cost to build a
 * library in any city is "libraryCost" dollars.
 * 
 * You are given q queries, where each query consists of a map of HackerLand and
 * value of 'roadCost' and 'libraryCost'. For each query, find the minimum cost of
 * making libraries accessible to all the citizens and print it on a new line.
 */

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution {
   
    /*
     * Use Depth First Search to find the total number of cities reachable
     * from 'currCity'. Each dfs() call returns the size of the connected
     * component containing 'currCity'.
     */
    public static int dfs(int currCity, boolean[] visitedCities, Map<Integer, List<Integer>> adjacencyList) {
        if (visitedCities[currCity]) {
            return 0;
        }
        int visitedCnt = 1;
        visitedCities[currCity] = true;
        if (adjacencyList.containsKey(currCity)) {
            List<Integer> neighbors = adjacencyList.get(currCity);
            for (int neighbor : neighbors) {
                visitedCnt += dfs(neighbor, visitedCities, adjacencyList);
            }
        }
        return visitedCnt;
    }
    
    public static long getMinLibraryAndRoadCost(int cityCnt, long libCost, long roadCost,
                                                Map<Integer, List<Integer>> adjacencyList) {
        if (libCost <= roadCost) {
            // Cannot do better than building a library in every city
            return (libCost * cityCnt);
        }
        long totalCost = 0;
        boolean[] visitedCities = new boolean[cityCnt+1];
        
        for (int currCity = 1; currCity <= cityCnt; currCity++) {
            if (!visitedCities[currCity]) {
                int citiesInCC = dfs(currCity, visitedCities, adjacencyList);
                /*
                 * For this connected component, build one library and (n-1) roads,
                 * where n is the number of cities in the connected component.
                 * Note: if a city has no neighbors (i.e. it has an in-degree and
                 * out-degree of zero), it will necessary have its own library.
                 */
                totalCost += libCost;
                totalCost += ((citiesInCC-1) * roadCost);
            }
        }
        return totalCost;
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int queryCnt = in.nextInt();
        for (int i=0; i < queryCnt; i++) {
            int cityCnt = in.nextInt();
            int roadCnt = in.nextInt();
            long libCost = in.nextLong();
            long roadCost = in.nextLong();
            Map<Integer, List<Integer>> adjacencyList = new HashMap<Integer, List<Integer>>();
            for (int j=0; j < roadCnt; j++) {
                int[] cities = new int[] {in.nextInt(), in.nextInt()};
                List<Integer> adjacentCities;
                for (int k=0; k < 2; k++) {
                    if (adjacencyList.containsKey(cities[k])) {
                        adjacentCities = adjacencyList.get(cities[k]);
                    } else {
                        adjacentCities = new ArrayList<Integer>();
                    }
                    adjacentCities.add(cities[k^1]);
                    adjacencyList.put(cities[k], adjacentCities);
                }
            }
            long minLibAndRoadCost = getMinLibraryAndRoadCost(cityCnt, libCost, roadCost, adjacencyList);
            System.out.println(minLibAndRoadCost);
        }
        in.close();
    }
}
