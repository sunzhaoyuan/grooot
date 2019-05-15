
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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
import java.util.Scanner;
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

	private JPasswordField newpass;
	private JPasswordField confirmpass;
	private static JButton[][] buttons;
	private static JButton resetgame1;

	private static CardLayout windows;

	private static int moveCounter = 9;
	private static boolean gameWon = false;
	private static int WhoseTurn = 1;

	private static int loginTrials = 0;
	private static int maxLoginTrials = 5;

	private static String loggedinUser = "";
	private static String loggedinName = "";

	private JTextField chatMsg;

	private JTextField textField;
	private String creatorID;
	Socket client;

	/**
	 * Create the frame (constructor)
	 * 
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public GameRoom() throws UnknownHostException, IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 984, 575);

		setTitle("Groot");
		JPanel controlPanel = new JPanel();

		JPanel loginPanel = new JPanel();
		JPanel modifyPanel = new JPanel();
		JPanel tictactoePanel = new JPanel();

		JPanel characterPanel = new JPanel();
		JPanel selectPanel = new JPanel();

		characterPanel.setVisible(false);
		selectPanel.setVisible(false);
		controlPanel.setVisible(false);

		loginPanel.setVisible(true);
		modifyPanel.setVisible(false);
		tictactoePanel.setVisible(false);

		loginPanel.setLayout(null);

		contentPane = new JPanel(new CardLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		windows = (CardLayout) contentPane.getLayout();

		contentPane.add(loginPanel, "Login");
		contentPane.add(selectPanel, "select");
		contentPane.add(characterPanel, "character");

		contentPane.add(controlPanel, "Controlpanel");

		this.setLocationRelativeTo(null);

		JScrollPane scrollPane_7 = new JScrollPane();
		scrollPane_7.setBounds(296, 172, 642, 173);

		JList list_6 = new JList();
		scrollPane_7.setViewportView(list_6);

		newpass = new JPasswordField();
		newpass.setBounds(138, 204, 122, 28);

		confirmpass = new JPasswordField();
		confirmpass.setBounds(138, 244, 122, 28);

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
		// JButton btnGetGames = new JButton("Get Games");

		/*
		 * =========================================================== Character
		 * ===========================================================
		 */

		JButton btnCharacter = new JButton("Choose Character!");
		btnCharacter.setBounds(403, 250, 130, 29);

		JButton btnPlay = new JButton("Play Game!");
		btnPlay.setBounds(403, 250, 130, 29);

		characterPanel.add(btnCharacter, btnCharacter.CENTER_ALIGNMENT);
		characterPanel.add(btnPlay, btnPlay.CENTER_ALIGNMENT);

		btnCharacter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				windows.show(contentPane, "select");

			}
		});
		btnPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				windows.show(contentPane, "select");

				DataOutputStream out;
				try {
					out = new DataOutputStream(client.getOutputStream());
					out.writeUTF("Create User " + creatorID);
					out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				DataInputStream in;
				try {

					in = new DataInputStream(client.getInputStream());
					String input = in.readUTF();
					if (input.contains("200")) {
						windows.show(characterPanel, "character");
					}

				} catch (IOException e1) {

					e1.printStackTrace();
				}

			}
		});

		/*
		 * =========================================================== Select
		 * ===========================================================
		 */

		JButton btn1 = new JButton("Character 1");
		btn1.setBounds(403, 250, 130, 29);
		JButton btn2 = new JButton("Character 2");
		btn2.setBounds(403, 250, 130, 29);
		JButton btn3 = new JButton("Character 3");
		btn3.setBounds(403, 250, 130, 29);
		JButton btn4 = new JButton("Character 4");
		btn4.setBounds(403, 250, 130, 29);

		selectPanel.add(btn1, btn1.CENTER_ALIGNMENT);
		selectPanel.add(btn2, btn2.CENTER_ALIGNMENT);
		selectPanel.add(btn3, btn3.CENTER_ALIGNMENT);
		selectPanel.add(btn4, btn4.CENTER_ALIGNMENT);

		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				DataOutputStream out;
				try {
					out = new DataOutputStream(client.getOutputStream());
					out.writeUTF("Create Chatroom " + creatorID + " " + creatorID + "_room");
					out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				DataInputStream in;
				try {

					in = new DataInputStream(client.getInputStream());
					String input = in.readUTF();
					if (input.contains("200")) {
						windows.show(contentPane, "Controlpanel");
						newpass.setText("");
						confirmpass.setText("");
						welcomeLabel.setText("Welcome Character1!");
						usersCombo.removeAllItems();
					}

				} catch (IOException e1) {

					e1.printStackTrace();
				}

			}
		});

		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				DataOutputStream out;
				try {
					out = new DataOutputStream(client.getOutputStream());
					out.writeUTF("Create Chatroom " + creatorID + " " + creatorID + "_room");
					out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				DataInputStream in;
				try {
					in = new DataInputStream(client.getInputStream());
					if (in.readUTF().contains("200")) {
						windows.show(contentPane, "Controlpanel");
						newpass.setText("");
						confirmpass.setText("");
						welcomeLabel.setText("Welcome Character2!");
						usersCombo.removeAllItems();
					}
				} catch (IOException e1) {

					e1.printStackTrace();
				}

			}
		});
		btn3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DataOutputStream out;
				try {
					out = new DataOutputStream(client.getOutputStream());
					out.writeUTF("Create Chatroom " + creatorID + " " + creatorID + "_room");
					out.flush();
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				DataInputStream in;
				try {
					in = new DataInputStream(client.getInputStream());
					if (in.readUTF().contains("200")) {
						windows.show(contentPane, "Controlpanel");
						newpass.setText("");
						confirmpass.setText("");
						welcomeLabel.setText("Welcome Character3!");
						usersCombo.removeAllItems();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btn4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataOutputStream out;
				try {
					out = new DataOutputStream(client.getOutputStream());
					out.writeUTF("Create Chatroom " + creatorID + " " + creatorID + "_room");
					out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				DataInputStream in;
				try {
					in = new DataInputStream(client.getInputStream());
					if (in.readUTF().contains("200")) {
						windows.show(contentPane, "Controlpanel");
						newpass.setText("");
						confirmpass.setText("");
						welcomeLabel.setText("Welcome Character4!");
						usersCombo.removeAllItems();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});

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
				boolean flag = true;

				try {

					client = new Socket(ip, port);
					System.out.println("connected to:" + ip + " Port number:" + port);
					System.out.println("Connection succeed!");

					// String username = textuser.getText();
					String password = textpass.getText();

					creatorID = textuser.getText();

					DataOutputStream out;
					try {
						out = new DataOutputStream(client.getOutputStream());
						out.writeUTF("Create User " + creatorID);
						out.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					DataInputStream in;
					try {

						in = new DataInputStream(client.getInputStream());
						String input = in.readUTF();
						if (input.contains("200")) {
							windows.show(contentPane, "character");
						}

					} catch (IOException e1) {

						e1.printStackTrace();
					}
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
		 * =========================================================== Welcome
		 * ===========================================================
		 */

		JLabel lblUsername_1 = new JLabel("Nickname:");
		lblUsername_1.setBounds(323, 179, 76, 16);
		loginPanel.add(lblUsername_1);

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

		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = textField.getText();
				String recipient = "GLOBAL";

				if (message.equals("")) {
					chatMsg.setText("");
					return;
				}

				try {
					DataOutputStream out = new DataOutputStream(client.getOutputStream());
					out.writeUTF(message);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				chatMsg.setText(message);
				DataInputStream in;
				try {
					in = new DataInputStream(client.getInputStream());
					textArea_1.append("Alice: " + message + "\n");
					textArea_1.append("Response: " + in.readUTF() + "\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				textField.setText("");
			}
		});
		btnSend.setBounds(6, 435, 118, 34);
		globalChatP.add(btnSend);

		JButton btnSignout = new JButton("Signout");
		btnSignout.setBounds(837, 493, 115, 27);
		controlPanel.add(btnSignout);
		btnSignout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DataOutputStream out = new DataOutputStream(client.getOutputStream());

					out.writeUTF("quit");
					client.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				windows.show(contentPane, "Login");
				textuser.setText(loggedinUser);
				textpass.setText("");
				loggedinUser = "";
			}
		});

		// ENTER key for sending message
		chatMsg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("ahhhhhhhh");
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