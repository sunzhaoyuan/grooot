
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.TextArea;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import javax.swing.JSplitPane;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JToolBar;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;

import javax.swing.border.BevelBorder;
import javax.swing.ListSelectionModel;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.swing.AbstractListModel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import java.io.*;
import java.net.Socket;

public class GameRoom extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textuser;
	private JPasswordField textpass;
	private JTextField userSignup;
	private JTextField fnameSignup;
	private JTextField lnameSignup;
	private JPasswordField passSignup;
	private JPasswordField newpass;
	private JPasswordField confirmpass;
	private static JButton[][] buttons;
	private static JButton resetgame1;

	private static CardLayout windows;

	private static int moveCounter = 9;
	private static boolean gameWon = false;
	private static int WhoseTurn = 1;

	private static String serverResponse = null;
	private static String clientResponse = null;
	private static String errorMsg;
	private static String errorTitle;
	private static int loginTrials = 0;
	private static int maxLoginTrials = 5;
	private static Boolean loginFlag;
	private static String loggedinUser = "";
	private static String loggedinName = "";
	private static String opponentUsername = "";
	private static Boolean addFlag;
	private JTextField chatMsg;
	private static Timer timerForUsers;
	private static Timer timerForMsgs;
	private static Timer timerForGlobal;
	private static Timer timerForGames;
	private static Timer timerForMoves;
	private final static int ONE_SECOND1 = 800;
	private final static int ONE_SECOND2 = 2000;
	private final static int TEN_SECOND1 = 1500;
	private final static int TEN_SECOND2 = 1000;
	private final static int FIVE_SECOND = 5000;
	private static int lastchatID = 0;
	private static int numRequests = 0;
	private static String selectedUserChat = " ";
	private JTextField textField;

	/**
	 * Create the frame (constructor)
	 * 
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public GameRoom() throws UnknownHostException, IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 984, 575);

		setTitle("The GameRoom Login Beta0.x");
		JPanel controlPanel = new JPanel();
		JPanel signupPanel = new JPanel();
		JPanel loginPanel = new JPanel();
		JPanel modifyPanel = new JPanel();
		JPanel tictactoePanel = new JPanel();
		JPanel scoresPanel = new JPanel();

		controlPanel.setVisible(false);
		signupPanel.setVisible(false);
		loginPanel.setVisible(true);
		modifyPanel.setVisible(false);
		tictactoePanel.setVisible(false);
		scoresPanel.setVisible(false);
		loginPanel.setLayout(null);

		contentPane = new JPanel(new CardLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		windows = (CardLayout) contentPane.getLayout();

		contentPane.add(loginPanel, "Login");
		contentPane.add(signupPanel, "Signup");
		contentPane.add(controlPanel, "Controlpanel");
		contentPane.add(modifyPanel, "Modify");
		contentPane.add(tictactoePanel, "TicTacToe");
		contentPane.add(scoresPanel, "Scores");
		scoresPanel.setLayout(null);

		JLabel lblTicTacToe = new JLabel("Tic Tac Toe");
		lblTicTacToe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTicTacToe.setFont(new Font("SansSerif", Font.BOLD, 22));
		lblTicTacToe.setBounds(208, 87, 464, 38);
		scoresPanel.add(lblTicTacToe);

		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(36, 172, 179, 173);
		scoresPanel.add(scrollPane_6);

		JList list_5 = new JList();
		scrollPane_6.setViewportView(list_5);

		JLabel lblRanking = new JLabel("Ranking");
		lblRanking.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_6.setColumnHeaderView(lblRanking);
		lblRanking.setFont(new Font("SansSerif", Font.BOLD, 19));

		JButton btnBack_2 = new JButton("Back");
		btnBack_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windows.show(contentPane, "Controlpanel");
			}
		});
		btnBack_2.setBounds(6, 492, 90, 28);
		scoresPanel.add(btnBack_2);

		JButton btnScores = new JButton("Scores");

		JButton btnRefresh_1 = new JButton("Refresh");
		btnRefresh_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnScores.doClick();
			}
		});
		btnRefresh_1.setBounds(6, 452, 90, 28);
		scoresPanel.add(btnRefresh_1);

		JScrollPane scrollPane_7 = new JScrollPane();
		scrollPane_7.setBounds(296, 172, 642, 173);
		scoresPanel.add(scrollPane_7);

		JList list_6 = new JList();
		scrollPane_7.setViewportView(list_6);

		JLabel lblHistory = new JLabel("History");
		lblHistory.setHorizontalAlignment(SwingConstants.CENTER);
		lblHistory.setFont(new Font("SansSerif", Font.BOLD, 19));
		scrollPane_7.setColumnHeaderView(lblHistory);
		tictactoePanel.setLayout(new BorderLayout(0, 0));
		modifyPanel.setLayout(null);

		JLabel lblChangePassword = new JLabel("Change password");
		lblChangePassword.setBounds(101, 166, 159, 26);
		modifyPanel.add(lblChangePassword);
		lblChangePassword.setFont(new Font("Lucida Grande", Font.PLAIN, 20));

		newpass = new JPasswordField();
		newpass.setBounds(138, 204, 122, 28);
		modifyPanel.add(newpass);
		newpass.setColumns(10);

		confirmpass = new JPasswordField();
		confirmpass.setBounds(138, 244, 122, 28);
		modifyPanel.add(confirmpass);
		confirmpass.setColumns(10);

		JButton btnNewButton_1 = new JButton("Submit");
		btnNewButton_1.setBounds(138, 284, 67, 28);
		modifyPanel.add(btnNewButton_1);

		JLabel lblNewPassword = new JLabel("New password:");
		lblNewPassword.setBounds(40, 210, 86, 16);
		modifyPanel.add(lblNewPassword);

		JLabel lblConfirm = new JLabel("Confirm:");
		lblConfirm.setBounds(79, 250, 47, 16);
		modifyPanel.add(lblConfirm);

		JButton btnBack_1 = new JButton("Back");
		btnBack_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				windows.show(contentPane, "Controlpanel");
			}
		});

		setContentPane(contentPane);
		JLabel welcomeLabel = new JLabel();
		welcomeLabel.setBounds(644, 6, 274, 35);
		controlPanel.setLayout(null);

		JTabbedPane chatTab = new JTabbedPane(JTabbedPane.TOP);
		chatTab.setBounds(6, 6, 229, 504);
		controlPanel.add(chatTab);
		JTextArea textArea = new JTextArea();
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		JPanel userChatP = new JPanel();
		chatTab.addTab("Private Chat", null, userChatP, null);
		userChatP.setLayout(null);

		JPanel globalChatP = new JPanel();
		chatTab.addTab("Global Chat", null, globalChatP, null);
		globalChatP.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(6, 22, 217, 365);
		globalChatP.add(scrollPane_1);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setWrapStyleWord(true);
		textArea_1.setLineWrap(true);
		textArea_1.setEditable(false);
		scrollPane_1.setViewportView(textArea_1);

		textField = new JTextField();
		textField.setBounds(6, 399, 217, 27);
		globalChatP.add(textField);
		textField.setColumns(10);

		JButton btnSend = new JButton("Send Msg");

		btnSend.setBounds(6, 435, 118, 34);
		globalChatP.add(btnSend);

		JComboBox<String> usersCombo = new JComboBox<String>();
		usersCombo.setBounds(105, 398, 118, 25);
		userChatP.add(usersCombo);

		textuser = new JTextField();
		textuser.setBounds(403, 174, 130, 26);
		loginPanel.add(textuser);
		textuser.setColumns(10);

		textpass = new JPasswordField();
		textpass.setBounds(403, 212, 130, 26);
		loginPanel.add(textpass);
		textpass.setColumns(10);
		JButton btnGetGames = new JButton("Get Games");

		/*
		 * =========================================================== LOGIN
		 * ===========================================================
		 */

		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(403, 250, 130, 29);
		loginPanel.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String ip = "127.0.0.1";
				int port = 5000;

				try {
					System.out.println("connected to:" + ip + " Port number:" + port);
					Socket client = new Socket(ip, port);
					System.out.println("server address:" + client.getRemoteSocketAddress());
					OutputStream outToServer = client.getOutputStream();
					DataOutputStream out = new DataOutputStream(outToServer);

					out.writeUTF("Hello from " + client.getLocalSocketAddress());
					InputStream inFromServer = client.getInputStream();
					DataInputStream in = new DataInputStream(inFromServer);
					System.out.println("Response:" + in.readUTF());

					String username = textuser.getText();
					String password = textpass.getText();
					// check empty string case
					if (username.equals("") || password.equals("")) {
						username = password = " ";

						textArea.setText("");
						loggedinUser = username; // remember username
						loginTrials = 0;
						windows.show(contentPane, "Controlpanel");
						newpass.setText("");
						confirmpass.setText("");
						welcomeLabel.setText("Welcome " + loggedinName);
						usersCombo.removeAllItems();

						btnGetGames.doClick();

					}
					client.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});

		// ENTER key for login button
		textpass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnLogin.doClick();
			}
		});

		/*
		 * =========================================================== SIGNUP
		 * ===========================================================
		 */

		JButton btnSignup = new JButton("Sign-Up");

		btnSignup.setBounds(403, 306, 130, 29);
		loginPanel.add(btnSignup);

		JLabel lblUsername_1 = new JLabel("Username:");
		lblUsername_1.setBounds(323, 179, 76, 16);
		loginPanel.add(lblUsername_1);

		JLabel lblPassword_1 = new JLabel("Password:");
		lblPassword_1.setBounds(323, 217, 68, 16);
		loginPanel.add(lblPassword_1);

		JLabel lblNewHere = new JLabel("New here?");
		lblNewHere.setBounds(323, 311, 76, 16);
		loginPanel.add(lblNewHere);

		JButton btnNewButton = new JButton("Sign Up");
		btnNewButton.setBounds(389, 312, 130, 29);

		signupPanel.setLayout(null);
		signupPanel.add(btnNewButton);

		userSignup = new JTextField();
		userSignup.setBounds(389, 160, 130, 26);
		signupPanel.add(userSignup);
		userSignup.setColumns(10);

		fnameSignup = new JTextField();
		fnameSignup.setBounds(389, 198, 130, 26);
		signupPanel.add(fnameSignup);
		fnameSignup.setColumns(10);

		lnameSignup = new JTextField();
		lnameSignup.setBounds(389, 236, 130, 26);
		signupPanel.add(lnameSignup);
		lnameSignup.setColumns(10);

		passSignup = new JPasswordField();
		passSignup.setBounds(389, 274, 130, 26);
		signupPanel.add(passSignup);
		passSignup.setColumns(10);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(313, 165, 70, 16);
		signupPanel.add(lblUsername);

		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(313, 203, 84, 16);
		signupPanel.add(lblFirstName);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(313, 241, 70, 16);
		signupPanel.add(lblLastName);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(313, 274, 84, 16);
		signupPanel.add(lblPassword);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windows.show(contentPane, "Login");
				loggedinUser = "";
				userSignup.setText("");
				fnameSignup.setText("");
				lnameSignup.setText("");
				passSignup.setText("");
				textuser.setText("");
				textpass.setText("");
			}
		});
		btnBack.setBounds(6, 481, 117, 29);
		signupPanel.add(btnBack);

		welcomeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 19));
		controlPanel.add(welcomeLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 22, 217, 323);
		userChatP.add(scrollPane);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		scrollPane.setViewportView(textArea);

		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);

		chatMsg = new JTextField();
		chatMsg.setBounds(6, 357, 217, 27);
		userChatP.add(chatMsg);
		chatMsg.setColumns(10);

		JButton btnSendMsg = new JButton("Send Msg");
		btnSendMsg.setBounds(6, 435, 118, 34);
		userChatP.add(btnSendMsg);

		JLabel lblRecipient = new JLabel("Recipient:");
		lblRecipient.setBounds(36, 403, 57, 15);
		userChatP.add(lblRecipient);

		JButton btnModifyProfile = new JButton("Modify Profile");
		btnModifyProfile.setBounds(804, 47, 114, 27);
		btnModifyProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windows.show(contentPane, "Modify");
			}
		});
		controlPanel.add(btnModifyProfile);

		JButton btnSignout = new JButton("Signout");
		btnSignout.setBounds(837, 493, 115, 27);
		controlPanel.add(btnSignout);
		btnSignout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				windows.show(contentPane, "Login");
				textuser.setText(loggedinUser);
				textpass.setText("");
				loggedinUser = "";
			}
		});
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(677, 86, 241, 407);
		controlPanel.add(tabbedPane);
		JList<String> friendList = new JList<String>();

		JPanel panel = new JPanel();
		tabbedPane.addTab("Add Friends", null, panel, null);
		panel.setLayout(null);

		JButton userSearch = new JButton("Search for Friends");

		userSearch.setBounds(6, 251, 229, 33);
		panel.add(userSearch);

		JButton btnSendRequest = new JButton("Add Friend");

		btnSendRequest.setBounds(6, 175, 229, 27);
		panel.add(btnSendRequest);

		btnSendRequest.setEnabled(false);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(6, 6, 229, 157);
		panel.add(scrollPane_2);

		scrollPane_2.setViewportView(friendList);

		friendList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Manage Friends", null, panel_1, null);
		panel_1.setLayout(null);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(6, 6, 229, 157);
		panel_1.add(scrollPane_3);

		JList list = new JList();
		scrollPane_3.setViewportView(list);

		JButton btnGetFriends = new JButton("Get Friends");

		btnGetFriends.setBounds(6, 175, 229, 27);
		panel_1.add(btnGetFriends);

		JButton btnRemoveFriend = new JButton("Remove Friend");

		btnRemoveFriend.setEnabled(false);
		btnRemoveFriend.setBounds(6, 214, 114, 27);
		panel_1.add(btnRemoveFriend);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(321, 346, 305, 180);
		controlPanel.add(tabbedPane_1);

		JPanel panel_2 = new JPanel();
		tabbedPane_1.addTab("Choose Character", null, panel_2, null);
		panel_2.setLayout(null);

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(6, 6, 293, 78);
		panel_2.add(scrollPane_4);

		JList list_3 = new JList();
		scrollPane_4.setViewportView(list_3);

		JButton btnNewButton_3 = new JButton("Accept");

		btnNewButton_3.setBounds(203, 116, 90, 28);
		panel_2.add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("Decline");

		btnNewButton_4.setBounds(203, 84, 90, 28);
		panel_2.add(btnNewButton_4);

		JButton btnNewButton_5 = new JButton("Refresh");

		btnNewButton_5.setBounds(16, 96, 90, 28);
		panel_2.add(btnNewButton_5);

		JPanel panel_3 = new JPanel();
		tabbedPane_1.addTab("Accepted Requests", null, panel_3, null);
		panel_3.setLayout(null);

		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(6, 6, 293, 78);
		panel_3.add(scrollPane_5);

		JList list_4 = new JList();
		scrollPane_5.setViewportView(list_4);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(249, 6, 416, 337);
		controlPanel.add(panel_4);
		panel_4.setLayout(null);

		JScrollPane game_select = new JScrollPane();
		game_select.setBounds(68, 5, 262, 112);
		panel_4.add(game_select);

		JLabel lblSelectGame = new JLabel("Select Game");
		lblSelectGame.setHorizontalAlignment(SwingConstants.CENTER);
		game_select.setColumnHeaderView(lblSelectGame);

		JList list_1 = new JList();
		game_select.setViewportView(list_1);

		JScrollPane game_user_select = new JScrollPane();
		game_user_select.setBounds(68, 129, 262, 112);
		panel_4.add(game_user_select);

		JLabel lblSelectFriend = new JLabel("Select From Online Friends");
		lblSelectFriend.setHorizontalAlignment(SwingConstants.CENTER);
		game_user_select.setColumnHeaderView(lblSelectFriend);

		JList list_2 = new JList();
		game_user_select.setViewportView(list_2);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(338, 166, 72, 28);
		panel_4.add(btnRefresh);

		JButton btnNewButton_2 = new JButton("Send Request");
		btnNewButton_2.setBounds(135, 263, 107, 28);
		panel_4.add(btnNewButton_2);

		list_2.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {

					if ((list_2.getSelectedIndex() == -1) && (list_1.getSelectedIndex() == -1)) {
						// No selection, disable fire button.
						btnNewButton_2.setEnabled(false);
					} else {
						// Selection, enable the fire button.
						btnNewButton_2.setEnabled(true);
					}
				}
			}

		});

		btnGetGames.setBounds(558, 46, 107, 28);
		// controlPanel.add(btnGetGames);

		friendList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {

					if (friendList.getSelectedIndex() == -1) {
						// No selection, disable fire button.
						btnSendRequest.setEnabled(false);
					} else {
						// Selection, enable the fire button.
						btnSendRequest.setEnabled(true);
					}
				}
			}
		});

		btnNewButton_4.setEnabled(false);
		list_3.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {

					if (list_3.getSelectedIndex() == -1) {
						btnNewButton_4.setEnabled(false);
					} else {
						btnNewButton_4.setEnabled(true);
					}
				}
			}
		});

		btnNewButton_3.setEnabled(false);

		btnScores.setBounds(677, 47, 114, 27);
		controlPanel.add(btnScores);

		// ENTER key for sending message
		chatMsg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSendMsg.doClick();
			}
		});

		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSend.doClick();
			}
		});
	}

	private static void disableButtons() {
		// disable buttons of tictactoe
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j].setEnabled(false);
			}
		}
	}

	private static void enableButtons() {
		// disable buttons of tictactoe
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (buttons[i][j].getText().equals("")) {
					buttons[i][j].setEnabled(true);
				}
			}
		}
	}

}