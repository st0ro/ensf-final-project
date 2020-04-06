package studentrecordmanager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.Font;

public class StudentRecordManager {
	
	private JFrame frame;
	private JPanel mainPanel, footPanel;
	private JScrollPane listPanel;
	private JLabel headLabel;
	private JButton insertBtn, findBtn, browseBtn, treeBtn;
	
	private BinSearchTree tree;
	private JTextPane mainTextPane;

	public StudentRecordManager() {
		tree = null;
		
		headLabel = new JLabel("Student Record Manager Application");
		headLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		listPanel = new JScrollPane();
		
		insertBtn = new JButton("Insert");
		findBtn = new JButton("Find");
		browseBtn = new JButton("Browse");
		treeBtn = new JButton("Create Tree from File");
		footPanel = new JPanel();
		footPanel.add(insertBtn);
		footPanel.add(findBtn);
		footPanel.add(browseBtn);
		footPanel.add(treeBtn);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(10, 10));
		mainPanel.add(headLabel, BorderLayout.PAGE_START);
		mainPanel.add(listPanel, BorderLayout.CENTER);
		
		mainTextPane = new JTextPane();
		mainTextPane.setFont(new Font("Consolas", Font.PLAIN, 14));
		mainTextPane.setEditable(false);
		listPanel.setViewportView(mainTextPane);
		mainPanel.add(footPanel, BorderLayout.PAGE_END);
		
		frame = new JFrame("Student Record Manager");
		frame.setContentPane(mainPanel);
		frame.setSize(500, 350);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addListeners() {
		insertBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tree == null) {
					tree = new BinSearchTree();
				}
				InsertWindow insertWindow= new InsertWindow(frame);
				insertWindow.addListeners(StudentRecordManager.this);
			}
		});
		findBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tree == null) {
					JOptionPane.showMessageDialog(null, "Please open a file first!", "Find Student", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				String id = JOptionPane.showInputDialog("Please enter the student ID to search for:");
				Node student = tree.find(tree.root, id);
				String result;
				if(student != null) {
					result = student.data.toString();
				}
				else {
					result = "No student found with matching ID!";
				}
				JOptionPane.showMessageDialog(null, result, "Find Student", JOptionPane.PLAIN_MESSAGE);
			}
		});
		browseBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tree != null) {
					refreshList();
				}
				else {
					JOptionPane.showMessageDialog(null, "No file loaded.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		treeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String filePath = JOptionPane.showInputDialog("Enter the file name:");
				try {
					tree = new BinSearchTree(filePath);
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "File not found.", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NullPointerException e2) {
					System.out.println("No file specified...");
				}
			}
		});
	}
	
	public void insertInput(String id, String faculty, String major, String year) {
		tree.insert(id, faculty, major, year);
		refreshList();
	}
	
	public void refreshList() {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(byteOut);
		try {
			tree.print_tree(tree.root, writer);
			writer.flush();
			Scanner scan = new Scanner(byteOut.toString());
			StringBuilder builder = new StringBuilder();
			while(scan.hasNextLine()) {
				builder.append(String.format("%10s %8s %10s %5s\n", scan.next(), scan.next(), scan.next(), scan.nextLine()));
			}
			mainTextPane.setText(builder.toString());
			scan.close();
		} catch (IOException e1) {
			System.out.println("I/O error occured!");
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		StudentRecordManager srm = new StudentRecordManager();
		srm.addListeners();
	}
}
