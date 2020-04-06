package studentrecordmanager;

import java.io.IOException;
import java.io.PrintWriter;

public class Test {

	public static void main(String[] args) {
		BinSearchTree bst = new BinSearchTree("input.txt");
		PrintWriter pr = new PrintWriter(System.out);
		try {
			bst.print_tree(bst.root, pr);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
