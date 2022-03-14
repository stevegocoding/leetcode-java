### Thoughts
* DFS
  * When serializing, traverse the tree with pre-order traversal. Append each node's value to a string with a splitter. Use a special character to represent the null node.
  * When deserializing, split the input string with the splitter, use a queue to poll each string representing node value sequentially. Keep reconstructing the left subtree first and then the right subtree. (alighs with the pre-order serialization).

```
        5
       / \
      3   #     -> [5,3,#,1,#,#,#]
     / \
    #   1
       /  \
      #    #
```
### Solution - DFS

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {
    private static final String SPLITTER = ",";
    private static final String NULL_NODE_VAL = "#";

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        final StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }
    
    private void serializeHelper(final TreeNode node, final StringBuilder sb) {
        if (node == null) {
            sb.append(NULL_NODE_VAL).append(SPLITTER);
            return;
        }
        
        sb.append(node.val).append(SPLITTER);
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        final String[] nodesStr = data.split(SPLITTER);
        final Deque<String> q = new LinkedList<>();
        q.addAll(Arrays.asList(nodesStr));
        return deserializeHelper(q);
    }
    
    private TreeNode deserializeHelper(Deque<String> q) {
        if (q.isEmpty()) {
            return null;
        }   
        
        final String s = q.poll();
        if (s.equals(NULL_NODE_VAL)) {
            return null;
        }
        
        final TreeNode node = new TreeNode(Integer.valueOf(s));
        node.left = deserializeHelper(q);
        node.right = deserializeHelper(q);
        return node;
    }
}
```