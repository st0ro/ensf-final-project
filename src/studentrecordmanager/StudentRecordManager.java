package studentrecordmanager;

import java.awt.*;

import javax.swing.*;

public class StudentRecordManager {
	
	private JFrame frame;
	private JPanel mainPanel, listPanel, footPanel;
	private JLabel headLabel;
	private JButton insertBtn, findBtn, browseBtn, treeBtn;
	
	private BinSearchTree tree;

	public StudentRecordManager() {
		tree = null;
		
		headLabel = new JLabel("Student Record Manager Application");
		headLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		listPanel = new JPanel();
		listPanel.setBackground(Color.WHITE);
		listPanel.setLayout(new GridLayout(0, 4));
		
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
		mainPanel.add(footPanel, BorderLayout.PAGE_END);
		
		frame = new JFrame("Student Record Manager");
		frame.setContentPane(mainPanel);
		frame.setSize(700, 450);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		StudentRecordManager srm = new StudentRecordManager();
	}
}
