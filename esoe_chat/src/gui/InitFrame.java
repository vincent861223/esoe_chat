package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import client.ChatController;
import container.Response;

public class InitFrame extends JFrame {

	private static InitFrame frame;
	private JPanel contentPane;
	private JPanel loginPanel = new JPanel();
	private JPanel registerPanel = new JPanel();

	// Login Panel Components
	private JTextField inputUsername;
	private JPasswordField inputPassword;
	private JLabel lblLoginMsg;
	private JLabel lblUsername = new JLabel("Username");
	private JLabel lblPassword = new JLabel("Password");
	private JButton btnGotoSignUp = new JButton("Sign Up");
	private JButton btnLogin = new JButton("Login");
	private JRadioButton rdbtnRememberMe = new JRadioButton("Remember Me");

	// Register Panel Components
	private JLabel lblRegUsername;
	private JLabel lblRegEmail;
	private JLabel lblRegPassword;
	private JLabel lblRegPasswordCheck;
	private JTextField regUsername;
	private JTextField regEmail;
	private JPasswordField regPassword;
	private JPasswordField regPasswordCheck;
	private JLabel lblArrowBack = new JLabel("Back");
	private JLabel lblSignUpMsg;



	ChatController chatController = new ChatController("127.0.0.1", 12345);
	Response response;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new InitFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InitFrame() {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(6, 6, 288, 426);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		layeredPane.add(loginPanel, "LoginPanel");
		loginPanel.setLayout(null);
		setLoginPanel();

		btnGotoSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(registerPanel);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});

		layeredPane.add(registerPanel, "registerPanel");
		registerPanel.setLayout(null);
		setRegisterPanel();
		
		lblArrowBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				layeredPane.removeAll();
				layeredPane.add(loginPanel);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblArrowBack.setForeground(Color.DARK_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblArrowBack.setForeground(Color.GRAY);
			}
		});
	}


	private void setLoginPanel() {
		lblUsername.setBounds(34, 156, 80, 16);
		lblUsername.setForeground(Color.LIGHT_GRAY);
		loginPanel.add(lblUsername);

		lblPassword.setBounds(34, 194, 61, 16);
		lblPassword.setForeground(Color.LIGHT_GRAY);
		loginPanel.add(lblPassword);

		inputUsername = new JTextField();
		inputUsername.setBounds(24, 146, 239, 36);
		loginPanel.add(inputUsername);
		setInternalPadding(inputUsername);

		inputPassword = new JPasswordField();
		inputPassword.setBounds(24, 184, 239, 36);
		loginPanel.add(inputPassword);
		setInternalPadding(inputPassword);

		btnGotoSignUp.setBounds(27, 368, 239, 29);
		loginPanel.add(btnGotoSignUp);	

		btnLogin.setBounds(159, 223, 107, 29);
		loginPanel.add(btnLogin);

		rdbtnRememberMe.setBounds(24, 224, 141, 23);
		loginPanel.add(rdbtnRememberMe);

		lblLoginMsg = new JLabel("Login Message");
		lblLoginMsg.setBounds(25, 275, 238, 40);
		lblLoginMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginMsg.setVisible(false);
		loginPanel.add(lblLoginMsg);
		
		setPlaceholder(inputPassword, lblPassword);
		setPlaceholder(inputUsername, lblUsername);

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String account;
				String password;
				try {
					account = inputUsername.getText();
					password = String.valueOf(inputPassword.getPassword());
					response = chatController.login(account, password);
					if (response.getStatus().equals("Failed")) {
						throw new GuiException(response.getMsg());
					}
					lblLoginMsg.setVisible(true);
					lblLoginMsg.setText("Login Success");
					frame.dispose();
					MainFrame frmMain = new MainFrame();
					frmMain.setVisible(true);

				} catch (GuiException e1) {
					lblLoginMsg.setVisible(true);
					lblLoginMsg.setText(e1.getErrMessage());
				}
			}
		});
	}

	private void setRegisterPanel() {
		lblArrowBack.setForeground(Color.GRAY);
		lblArrowBack.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		
		lblArrowBack.setBounds(0, 0, 34, 25);
		registerPanel.add(lblArrowBack);

		lblRegUsername = new JLabel("Username");
		lblRegUsername.setForeground(Color.LIGHT_GRAY);
		lblRegUsername.setBounds(34, 92, 78, 16);
		registerPanel.add(lblRegUsername);

		lblRegEmail = new JLabel("E-mail");
		lblRegEmail.setForeground(Color.LIGHT_GRAY);
		lblRegEmail.setBounds(36, 128, 61, 16);
		registerPanel.add(lblRegEmail);

		lblRegPassword = new JLabel("Password");
		lblRegPassword.setForeground(Color.LIGHT_GRAY);
		lblRegPassword.setBounds(34, 165, 61, 16);
		registerPanel.add(lblRegPassword);

		lblRegPasswordCheck = new JLabel("Enter Password Again");
		lblRegPasswordCheck.setForeground(Color.LIGHT_GRAY);
		lblRegPasswordCheck.setBounds(34, 202, 140, 16);
		registerPanel.add(lblRegPasswordCheck);

		regUsername = new JTextField();
		regUsername.setBounds(24, 82, 239, 36);
		registerPanel.add(regUsername);
		setInternalPadding(regUsername);

		regEmail = new JTextField();
		regEmail.setBounds(24, 118, 239, 36);
		registerPanel.add(regEmail);
		setInternalPadding(regEmail);

		regPassword = new JPasswordField();
		regPassword.setBounds(24, 155, 239, 36);
		registerPanel.add(regPassword);
		setInternalPadding(regPassword);

		regPasswordCheck = new JPasswordField();
		regPasswordCheck.setBounds(24, 192, 239, 36);
		registerPanel.add(regPasswordCheck);
		setInternalPadding(regPasswordCheck);

		setPlaceholder(regUsername, lblRegUsername);
		setPlaceholder(regEmail, lblRegEmail);
		setPlaceholder(regPassword, lblRegPassword);
		setPlaceholder(regPasswordCheck, lblRegPasswordCheck);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String account;
				String email;
				String password;
				try {
					account = regUsername.getText();
					email = regEmail.getText();
					password = String.valueOf(regPassword.getPassword());
					response = chatController.register(account, email, password);
					if (response.getStatus().equals("Failed")) {
						throw new GuiException(response.getMsg());
					}
					lblSignUpMsg.setVisible(true);
					lblSignUpMsg.setText("Sign up Successfully");

				} catch (GuiException e1) {
					lblSignUpMsg.setVisible(true);
					lblSignUpMsg.setText(e1.getErrMessage());
				}
			}
		});
		btnSignUp.setBounds(24, 230, 239, 29);
		registerPanel.add(btnSignUp);
		
		lblSignUpMsg = new JLabel("SignUp Message");
		lblSignUpMsg.setBounds(25, 275, 238, 40);
		lblSignUpMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignUpMsg.setVisible(false);
		registerPanel.add(lblSignUpMsg);
	}
	
	/**
	 * Sets internal padding of JTextField
	 * @param tf field that needs internal padding
	 */
	private void setInternalPadding(JTextField tf) {
		tf.setBorder(BorderFactory.createCompoundBorder(
				tf.getBorder(), 
				BorderFactory.createEmptyBorder(0, 5, 0, 0)));
	}
	/**
	 * Sets internal padding of JPasswordfield
	 * @param pf field that needs internal padding
	 */
	private void setInternalPadding(JPasswordField pf) {
		pf.setBorder(BorderFactory.createCompoundBorder(
				pf.getBorder(), 
				BorderFactory.createEmptyBorder(0, 5, 0, 0)));
	}
	
	/**
	 * Dynamically displays placeholder on JPasswordField
	 * @param pf Password Field that shows a placeholder
	 * @param placeholder Label that want to be used as a placeholder
	 */
	private void setPlaceholder(JPasswordField pf, JLabel placeholder) {
		pf.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) { changed(); }
			@Override
			public void removeUpdate(DocumentEvent e) { changed(); }
			@Override
			public void insertUpdate(DocumentEvent e) { changed(); }
			public void changed() {
				placeholder.setVisible(pf.getPassword().length == 0);
			}
		});
	}
	/**
	 * Dynamically displays placeholder on JTextField
	 * @param tf Text Field that shows a placeholder
	 * @param placeholder Label that want to be used as a placeholder
	 */
	private void setPlaceholder(JTextField tf, JLabel placeholder) {
		tf.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) { changed(); }
			@Override
			public void removeUpdate(DocumentEvent e) { changed(); }
			@Override
			public void insertUpdate(DocumentEvent e) { changed(); }
			public void changed() {
				placeholder.setVisible(tf.getText().isEmpty());
			}
		});
	}
}
