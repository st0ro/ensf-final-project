package client.clientview;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.clientcontroller.ClientController;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ClientView extends JFrame{
	
	private static final long serialVersionUID = 998423311465136417L;
	
	private JTabbedPane tabbedPane;
	private JPanel allPane, allFootPane, enrolledPane, enrolledFootPane;
	private JTable allTable, enrolledTable;
	private JScrollPane allScrollPane, enrolledScrollPane;
	private JButton allSearchBtn, allEnrollBtn, enrolledRemoveBtn;
	
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
		allEnrollBtn = new JButton("Enroll");
		allFootPane = new JPanel();
		((FlowLayout) allFootPane.getLayout()).setAlignment(FlowLayout.RIGHT);
		allFootPane.add(allSearchBtn);
		allFootPane.add(allEnrollBtn);
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
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setListeners(ClientController controller) {
		allSearchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					String search = JOptionPane.showInputDialog("Please enter the course to search for:").toUpperCase();
					if(search.isEmpty()) {
						return;
					}
					for(int i = 0; i < allTable.getRowCount(); i++) {
						if(search.equals(allTable.getValueAt(i, 0))) {
							allTable.changeSelection(i, 0, false, false);
							return;
						}
					}
					JOptionPane.showMessageDialog(ClientView.this, "No course found with that name.", "Search Result", JOptionPane.ERROR_MESSAGE);
				} catch (NullPointerException e){
					return;
				}
			}
		});
		allEnrollBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(allTable.getSelectedRow() >= 0) {
					
				} else {
					JOptionPane.showMessageDialog(ClientView.this, "Please select a class to enroll.", "Enroll", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	public void fillTables(String[][] all, String[][] enrolled) {
		allTable.setModel(makeTable(all));
		enrolledTable.setModel(makeTable(enrolled));
		tabbedPane.repaint();
	}
	
	private DefaultTableModel makeTable(String[][] list) {
		String[] headers = {"Course", "Section", "Capacity"};
		return new DefaultTableModel(list, headers);
	}
	
	public void setAdmin() {
		// TODO enable admin mode
	}

}
