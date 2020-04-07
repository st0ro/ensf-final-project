package studentrecordmanager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.SwingConstants;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

/**
 * A window for inserting a student to the list
 * @author Tony F, Alex P, James Z
 *
 */
public class InsertWindow extends JFrame{
	
	private static final long serialVersionUID = 2211164234179019854L;
	private JButton insertBtn, returnBtn;
	private JTextField idField;
	private JTextField facultyField;
	private JTextField majorField;
	private JTextField yearField;
	
	/**
	 * Creates and displays the window for inserting a student
	 * @param parent JFrame opening the insert window
	 */
	public InsertWindow(JFrame parent) {
		super("Insert New Student");
		setSize(450, 150);
		setLocationRelativeTo(parent);
		Container mainPane = getContentPane();
		
		JLabel headLabel = new JLabel("Insert a new student to the tree");
		headLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mainPane.add(headLabel, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel();
		mainPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel idLabel = new JLabel("Student ID:");
		centerPanel.add(idLabel);
		
		idField = new JTextField();
		centerPanel.add(idField);
		idField.setColumns(10);
		
		JLabel facultyLabel = new JLabel("Student Faculty");
		centerPanel.add(facultyLabel);
		
		facultyField = new JTextField();
		centerPanel.add(facultyField);
		facultyField.setColumns(10);
		
		JLabel majorLabel = new JLabel("Student Major");
		centerPanel.add(majorLabel);
		
		majorField = new JTextField();
		centerPanel.add(majorField);
		majorField.setColumns(10);
		
		JLabel yearLabel = new JLabel("Student Year");
		centerPanel.add(yearLabel);
		
		yearField = new JTextField();
		centerPanel.add(yearField);
		yearField.setColumns(10);
		
		JPanel btnPanel = new JPanel();
		mainPane.add(btnPanel, BorderLayout.SOUTH);
		
		insertBtn = new JButton("Insert");
		btnPanel.add(insertBtn);
		
		returnBtn = new JButton("Return to main window");
		btnPanel.add(returnBtn);
		
		setVisible(true);
	}
	
	/**
	 * Adds listeners to the input fields and buttons of the window
	 * @param parent main function requesting the window
	 */
	public void addListeners(StudentRecordManager parent) {
		insertBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(idField.getText().isEmpty() || facultyField.getText().isEmpty() ||
						majorField.getText().isEmpty() || yearField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please ensure all fields are filled.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					parent.insertInput(idField.getText(), facultyField.getText(), majorField.getText(), yearField.getText());
					setVisible(false);
				}
			}
		});
		returnBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
	}

}
