package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.ChatController;
import container.Response;

public class InitFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static InitFrame frame;
	private JPanel contentPane;
	private JPanel loginPanel = new JPanel();
	private JPanel registerPanel = new JPanel();
	private static final String SAVED_USERNAME = "savedUsername";
	private static final String SAVED_PASSWORD = "savedPassword";

	// Login Panel Components
	private static JTextField inputUsername = new JTextField();
	private static JPasswordField inputPassword = new JPasswordField();
	private static JRadioButton rdbtnRememberMe = new JRadioButton("Remember Me");
	private static JButton btnGotoSignUp = new JButton("Sign Up");
	private static JButton btnLogin = new JButton("Login");

	// Register Panel Components
	private static JTextField regUsername = new JTextField();
	private static JTextField regEmail = new JTextField();
	private static JPasswordField regPassword = new JPasswordField();
	private static JPasswordField regPasswordCheck = new JPasswordField();
	private static JLabel lblArrowBack = new JLabel("Back");
	private static JButton btnSignUp = new JButton("Sign Up");
	private static JLabel iconRegEmailOk = new JLabel("");
	private static JLabel iconRegEmailWrong = new JLabel("");
	private static JLabel iconRegPasswordOk = new JLabel("");
	private static JLabel iconRegPasswordWrong = new JLabel("");

	// Controller
	transient ChatController chatController = new ChatController("127.0.0.1", 12345);
	Response response;

	// Preferences
	transient Preferences userPreferences = Preferences.userRoot();

	//
	private static String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
	private static Pattern pattern = Pattern.compile(regex);
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				frame = new InitFrame();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				frame.setResizable(false);
			} catch (Exception e) {
				e.printStackTrace();
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
		btnGotoSignUp.addActionListener(e -> {
			layeredPane.removeAll();
			layeredPane.add(registerPanel);
			layeredPane.repaint();
			layeredPane.revalidate();
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
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(34, 156, 80, 16);
		lblUsername.setForeground(Color.LIGHT_GRAY);
		loginPanel.add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(34, 194, 61, 16);
		lblPassword.setForeground(Color.LIGHT_GRAY);
		loginPanel.add(lblPassword);

		inputUsername.setBounds(24, 146, 239, 36);
		loginPanel.add(inputUsername);
		setInternalPadding(inputUsername);
		inputUsername.setDocument(new JTextFieldLimit(25));

		inputPassword.setBounds(24, 184, 239, 36);
		loginPanel.add(inputPassword);
		setInternalPadding(inputPassword);
		inputPassword.setDocument(new JTextFieldLimit(25));

		btnGotoSignUp.setBounds(27, 368, 239, 29);
		loginPanel.add(btnGotoSignUp);

		btnLogin.setBounds(159, 223, 107, 29);
		loginPanel.add(btnLogin);
		btnLogin.setEnabled(false);

		rdbtnRememberMe.setBounds(24, 224, 141, 23);
		loginPanel.add(rdbtnRememberMe);

		JLabel lblLoginMsg = new JLabel("Login Message");
		lblLoginMsg.setBounds(25, 258, 238, 40);
		lblLoginMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginMsg.setVisible(false);
		loginPanel.add(lblLoginMsg);

		documentListener(inputUsername, ListenerActions::toggleLoginBtn);
		documentListener(inputPassword, ListenerActions::toggleLoginBtn);
		diplaySavedInfo();
		setPlaceholder(inputPassword, lblPassword);
		setPlaceholder(inputUsername, lblUsername);

		btnLogin.addActionListener(e -> {
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

				if (rdbtnRememberMe.isSelected()) {
					userPreferences.put(SAVED_USERNAME, account);
					userPreferences.put(SAVED_PASSWORD, password);
				} else {
					userPreferences.put(SAVED_USERNAME, "");
					userPreferences.put(SAVED_PASSWORD, "");
				}

				frame.dispose();
				MainFrame frmMain = new MainFrame();
				frmMain.setVisible(true);

			} catch (GuiException e1) {
				lblLoginMsg.setVisible(true);
				lblLoginMsg.setText(e1.getErrMessage());
			}
		});
	}

	private void setRegisterPanel() {
		lblArrowBack.setForeground(Color.GRAY);
		lblArrowBack.setFont(new Font("Lucida Grande", Font.PLAIN, 15));

		lblArrowBack.setBounds(0, 0, 34, 25);
		registerPanel.add(lblArrowBack);

		JLabel lblRegUsername = new JLabel("Username");
		lblRegUsername.setForeground(Color.LIGHT_GRAY);
		lblRegUsername.setBounds(34, 92, 78, 16);
		registerPanel.add(lblRegUsername);

		JLabel lblRegEmail = new JLabel("E-mail");
		lblRegEmail.setForeground(Color.LIGHT_GRAY);
		lblRegEmail.setBounds(36, 128, 61, 16);
		registerPanel.add(lblRegEmail);

		JLabel lblRegPassword = new JLabel("Password");
		lblRegPassword.setForeground(Color.LIGHT_GRAY);
		lblRegPassword.setBounds(34, 165, 61, 16);
		registerPanel.add(lblRegPassword);

		JLabel lblRegPasswordCheck = new JLabel("Enter Password Again");
		lblRegPasswordCheck.setForeground(Color.LIGHT_GRAY);
		lblRegPasswordCheck.setBounds(34, 202, 140, 16);
		registerPanel.add(lblRegPasswordCheck);
		
		
		Image img = new ImageIcon(this.getClass().getResource("/ok.png")).getImage();
		iconRegEmailOk.setIcon(new ImageIcon(img));
		iconRegEmailOk.setBounds(236, 128, 16, 16);
		registerPanel.add(iconRegEmailOk);
		iconRegEmailOk.setVisible(false);
				
		img = new ImageIcon(this.getClass().getResource("/wrong.png")).getImage();
		iconRegEmailWrong.setIcon(new ImageIcon(img));
		iconRegEmailWrong.setBounds(236, 128, 16, 16);
		registerPanel.add(iconRegEmailWrong);
		iconRegEmailWrong.setVisible(false);
		
		img = new ImageIcon(this.getClass().getResource("/ok.png")).getImage();
		iconRegPasswordOk.setIcon(new ImageIcon(img));
		iconRegPasswordOk.setBounds(236, 202, 16, 16);
		registerPanel.add(iconRegPasswordOk);
		iconRegPasswordOk.setVisible(false);
		
		img = new ImageIcon(this.getClass().getResource("/wrong.png")).getImage();
		iconRegPasswordWrong.setIcon(new ImageIcon(img));
		iconRegPasswordWrong.setBounds(236, 202, 16, 16);
		registerPanel.add(iconRegPasswordWrong);
		iconRegPasswordWrong.setVisible(false);

		regUsername.setBounds(24, 82, 239, 36);
		registerPanel.add(regUsername);
		setInternalPadding(regUsername);
		regUsername.setDocument(new JTextFieldLimit(25));

		regEmail.setBounds(24, 118, 239, 36);
		registerPanel.add(regEmail);
		setInternalPadding(regEmail);
		regEmail.setDocument(new JTextFieldLimit(25));

		regPassword.setBounds(24, 155, 239, 36);
		registerPanel.add(regPassword);
		setInternalPadding(regPassword);
		regPassword.setDocument(new JTextFieldLimit(25));

		regPasswordCheck.setBounds(24, 192, 239, 36);
		registerPanel.add(regPasswordCheck);
		setInternalPadding(regPasswordCheck);
		regPassword.setDocument(new JTextFieldLimit(25));

		btnSignUp.setEnabled(false);
		documentListener(regUsername, ListenerActions::toggleSignUpBtn);
		documentListener(regEmail, ListenerActions::toggleSignUpBtn);
		documentListener(regPassword, ListenerActions::toggleSignUpBtn);
		documentListener(regPasswordCheck, ListenerActions::toggleSignUpBtn);
		documentListener(regEmail, ListenerActions::toggleEmailIcon);
		documentListener(regPasswordCheck, ListenerActions::togglePasswordIcon);
		documentListener(regPassword, ListenerActions::togglePasswordIcon);

		setPlaceholder(regUsername, lblRegUsername);
		setPlaceholder(regEmail, lblRegEmail);
		setPlaceholder(regPassword, lblRegPassword);
		setPlaceholder(regPasswordCheck, lblRegPasswordCheck);

		btnSignUp.setBounds(157, 229, 106, 29);
		registerPanel.add(btnSignUp);

		JRadioButton rdbtnShowPassword = new JRadioButton("Show Password");
		rdbtnShowPassword.addActionListener(e -> {
			if (rdbtnShowPassword.isSelected()) {
				regPassword.setEchoChar('\0');
				regPasswordCheck.setEchoChar('\0');
			} else {
				regPassword.setEchoChar('●');
				regPasswordCheck.setEchoChar('●');
			}
		});
		rdbtnShowPassword.setBounds(24, 230, 141, 23);
		registerPanel.add(rdbtnShowPassword);
		
		JLabel lblRegisterMsg = new JLabel("Register Message");
		lblRegisterMsg.setBounds(25, 258, 238, 40);
		lblRegisterMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegisterMsg.setVisible(false);
		registerPanel.add(lblRegisterMsg);
		

		btnSignUp.addActionListener(e -> {
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
				lblRegisterMsg.setVisible(true);
				lblRegisterMsg.setText("Sign up Successfully");

			} catch (GuiException e1) {
				lblRegisterMsg.setVisible(true);
				lblRegisterMsg.setText(e1.getErrMessage());
			}
		});
	}

	/**
	 * Sets internal padding of JTextField
	 * 
	 * @param tf field that needs internal padding
	 */
	private void setInternalPadding(JTextField tf) {
		tf.setBorder(BorderFactory.createCompoundBorder(tf.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
	}

	/**
	 * Dynamically displays placeholder on JTextField
	 * 
	 * @param tf          Text Field that shows a placeholder
	 * @param placeholder Label that want to be used as a placeholder
	 */
	private void setPlaceholder(JTextField tf, JLabel placeholder) {
		placeholder.setVisible(tf.getText().isEmpty());
		tf.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				changed();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changed();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changed();
			}

			private void changed() {
				placeholder.setVisible(tf.getText().isEmpty());
			}
		});
	}

	/**
	 * displays Saved account and password if remember me button is selected last
	 * login
	 */
	private void diplaySavedInfo() {
		String account = userPreferences.get(SAVED_USERNAME, "");
		String password = userPreferences.get(SAVED_PASSWORD, "");
		if (account.length() != 0 && password.length() != 0) {
			inputUsername.setText(account);
			inputPassword.setText(password);
			rdbtnRememberMe.setSelected(true);
			inputUsername.setCaret(new HighlightCaret());
			inputUsername.setCaretPosition(account.length());
			btnLogin.setEnabled(true);
		}
	}

	/**
	 * adds a documentLister to JTextField and performs the specified function if an
	 * event occurs
	 * 
	 * @param tf       the JTextField
	 * @param function the function that you want to perform when the listener is
	 *                 triggered
	 */
	private void documentListener(JTextField tf, Supplier<Void> function) {
		tf.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				function.get();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				function.get();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				function.get();
			}
		});
	}

	/**
	 * Methods perform when a listener is triggered
	 */
	private static class ListenerActions {
		private static Void toggleLoginBtn() {
			btnLogin.setEnabled(!inputUsername.getText().equals("") && inputPassword.getPassword().length != 0);
			return null;
		}

		private static Void toggleSignUpBtn() {
			btnSignUp.setEnabled(!regUsername.getText().equals("") && !regEmail.getText().equals("")
					&& regPassword.getPassword().length != 0 && regPasswordCheck.getPassword().length != 0
					&& Arrays.equals(regPassword.getPassword(), regPasswordCheck.getPassword()));
			return null;
		}
		private static Void toggleEmailIcon() {
			if (regEmail.getText().length() != 0) {
				Matcher matcher = pattern.matcher(regEmail.getText());
				if (matcher.matches()) {
					iconRegEmailWrong.setVisible(false);
					iconRegEmailOk.setVisible(true);
					regEmail.setToolTipText("");
				}
				else {
					iconRegEmailOk.setVisible(false);
					iconRegEmailWrong.setVisible(true);
					regEmail.setToolTipText("Wrong E-mail Format!");
				}
			}
			else {
				iconRegEmailWrong.setVisible(false);
				iconRegEmailOk.setVisible(false);
			}
			return null;
		}
		private static Void togglePasswordIcon() {
			if (regPassword.getPassword().length != 0
				&& regPasswordCheck.getPassword().length != 0) {
				if (Arrays.equals(regPassword.getPassword(), regPasswordCheck.getPassword())) {
					iconRegPasswordWrong.setVisible(false);
					iconRegPasswordOk.setVisible(true);
					regPasswordCheck.setToolTipText("");
				}
				else {
					iconRegPasswordOk.setVisible(false);
					iconRegPasswordWrong.setVisible(true);
					regPasswordCheck.setToolTipText("Passwords do not match!");
				}
			}
			else {
				iconRegPasswordWrong.setVisible(false);
				iconRegPasswordOk.setVisible(false);
			}
			return null;
		}
	}
	/**
	 * Sets JTextField input length limit
	 */
	private class JTextFieldLimit extends PlainDocument {
		private int limit;
		JTextFieldLimit(int limit) {
			super();
			this.limit = limit;
		}
		@Override
		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null) return;
			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}
	}
}
