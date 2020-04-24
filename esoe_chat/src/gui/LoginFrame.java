package gui;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

import client.ChatController;
import container.Response;

public class LoginFrame {

	private JFrame frmLogin;
	private JTextField inputUsername;
	private JPasswordField inputPassword;
	private JLabel lblLoginErr;

	ChatController chatController = new ChatController("127.0.0.1", 12345);
	Response response;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LoginFrame window = new LoginFrame();
//					window.frmEsoeChat.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//
//				}
//			}
//		});
//	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame window = new LoginFrame();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();

				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setForeground(Color.BLACK);
		frmLogin.setTitle("ESOE CHAT");
		frmLogin.setBounds(100, 100, 300, 480);
		frmLogin.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);

		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(150, 198, 117, 29);
		frmLogin.getContentPane().add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String account;
				String password;
				try {
					account = inputUsername.getText();
					password = String.valueOf(inputPassword.getPassword());
					response = chatController.login(account, password);
					if (response.getStatus().equals("Failed")) {
						throw new LoginException(response.getMsg());
					}
					lblLoginErr.setVisible(true);
					lblLoginErr.setText("Login Success");
					frmLogin.dispose();
					MainFrame frmMain = new MainFrame();
					frmMain.setVisible(true);
					
				} catch (LoginException e1) {
					lblLoginErr.setVisible(true);
					lblLoginErr.setText(e1.getErrMessage());
				}
			}
		});
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.LIGHT_GRAY);
		lblUsername.setBounds(39, 132, 74, 16);
		frmLogin.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.LIGHT_GRAY);
		lblPassword.setBounds(39, 171, 61, 16);
		frmLogin.getContentPane().add(lblPassword);

		inputUsername = new JTextField();
		inputUsername.setBounds(29, 120, 238, 40);
		inputUsername.setColumns(10);
		inputUsername.setBorder(BorderFactory.createCompoundBorder(
								inputUsername.getBorder(), 
								BorderFactory.createEmptyBorder(0, 5, 0, 0)));
		frmLogin.getContentPane().add(inputUsername);
		inputUsername.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) { changed(); }
			@Override
			public void removeUpdate(DocumentEvent e) { changed(); }
			@Override
			public void insertUpdate(DocumentEvent e) { changed(); }
			public void changed() {
				lblUsername.setVisible(inputUsername.getText().isEmpty());
			}
		});

		inputPassword = new JPasswordField();
		inputPassword.setBounds(29, 159, 238, 40);
		inputPassword.setColumns(10);
		inputPassword.setBorder(BorderFactory.createCompoundBorder(
								inputPassword.getBorder(), 
								BorderFactory.createEmptyBorder(0, 5, 0, 0)));
		frmLogin.getContentPane().add(inputPassword);
		inputPassword.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) { changed(); }
			@Override
			public void removeUpdate(DocumentEvent e) { changed(); }
			@Override
			public void insertUpdate(DocumentEvent e) { changed(); }
			public void changed() {
				lblPassword.setVisible(inputPassword.getPassword().length == 0);
		
			}
		});

		
		lblLoginErr = new JLabel("Login Message");
		lblLoginErr.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginErr.setVisible(false);
		lblLoginErr.setBounds(29, 229, 238, 40);
		frmLogin.getContentPane().add(lblLoginErr);


		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		btnSignUp.setBounds(29, 388, 238, 29);
		frmLogin.getContentPane().add(btnSignUp);

		JRadioButton rdbtnRememberMe = new JRadioButton("Remember Me");
		rdbtnRememberMe.setBounds(29, 199, 141, 23);
		frmLogin.getContentPane().add(rdbtnRememberMe);

	}
}
