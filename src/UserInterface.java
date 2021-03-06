//@author A0112715R
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

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
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.UIManager;
import javax.swing.JLabel;

public class UserInterface {

	private JFrame frame;
	public static JTextPane commandFromUser;
	public static JTextPane showToUser;
	private static Controller control;
	private static JPanel outerPanel;
	private DefaultStyledDocument doc = new DefaultStyledDocument();
	private static UserInterface UI = null;
	private JPanel feedbackPanel;
	private JPanel topPanel;
	private JTextPane feedback;
	private JButton closeButton;
	private JButton minimiseButton;
	private JPanel buttonPanel;
	private JLabel welcomeLabel;
	private Point mouseDownCoords;
	private int noOfCurrentCmd;
	private ArrayList<String> commandsEntered = new ArrayList<String>();
	private int noOfCommandsEntered;
	private String message = "";
	private String CLASS_MESSAGE = "UserInterface";
	private String ERROR_DISPLAYING_VIEW_MESSAGE = "Error in displaying the view.";
	private String INVALID_INPUT_MESSAGE ="Invalid input entered.";
	
	OneTagLogger logger = OneTagLogger.getInstance();

	
	public static UserInterface getInstance() {
		if (UI == null) {
			UI = new UserInterface();
		}
		return UI;
	}


	/**
	 * Executes the program
	 * @ 
	 */
	public void executeInterface() {
		control = Controller.getInstance();
		UserInterface window = UserInterface.getInstance();
		window.initialize();
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

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
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
		initializeMinimizeButton();
		initializeWelcomeLabel();
		processKeyPressed();
	}

	/**
	 * As frame is undecorated, frame is made movable here (user can move gui)
	 */
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

	/**
	 * The label on top is initialized
	 * 
	 * @return ArrayList<Task>
	 */
	private void initializeWelcomeLabel() {
		welcomeLabel = new JLabel(" OneTag", SwingConstants.LEFT);
		welcomeLabel.setFont(new Font("Elephant", Font.PLAIN, 16));
		welcomeLabel.setForeground(new Color(255, 255, 255));
		topPanel.add(welcomeLabel, BorderLayout.CENTER);
	}

	/**
	 * The JtextPane(textbox) which receives user input is initialized
	 */
	private void initializeCmdFromUser() {
		outerPanel = new JPanel();
		frame.getContentPane().add(outerPanel, BorderLayout.CENTER);
		outerPanel.setLayout(new BorderLayout(0, 0));
		outerPanel.setBackground(Color.WHITE);
		outerPanel.setBounds(0, 0, 612, 425);
		outerPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(
				30, 144, 255), new Color(0, 0, 0)));
		highlightKeywords();
		commandFromUser = new JTextPane(doc);
		commandFromUser.setToolTipText("Enter command here");
		outerPanel.add(commandFromUser, BorderLayout.SOUTH);
		commandFromUser.setFont(new Font("Calibri", Font.PLAIN, 20));
		commandFromUser.setBackground(new Color(255, 255, 255));
		commandFromUser.setBounds(20, 371, 573, 43);
		commandFromUser.setForeground(new Color(0, 0, 0));
		commandFromUser.setBorder(new EtchedBorder(EtchedBorder.LOWERED,
				new Color(30, 144, 255), new Color(0, 0, 0)));

	}

	/**
	 * The JtextPane(textbox) which shows the output is initialized
	 */
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

	/**
	 * The JtextPane(textbox) which shows the feedback for each command execution is initialized
	 */
	private void initializeFeedback() {
		feedback = new JTextPane();
		feedback.setForeground(new Color(153, 0, 153));
		feedback.setFont(new Font("Candara", Font.BOLD | Font.ITALIC, 6));
		feedback.setEditable(false);
		feedbackPanel.add(feedback, BorderLayout.SOUTH);
		feedback.setContentType("text/html");
	}

	/**
	 * Close button initialized
	 */
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

	/**
	 * Minimize button is initialized
	 */
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
				frame.setState(Frame.ICONIFIED);
			}
		});
	}
	
	/**
	 * If enter is pressed, process command, else
	 * Stores previous commands entered by user, navigated using up and down keys
	 */
	public void processKeyPressed() {
		inputBoxChangeColour();
		commandFromUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						setCommand();
					
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
	
	/**
	 * Gets and sets command to be processed
	 * @ 
	 */
	public void setCommand(){
		String command = commandFromUser.getText();
		commandsEntered.add(command);
		noOfCommandsEntered = commandsEntered.size();
		processCommand(command);
	}

	/**
	 * Executes the command, any error is shown to user. Else,
	 * The relevant view is shown
	 * 
	 */
	private void processCommand(String command)  {
		showToUser.setContentType("text/html");
		try{
		control.executeCommand(command);
		}catch(ArithmeticException | Error e){
			logger.log(Level.WARNING, CLASS_MESSAGE, INVALID_INPUT_MESSAGE);
			JOptionPane.showMessageDialog(null, "Invalid input. Enter \"help\" for assistance.", "Error", JOptionPane.ERROR_MESSAGE);   
		}
		showMessageToUser();
		try {
			showToUser.setText(Display.getInstance().getView().show());
			
		} catch (BadLocationException | IOException e1) {
			logger.log(Level.WARNING, CLASS_MESSAGE, ERROR_DISPLAYING_VIEW_MESSAGE);
		}
			
		
	}

	/**
	 * Navigating previous commands
	 */
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

	/**
	 * Get and show the feedback message to user
	 */
	private void showMessageToUser() {
		StringBuilder htmlMessage = new StringBuilder();
		feedback.setContentType("text/html");
		message = Display.getInstance().getMessage();
		
			if (!message.isEmpty()) {
				htmlMessage.append("<html><p valign=\"top\" align=\"center\"><font face=\"Lucida sans\" color=\"#660000\" size=\"4.5\"><b>"+Display.getInstance().getMessage()+"</b></font></p></html>");
				feedback.setText((htmlMessage.toString()) + "\n");
			}
			else{
				assert message=="";
				feedback.setText(message);
			}
	}

	/**
	 * Add colour to keywords such as by,from and to. For design purposes.
	 */
	private void highlightKeywords() {
		StyleContext style = StyleContext.getDefaultStyleContext();
		final AttributeSet attrRed = style.addAttribute(style.getEmptySet(),
				StyleConstants.Foreground, Color.RED);
		
		final AttributeSet attrBlack = style.addAttribute(style.getEmptySet(),
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
								.matches("(\\W)*(^(add)|^(delete)|^(exit)|^(edit)|^(search)|^(undo)|from(?!.*from)|to|by|^(today|upcoming|someday)|^(help)|^(done)|^(save)|^(home))")) {
							setCharacterAttributes(wordL, wordR - wordL, attrRed,
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
						.matches("(\\W)*(^(add)|^(delete)|^(exit)|^(edit)|^(search)|^(undo)|from(?!.*from)|to|by|^(today|upcoming|someday)|^(help)|^(done)|^(save)|^(home))")) {
					setCharacterAttributes(before, after - before, attrRed, false);
				} else {
					setCharacterAttributes(before, after - before, attrBlack,
							false);
				}
			}

		};
	}

	private int findLastNonWordChar(String text, int index) {
		while (--index >= 0) {
			if (String.valueOf(text.charAt(index)).matches("\\W")) 
			{
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
