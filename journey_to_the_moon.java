/*
 * Solution to the HackerRank problem:
 * https://www.hackerrank.com/challenges/journey-to-the-moon/problem
 * 
 * Problem:
 *
 * The member states of the UN are planning to send 2 people to the moon. They want 
 * them to be from different countries. You will be given a list of pairs of astronaut
 * id's. Each pair is made of astronauts from the same country. Determine how many
 * pairs of astronauts from different countries they can choose from. For example, we
 * have the following data on 2 pairs of astronauts, and 4 astronauts total, numbered
 * 0 through 3:
 *                    (1 2)
 *                    (2 3)
 * The above implies that astronaut 1 and 2 are from the same country, and astronauts 2
 * and 3 are also from the same country. Therefore, astronauts by country are [0] and
 * [1, 2, 3]. The UN can choose from 3 pairs of astronauts: [0, 1], [0,2] and [0,3].
 */

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution {

    private static int dfs(int astroID, Map<Integer, Set<Integer>> adjacencyList, boolean[] visited) {
        if (visited[astroID]) {
            return 0;
        }
        int astronautsInCurrCC = 1;
        visited[astroID] = true;
        if (adjacencyList.containsKey(astroID)) {
            Set<Integer> neighbors = adjacencyList.get(astroID);
            for (int neighbor : neighbors) {
                if (!visited[neighbor]) {
                    astronautsInCurrCC += dfs(neighbor, adjacencyList, visited);
                }
            }
        }
        return astronautsInCurrCC;
    }

    public static long getAstronautPairCnt(int astroCnt, Map<Integer, Set<Integer>> adjacencyList) {
        // First, count the number of connected components in our astronaut graph
        long totalAstroPairs = 0, totalAstronautsWithCompatriots = 0;
        boolean[] visitedAstros = new boolean[astroCnt];
        List<Integer> connComponentSizes = new ArrayList<Integer>();

        for (int astronautID : adjacencyList.keySet()) {
            if (!visitedAstros[astronautID]) {
                int currCCSize = dfs(astronautID, adjacencyList, visitedAstros);
                // The connected component that includes astronautID is of size 'currCCSize'
                connComponentSizes.add(currCCSize);
                // Remember: each astronaut in the adjacency list necessarily has a
                // compatriot, as the adjacency list was populated using astronaut pairs.
                totalAstronautsWithCompatriots += currCCSize;
            }
        }

        // Compute total number of astronaut pairs s.t both astronauts have compatriots.
        for (int i = 0; i < connComponentSizes.size(); i++) {
            for (int j = i+1; j < connComponentSizes.size(); j++) {
                totalAstroPairs += connComponentSizes.get(i) * connComponentSizes.get(j);
            }
        }

        // totalUniqueAstronauts is the total number of astronauts from unique countries.
        // In this graph, each unique astronaut is a connected component of size 1.
        long totalUniqueAstronauts = astroCnt - totalAstronautsWithCompatriots;

        // Add all permissible pairs including exactly one astronaut from a unique country
        long pairsWithOneUniqueAstronaut = totalAstronautsWithCompatriots * totalUniqueAstronauts;

        // Add all permissible pairs s.t. both astronauts are from a unique country
        // This is calculated as (n choose 2), where n = totalUniqueAstronauts
        long pairsWithTwoUniqueAstronauts = (totalUniqueAstronauts * (totalUniqueAstronauts - 1) / 2);

        totalAstroPairs += (pairsWithOneUniqueAstronaut + pairsWithTwoUniqueAstronauts);

        return totalAstroPairs;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int astronautCnt = sc.nextInt();
        int pairCnt = sc.nextInt();
        Map<Integer, Set<Integer>> adjacencyList = new HashMap<Integer, Set<Integer>>();
        Set<Integer> neighbors;
        for (int i = 0; i < pairCnt; i++) {
            int[] astronauts = new int[]{sc.nextInt(), sc.nextInt()};
            for (int j = 0; j < 2; j++) {
                if (adjacencyList.containsKey(astronauts[j])) {
                    neighbors = adjacencyList.get(astronauts[j]);
                } else {
                    neighbors = new HashSet<Integer>();
                }
                // Add each astronaut in the pair to the other's "neighbors" Set:
                neighbors.add(astronauts[j^1]);
                adjacencyList.put(astronauts[j], neighbors);
            }
        }
        long result = getAstronautPairCnt(astronautCnt, adjacencyList);
        System.out.println(result);

        sc.close();
    }
}
