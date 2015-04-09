//@author A0112715R
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.JLabel;

public class UserInterface {

	private JFrame frame;
	private JTextPane commandFromUser;
	private static JTextPane showToUser;
	private static Controller control;
	private static JPanel outerPanel;
	private DefaultStyledDocument doc = new DefaultStyledDocument();
	private static UserInterface UI = null;
	private JPanel feedbackPanel;
	private JPanel topPanel;
	private JTextPane feedback;
	private JButton closeButton;
	private JButton minimiseButton;
	private JButton maximizeButton;
	private JPanel buttonPanel;
	private JLabel welcomeLabel;
	private Point mouseDownCoords;
	private int noOfCurrentCmd;
	private boolean isMaximizeFrame= true;
	private ArrayList<String> commandsEntered = new ArrayList<String>();
	private int noOfCommandsEntered;
	private String message = "";

	
	public static UserInterface getInstance() {
		if (UI == null) {
			UI = new UserInterface();
		}
		return UI;
	}
	
	public static void main (String [] args){
		executeInterface();
	}

	public static void executeInterface(){
	SwingUtilities.invokeLater(new Runnable(){
	public void run() {
		control = Controller.getInstance();
		UserInterface window = UserInterface.getInstance();
		window.initialize();
	//	control.executeCommand("home");
		showToUser.setContentType("text/html");
		try {
			showToUser.setText(Display.getInstance().getView().show());
		} catch (BadLocationException | IOException e) {
			e.printStackTrace();
		}

		window.frame.setUndecorated(true);
		window.frame.setOpacity(0.99f);
		window.frame.setVisible(true);
	}
	});}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(552, 535);
		frame.getRootPane().setBorder(
				BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(84, 121,
						163).darker()));

		makeFrameMovable();
		initializeCmdFromUser();
		initializeShowToUser();
		initializeFeedback();
		initializeCloseButton();
		initializeMaximizeButton();
		initializeMinimizeButton();
		initializeWelcomeLabel();
		processKeyPressed();
	}

	private void makeFrameMovable() {
		mouseDownCoords = null;
		frame.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				mouseDownCoords = null;
			}

			public void mousePressed(MouseEvent e) {
				mouseDownCoords = e.getPoint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		});

		frame.addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
			}

			public void mouseDragged(MouseEvent e) {
				Point currCoords = e.getLocationOnScreen();
				frame.setLocation(currCoords.x - mouseDownCoords.x,
						currCoords.y - mouseDownCoords.y);
			}
		});

	}

	private void initializeWelcomeLabel() {
		welcomeLabel = new JLabel(" OneTag", SwingConstants.LEFT);
		welcomeLabel.setFont(new Font("Elephant", Font.PLAIN, 16));
		welcomeLabel.setForeground(new Color(255, 255, 255));
		topPanel.add(welcomeLabel, BorderLayout.CENTER);
	}

	private void initializeCmdFromUser() {
		outerPanel = new JPanel();
		frame.getContentPane().add(outerPanel, BorderLayout.CENTER);
		outerPanel.setLayout(new BorderLayout(0, 0));
		outerPanel.setBackground(Color.WHITE);
		outerPanel.setBounds(0, 0, 612, 425);
		outerPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(
				30, 144, 255), new Color(0, 0, 0)));
		colourRestrictedWords();
		commandFromUser = new JTextPane(doc);
		commandFromUser.setToolTipText("Enter command here");
		outerPanel.add(commandFromUser, BorderLayout.SOUTH);
		commandFromUser.setFont(new Font("MV Boli", Font.PLAIN, 20));
		commandFromUser.setBackground(new Color(255, 255, 255));
		commandFromUser.setBounds(20, 371, 573, 43);
		commandFromUser.setForeground(new Color(0, 0, 0));
		commandFromUser.setBorder(new EtchedBorder(EtchedBorder.LOWERED,
				new Color(30, 144, 255), new Color(0, 0, 0)));

	}

	private void initializeShowToUser() {
		feedbackPanel = new JPanel();
		outerPanel.add(feedbackPanel, BorderLayout.CENTER);
		feedbackPanel.setLayout(new BorderLayout(0, 0));
		showToUser = new JTextPane();
		showToUser.setContentType("text/html");
		feedbackPanel.add(showToUser, BorderLayout.CENTER);
		showToUser.setContentType("");
		showToUser.setFont(new Font("Lucida Fax", Font.PLAIN, 16));
		showToUser.setBackground(new Color(255, 255, 255));
		showToUser.setEditable(false);
		showToUser.setForeground(new Color(0, 0, 128));
		showToUser.setBorder(null);
		showToUser.setBounds(20, 10, 573, 350);
	}

	private void initializeFeedback() {
		feedback = new JTextPane();
		feedback.setForeground(new Color(153, 0, 153));
		feedback.setFont(new Font("Candara", Font.BOLD | Font.ITALIC, 6));
		feedback.setEditable(false);
		feedbackPanel.add(feedback, BorderLayout.SOUTH);
	}

	private void initializeCloseButton() {
		topPanel = new JPanel();
		topPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(30,
				144, 255), new Color(0, 0, 0)));
		topPanel.setBackground(new Color(34, 77, 124));
		outerPanel.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BorderLayout(0, 0));

		buttonPanel = new JPanel();
		topPanel.add(buttonPanel, BorderLayout.EAST);
		buttonPanel.setLayout(new BorderLayout(0, 0));

		closeButton = new JButton("X");
		buttonPanel.add(closeButton, BorderLayout.EAST);
		closeButton.setFont(new Font("Calibri", Font.BOLD, 12));
		closeButton.setBorder(UIManager.getBorder("Button.border"));
		closeButton.setBackground(new Color(255, 255, 255));
		closeButton.setForeground(new Color(0, 0, 128));
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
	}

	private void initializeMinimizeButton() {
		minimiseButton = new JButton("-");
		buttonPanel.add(minimiseButton, BorderLayout.WEST);
		buttonPanel.setBorder(UIManager.getBorder("TextArea.border"));
		minimiseButton.setFont(new Font("Calibri", Font.BOLD, 13));
		minimiseButton.setBackground(new Color(255, 255, 255));
		minimiseButton.setForeground(new Color(0, 0, 128));
		minimiseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// minimizeApplication();
				frame.setState(frame.ICONIFIED);
			}
		});
	}
	
	private void initializeMaximizeButton() {
		maximizeButton = new JButton("+");
		buttonPanel.add(maximizeButton, BorderLayout.CENTER);
		buttonPanel.setBorder(UIManager.getBorder("TextArea.border"));
		maximizeButton.setFont(new Font("Calibri", Font.BOLD, 15));
		maximizeButton.setBackground(new Color(255, 255, 255));
		maximizeButton.setForeground(new Color(0, 0, 128));
		

		maximizeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(isMaximizeFrame){
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				isMaximizeFrame = false;
			}
				else{
					frame.setSize(552, 535);
					isMaximizeFrame = true;
				}
			}
		});
	}

	private void processKeyPressed() {
		inputBoxChangeColour();
		commandFromUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					processCommand();
					e.consume();
					commandFromUser.setText("");
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					pressedUpKey();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					pressedDownKey();
				}

			}
		});
	}

	private void processCommand() {
		String command = commandFromUser.getText();
		commandsEntered.add(command);
		noOfCommandsEntered = commandsEntered.size();
		showToUser.setContentType("text/html");
		control.executeCommand(command);
		showMessageToUser();
		try {
			showToUser.setText(Display.getInstance().getView().show());
		} catch (BadLocationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}

	private void pressedUpKey() {
		noOfCommandsEntered--;
		try {
			commandFromUser.setText(commandsEntered.get(noOfCommandsEntered));
			noOfCurrentCmd = noOfCommandsEntered;
		} catch (ArrayIndexOutOfBoundsException e1) {
			noOfCommandsEntered = 0;
		}
	}

	private void pressedDownKey() {
		try {
			commandFromUser.setText(commandsEntered.get(noOfCurrentCmd++));
			noOfCommandsEntered = noOfCurrentCmd;
		}

		catch (IndexOutOfBoundsException e2) {
			commandFromUser.setText("");
			noOfCommandsEntered = commandsEntered.size();
		}
	}

	private void inputBoxChangeColour() {
		commandFromUser.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				commandFromUser.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				commandFromUser.setBackground(Color.WHITE);
			}
		});

	}

	private void showMessageToUser() {
		StringBuilder htmlMessage = new StringBuilder();
		feedback.setContentType("text/html");
		message = Display.getInstance().getMessage();
		try {
			if (!message.isEmpty()) {
				htmlMessage.append("<html><p valign=\"top\" align=\"center\"><font face=\"Lucida sans\" color=\"#660000\" size=\"5\">"+Display.getInstance().getMessage()+"</font></p></html>");
				feedback.setText((htmlMessage.toString()) + "\n");
			}
		} catch (NullPointerException nullException) {
			feedback.setText("");
		}
	}

	private void colourRestrictedWords() {
		StyleContext cont = StyleContext.getDefaultStyleContext();
		final AttributeSet attr = cont.addAttribute(cont.getEmptySet(),
				StyleConstants.Foreground, Color.RED);
		final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(),
				StyleConstants.Foreground, Color.BLACK);
		doc = new DefaultStyledDocument() {
			public void insertString(int offset, String str, AttributeSet a)
					throws BadLocationException {
				super.insertString(offset, str, a);

				String text = getText(0, getLength());
				int before = findLastNonWordChar(text, offset);
				if (before < 0)
					before = 0;
				int after = findFirstNonWordChar(text, offset + str.length());
				int wordL = before;
				int wordR = before;

				while (wordR <= after) {
					if (wordR == after
							|| String.valueOf(text.charAt(wordR))
									.matches("\\W")) {
						if (text.substring(wordL, wordR)
								.toLowerCase()
								.matches("(\\W)*(^(add)|^(delete)|^(edit)|^(search)|from(?!.*from)|to|by|^(today|upcoming|someday)|^(help)|^(done)|^(save)|^(home))")) {
							setCharacterAttributes(wordL, wordR - wordL, attr,
									false);
						} else {
							setCharacterAttributes(wordL, wordR - wordL,
									attrBlack, false);
						}
						wordL = wordR;
					}
					wordR++;
				}
			}

			public void remove(int offs, int len) throws BadLocationException {
				super.remove(offs, len);

				String text = getText(0, getLength());
				int before = findLastNonWordChar(text, offs);
				if (before < 0)
					before = 0;
				int after = findFirstNonWordChar(text, offs);

				if (text.substring(before, after).toLowerCase()
						.matches("(\\W)*(^(add)|delete|edit|search|from(?!.*from)|to|by|^(today)|upcoming|someday|help|done|^(save)|^(home))")) {
					setCharacterAttributes(before, after - before, attr, false);
				} else {
					setCharacterAttributes(before, after - before, attrBlack,
							false);
				}
			}

		};
	}

	private int findLastNonWordChar(String text, int index) {
		while (--index >= 0) {
			if (String.valueOf(text.charAt(index)).matches("\\W")) {
				break;
			}
		}
		return index;
	}

	private int findFirstNonWordChar(String text, int index) {
		while (index < text.length()) {
			if (String.valueOf(text.charAt(index)).matches("\\W")) {
				break;
			}
			index++;
		}
		return index;
	}

}
