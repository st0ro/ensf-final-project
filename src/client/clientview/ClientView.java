// Written by A. Price
package client.clientview;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import client.clientcontroller.ClientController;

public class ClientView extends JFrame{
	
	private static final long serialVersionUID = 998423311465136417L;
	
	private JTabbedPane tabbedPane;
	private JPanel allPane, allFootPane, enrolledPane, enrolledFootPane, adminFootPane;
	private JTable allTable, enrolledTable;
	private JScrollPane allScrollPane, enrolledScrollPane;
	private JButton allSearchBtn, allEnrollBtn, allRefreshBtn, enrolledRemoveBtn, enrolledRefreshBtn;
	private JButton adminAddCourseBtn, adminAddOfferingBtn;
	
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
		allRefreshBtn = new JButton("Refresh");
		allFootPane = new JPanel();
		allFootPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		allFootPane.add(allRefreshBtn);
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
		enrolledRefreshBtn = new JButton("Refresh");
		enrolledFootPane = new JPanel();
		enrolledFootPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		enrolledFootPane.add(enrolledRefreshBtn);
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
	}
	
	public void setListeners(ClientController controller) {
		allSearchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					String search = JOptionPane.showInputDialog("Please enter the course to search for:").toUpperCase();
					if(search == null) {
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
				int row = allTable.getSelectedRow();
				if(row >= 0) {
					int section = Integer.parseInt((String) allTable.getValueAt(row, 1));
					String name = (String) allTable.getValueAt(row - section + 1, 0);
					int confirm = JOptionPane.showConfirmDialog(ClientView.this, "Do you wish to enroll in " + name + "?", "Enroll", JOptionPane.OK_CANCEL_OPTION);
					if(confirm == JOptionPane.OK_OPTION) {
						String result = controller.attemptEnroll(name, section);
						if(result == null) {
							controller.retrieveCourses(0);
							controller.retrieveCourses(1);
						} else {
							JOptionPane.showMessageDialog(ClientView.this, result, "Enroll", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(ClientView.this, "Please select a class to enroll.", "Enroll", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		allRefreshBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.retrieveCourses(0);
				controller.retrieveCourses(1);
			}
		});
		enrolledRemoveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int row = enrolledTable.getSelectedRow();
				if(row >= 0) {
					int section = Integer.parseInt((String) enrolledTable.getValueAt(row, 1));
					String name = (String) enrolledTable.getValueAt(row, 0);
					int confirm = JOptionPane.showConfirmDialog(ClientView.this, "Do you wish to remove " + name + "?", "Remove Course", JOptionPane.OK_CANCEL_OPTION);
					if(confirm == JOptionPane.OK_OPTION) {
						String result = controller.attemptUnenroll(name, section);
						if(result == null) {
							controller.retrieveCourses(0);
							controller.retrieveCourses(1);
						} else {
							JOptionPane.showMessageDialog(ClientView.this, result, "Remove Course", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(ClientView.this, "Please select a class to remove.", "Remove Course", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		enrolledRefreshBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.retrieveCourses(0);
				controller.retrieveCourses(1);
			}
		});
		addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent we) {
	        	 controller.quit();
	        	 System.exit(0);
	         }
	      });
	}
	
	public void fillTable(String[][] list, int set) {
		switch(set) {
		case 0:
			allTable.setModel(makeTable(list));
			break;
		case 1:
			enrolledTable.setModel(makeTable(list));
			break;
		}
		tabbedPane.repaint();
	}
	
	private DefaultTableModel makeTable(String[][] list) {
		String[] headers = {"Course", "Section", "Capacity"};
		return new DefaultTableModel(list, headers);
	}
	
	public void setAdmin(ClientController controller) {
		tabbedPane.remove(enrolledPane);
		allPane.remove(allFootPane);
		adminAddCourseBtn = new JButton("Add course");
		adminAddCourseBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String course = JOptionPane.showInputDialog(ClientView.this, "Please enter the course name and number:",
						"Add Course", JOptionPane.PLAIN_MESSAGE).toUpperCase();
				if(course != null) {
					String seats = JOptionPane.showInputDialog(ClientView.this, "Please enter the number of seats in the first offering:",
							"Add Course", JOptionPane.PLAIN_MESSAGE);
				 	if(seats == null) {
				 		String response = controller.attemptAdminAddOperation(course, seats);
				 		if(response == null) {
					 		controller.retrieveCourses(0);
				 		}
				 		else {
					 		JOptionPane.showMessageDialog(ClientView.this, response, "Add Course", JOptionPane.ERROR_MESSAGE);
				 		}
				 	}
				 }
			}
		});
		adminAddOfferingBtn = new JButton("Add offering");
		adminAddOfferingBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int row = allTable.getSelectedRow();
				if(row >= 0) {
					int section = Integer.parseInt((String) allTable.getValueAt(row, 1));
					String course = (String) allTable.getValueAt(row - section + 1, 0);
					String seats = JOptionPane.showInputDialog(ClientView.this, "Please enter the number of seats in the new offering:",
							 "Add Offering", JOptionPane.PLAIN_MESSAGE);
					if(seats != null) {
						String result = controller.attemptAdminAddOperation(course, seats);
						if(result == null) {
							controller.retrieveCourses(0);
						} else {
							JOptionPane.showMessageDialog(ClientView.this, result, "Add Offering", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(ClientView.this, "Please select a class to add an offering to.", "Add Offering", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		adminFootPane = new JPanel();
		adminFootPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		adminFootPane.add(adminAddCourseBtn);
		adminFootPane.add(adminAddOfferingBtn);
		allPane.add(adminFootPane, BorderLayout.SOUTH);
		tabbedPane.repaint();
	}

}
