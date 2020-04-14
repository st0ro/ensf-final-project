// Written by A. Price
package client.clientview;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.clientcontroller.ClientController;

public class LogInView extends JFrame {

	private static final long serialVersionUID = 666316270725557772L;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton logInBtn;

	public LogInView() {
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		
		JLabel headLbl = new JLabel("Course Registration System Log In");
		headLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		pane.add(headLbl);
		
		JLabel usernameLbl = new JLabel("Username:");
		pane.add(usernameLbl);
		
		usernameField = new JTextField();
		usernameField.setColumns(15);
		pane.add(usernameField);
		
		JLabel passwordLbl = new JLabel("Password:");
		pane.add(passwordLbl);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(15);
		pane.add(passwordField);
		
		logInBtn = new JButton("Log in");
		pane.add(logInBtn);
		
		setSize(300, 180);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setListeners(ClientController controller) {
		logInBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String user = usernameField.getText(), pass = new String(passwordField.getPassword());
				if(!user.isEmpty() && !pass.isEmpty()) {
					if(controller.attemptLogIn(user, pass)) {
						dispose();
					} else {
						JOptionPane.showMessageDialog(LogInView.this, "Invalid username or password!", "Log In", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(LogInView.this, "Please enter a username and password!", "Log In", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
