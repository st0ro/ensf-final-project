
/**
 * A node with data and two references to the next-left and next-right node
 */
class Node {
	
	Data data;
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
	
	public String toString() {
		return data.toString();
	}
}

