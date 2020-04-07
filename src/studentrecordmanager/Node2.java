package studentrecordmanager;

/**
 * A node with data and two references to the next-left and next-right node
 */
class Node {
	
	/**
	 * Contains the information held in the node
	 */
	Data data;
	/**
	 * References to the left and right branches of the tree
	 */
	Node left, right;
	/**
	 * 
	 * @param id student id
	 * @param faculty faculty code
	 * @param major student's major
	 * @param year student's year of study
	 */
	public Node(String id, String faculty, String major, String year) {
		// creating a data item
		data = new Data(id, faculty, major, year);
		left = null;
        right = null;
	}

	@Override
	public String toString() {
		return data.toString();
	}
}

