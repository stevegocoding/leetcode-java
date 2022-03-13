### Thoughts:
* Topological Sorting, BFS or DFS https://en.wikipedia.org/wiki/Topological_sorting#Depth-first_search 
* Use adjacency list to represent the graph. 
* For BFS, have to maintain a indegree for each unique character in the charset.
* For DFS, use temporary and permanent markers for each unique charater to detect circles.

### Code - BFS 
```java
class Solution {
    public String alienOrder(String[] words) {
        final Map<Character, List<Character>> adjList = new HashMap<>();
        final Map<Character, Integer> indegree = new HashMap<>();
        
        // Initialize adjacency list and indgree map.
          for (final String word : words) {
            for (final char ch : word.toCharArray()) {
                adjList.put(ch, new ArrayList<>());
                indegree.put(ch, 0);
            }
        }
        
        // Build the graph from the input.
        for (int i = 0; i < words.length - 1; ++i) {
            final String word1 = words[i];
            final String word2 = words[i + 1];
            
            // Cannot determine relative order between characters if word2 is a prefix of word1
            if (word1.length() > word2.length() && word1.startsWith(word2)) {
                return "";
            }
            
            final int len = Math.min(word1.length(), word2.length());
            for (int j = 0; j < len; ++j) {
                final Character ch1 = word1.charAt(j);
                final Character ch2 = word2.charAt(j);
                if (ch1 != ch2) {
                    adjList.get(ch1).add(ch2);
                    indegree.put(ch2, indegree.get(ch2) + 1);
                    break;
                }
            }
        }
        
        final Deque<Character> q = new LinkedList<>();
        
        for (final Map.Entry<Character, Integer> entry: indegree.entrySet()) {
            if (entry.getValue() == 0){
                q.add(entry.getKey());
            }
        }
        
        final StringBuilder sb = new StringBuilder();
        while (!q.isEmpty()) {
            int n = q.size();
            for (int i = 0; i < n; ++i) {
                final Character cur = q.poll();
                sb.append(cur);
                for (final Character ch : adjList.get(cur)) {
                    indegree.put(ch, indegree.get(ch) - 1);
                    if (indegree.get(ch) == 0) {
                        q.add(ch);
                    }
                }
            }
        }

        // Can only return a valid result if all characters in the charset are visited.     
        return sb.length() == indegree.size() ? sb.toString() : "";
    }
}
```

### Code - DFS
```java
class Solution {
    public String alienOrder(String[] words) {
        final Map<Character, List<Character>> adjList = new HashMap<>();
        
        /**
            0: init status
            1: visiting
            2: visited
        */
        final Map<Character, Integer> visited = new HashMap<>();
        
        for (final String word : words) {
            for (final char ch : word.toCharArray()) {
                adjList.put(ch, new ArrayList<>());
                visited.put(ch, 0);
            }
        }
        
        // Build the graph from the input.
        for (int i = 0; i < words.length - 1; ++i) {
            final String word1 = words[i];
            final String word2 = words[i + 1];
            
            // Cannot determine relative order between characters if word2 is a prefix of word1
            if (word1.length() > word2.length() && word1.startsWith(word2)) {
                return "";
            }
            
            final int len = Math.min(word1.length(), word2.length());
            for (int j = 0; j < len; ++j) {
                final Character ch1 = word1.charAt(j);
                final Character ch2 = word2.charAt(j);
                if (ch1 != ch2) {
                    adjList.get(ch1).add(ch2);
                    break;
                }
            }
        }
        
        final Set<Character> charset = new HashSet<>();
        for (final String word : words) {
            for (final char ch : word.toCharArray()) {
                charset.add(ch);
            }
        }
        
        // DFS
        final StringBuilder sb = new StringBuilder();
        for (final Character ch : adjList.keySet()) {
            if (visited.get(ch) == 0) {
                if (!dfs(ch, adjList, visited, sb)) {
                    return "";
                }
            }
        }
        
        return sb.reverse().toString();
    }
    
    private boolean dfs(final Character ch, 
                        final Map<Character, List<Character>> adjList, 
                        final Map<Character, Integer> visited,
                        final StringBuilder sb) {
        visited.put(ch, 1);
        for (final Character c : adjList.get(ch)) {
            if (visited.get(c) == 1) {
                return false;
            }
            if (visited.get(c) == 0) {
                if (!dfs(c, adjList, visited, sb)) {
                    return false;
                }
            }
        }
        sb.append(ch);
        visited.put(ch, 2);
        
        return true;
    }
}
```