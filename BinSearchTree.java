import java.io.*;
import javax.swing.JOptionPane;
/**
 * The following class called BinSearchTree creates an empty 
 * tree, and allows the user of the class to insert new nodes into the tree.  
 */

 

class BinSearchTree {
	
	Node root;
	
	/*  the following constructor creates an empty tree. */
	public BinSearchTree() {
		root = null;
	}
	
	/**
	 * the following method, inserts a new node that contains several data
	 * fields, the student's id, faculty, major, and year into the list.
	 * @param id - student's id number
	 * @param faculty - faculty code
	 * @param major - student's major
	 * @param year  - student's year of study
	 */
	public void insert(String id, String faculty, String major, String year) {
		
		Node node = new Node(id, faculty, major, year);
		
		if(root == null)
			root = node;
		else{
			Node cur;
			// find the location to insert a new node.
			cur = search(root, node);
			// the following if...else block attaches the new node to the left or right wing. 
			 
			if(cur.data.id.length() >node.data.id.length())
				cur.left=node;
			else if (cur.data.id.length() <node.data.id.length())
				cur.right = node;
			else if (cur.data.id.compareTo(node.data.id)<0)
				cur.right=node;
			else if (cur.data.id.compareTo(node.data.id)>0)
				cur.left=node;
			// if data already exists, send an error message to the user.
			else { 
				JOptionPane.showMessageDialog(null, "\nCannot insert: data already" +
																			" exists: \n" + id, " Warning",JOptionPane.PLAIN_MESSAGE);
				node = null;
			}  
		}
	}
	
	/** 
	 * Returns true if tree is empty 
	 */
	
	public boolean empty() {
		return (root == null);
	}
	
	/** 
	 * removes all nodes 
	 */
	
	public void destroy() {
//		splice();
		root = null;
	}
	
	public void splice() {
		if(!empty()){
			
			if (root.left!=null){
				root = root.left;
				splice();
			}
			root.left = null;
			if (root.right!=null){
				root = root.right;
				splice();
			}
			root.right = null;
		}
	}
	
	/**
	 * The following method finds and returns a reference to a node with the
	 * target id or returns null, if it fails to find a node containing target id.
	 * @param start - starting point of the tree (root node)
	 * @param target_id - id number that is being searched for
	 * @return - node that matches its id with the target id. Otherwise returns null.
	 */	
	public Node find( Node start, String target_id) {
		if(start == null) return null;
		if(start.data.id.equals(target_id)) return start;
		if(target_id.compareTo(start.data.id)>0) 
			return find(start.right, target_id); 
		else if(target_id.compareTo(start.data.id)<0)
			return find(start.left, target_id);
		return null;
	}
		
	/**
	 * The following function returns a reference to the node that the new node
	 * must be attached to. Or returns null if such a node does not exist.
	 * @param cur - current node that the new node should be attached to
	 * @param node - the new node 
	 * @return - node that matches its id with the target id. Otherwise returns null.
	 */
	private Node search(Node cur, Node node) {
		if((cur.left ==null && cur.right ==null)||
			 ((node.data.id.length()< cur.data.id.length()) && (cur.left==null))||
			 ((node.data.id.length()> cur.data.id.length()) && (cur.right==null)))
			return cur;
		if((node.data.id.length() < cur.data.id.length()) && (cur.left != null))
			cur =search(cur.left, node);
		else if((node.data.id.length()>cur.data.id.length()) && (cur.right != null))
			cur =search(cur.right, node);
		else if((node.data.id.compareTo( cur.data.id)>0) && cur.right!= null)
			cur=search(cur.right, node);
		else if((node.data.id.compareTo( cur.data.id)<0) && cur.left!= null)
			cur=search(cur.left, node);
		return cur;
	}
    
	/**
	 * Function that recursively display the data. 
	 * @param cur - current node
	 * @param out - the output stream to print the node information
	 * @throws IOException
	 */
	public void print_tree(Node cur, PrintWriter out) throws IOException {
		if (cur.left!=null)
			print_tree(cur.left,out);
		String s = cur.data.id+"        "+cur.data.faculty+"     "+
                                 cur.data.major+ "       "+cur.data.year + "\n";
		out.println(s);
		System.out.println(s);
		if (cur.right!=null)
			print_tree(cur.right,out);
	} 
}