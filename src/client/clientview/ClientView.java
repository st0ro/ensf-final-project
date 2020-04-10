package client.clientview;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.clientcontroller.ClientController;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.ListSelectionModel;

public class ClientView extends JFrame{
	
	private static final long serialVersionUID = 998423311465136417L;
	private ClientController controller;
	
	private JTabbedPane tabbedPane;
	private JPanel allPane, allFootPane, enrolledPane, enrolledFootPane;
	private JTable allTable, enrolledTable;
	private JScrollPane allScrollPane, enrolledScrollPane;
	private JButton allSearchBtn, allEnrollButton, enrolledRemoveBtn;
	
	public ClientView() {
		super("Course Registration System");
		
		allTable = new JTable() {
			private static final long serialVersionUID = 1L;
			
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		allTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		allScrollPane = new JScrollPane(allTable);
		allSearchBtn = new JButton("Search");
		allEnrollButton = new JButton("Enroll");
		allFootPane = new JPanel();
		((FlowLayout) allFootPane.getLayout()).setAlignment(FlowLayout.RIGHT);
		allFootPane.add(allSearchBtn);
		allFootPane.add(allEnrollButton);
		allPane = new JPanel();
		allPane.setLayout(new BorderLayout());
		allPane.add(allFootPane, BorderLayout.SOUTH);
		allPane.add(allScrollPane, BorderLayout.CENTER);
		
		enrolledTable = new JTable() {
			private static final long serialVersionUID = 1L;
			
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		enrolledTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		enrolledScrollPane = new JScrollPane(enrolledTable);
		enrolledRemoveBtn = new JButton("Remove course");
		enrolledFootPane = new JPanel();
		((FlowLayout) enrolledFootPane.getLayout()).setAlignment(FlowLayout.RIGHT);
		enrolledFootPane.add(enrolledRemoveBtn);
		enrolledPane = new JPanel();
		enrolledPane.setLayout(new BorderLayout());
		enrolledPane.add(enrolledFootPane, BorderLayout.SOUTH);
		enrolledPane.add(enrolledScrollPane, BorderLayout.CENTER);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("All courses", allPane);
		tabbedPane.addTab("Currently Enrolled Courses", enrolledPane);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		setSize(600, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setController(ClientController controller) {
		this.controller = controller;
	}

}
