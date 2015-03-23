import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class UserInterface {

	private JFrame frame;
	private JTextPane commandFromUser;
	private JTextPane showToUser;
	private Display display = Display.getInstanceOfDisplay();
	private Controller control;
	private JPanel panel;
	private DefaultStyledDocument doc = new DefaultStyledDocument();

	/**
	 * Launch the application.
	 */
	private static UserInterface UI = new UserInterface();

	public static UserInterface getInstanceOfDisplay() {
		if (UI == null) {
			UI = new UserInterface();
		}
		return UI;

	}

	public static void main(String[] args) {
		UserInterface window = new UserInterface();
		window.frame.setVisible(true);
	}

	/**
	 * Create the application.
	 * 
	 * @throws BadLocationException
	 */
	public UserInterface() {
		initialize();
		control = new Controller();
		// control.executeCommand("display 1");
		HomeView hm = new HomeView();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 500);
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 612, 425);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(30,
				144, 255), new Color(0, 0, 0)));
		colourCommand();
		commandFromUser = new JTextPane(doc);
		panel.add(commandFromUser, BorderLayout.SOUTH);
		commandFromUser.setFont(new Font("Calibri", Font.PLAIN, 20));
		commandFromUser.setBackground(new Color(255, 255, 255));
		commandFromUser.setBounds(20, 371, 573, 43);
		commandFromUser.setForeground(new Color(0, 0, 0));
		commandFromUser.setBorder(new EtchedBorder(EtchedBorder.LOWERED,
				new Color(30, 144, 255), new Color(0, 0, 0)));
		showToUser = new JTextPane();
		panel.add(showToUser, BorderLayout.CENTER);
		showToUser.setFont(new Font("Calibri", Font.PLAIN, 20));
		showToUser.setBackground(new Color(255, 255, 255));
		showToUser.setEditable(false);
		showToUser.setForeground(new Color(0, 0, 128));
		showToUser.setBorder(null);
		showToUser.setBounds(20, 10, 573, 350);
		getCommand();
	}

	private void getCommand() {
		commandFromUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String command = commandFromUser.getText();
					//control.executeCommand(command);
					try {
						processCommand(command);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					e.consume();
					commandFromUser.setText("");
				}
			}
		});
	}
	
	private void processCommand(String command) throws BadLocationException{
		String[] getCommand = command.split(" ");
		switch (getCommand[0].toLowerCase()) {
		case "home":
			ViewHome();
			break;

		case "today":
			ViewToday();
			break;

		case "upcoming":
		//	ViewUpcoming();
			break;
			
		case "someday":
		//	ViewSomeday();
			break;

		case "search":
			//ViewSearch();
			break;

		case "exit":
			//exitProgram();

		default:
			JOptionPane.showMessageDialog (null, "Please enter a valid command!", "Error message", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void ViewHome() throws BadLocationException {
		// TODO Auto-generated method stub
		StyledDocument doc = showToUser.getStyledDocument();
		Style style = showToUser.addStyle("Style", null);
		StyleConstants.setForeground(style, Color.BLUE.brighter());
		doc.insertString(doc.getLength(), " Today: \n", style);

		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), display.getToday(), style);

		StyleConstants.setForeground(style, Color.BLUE.brighter());
		doc.insertString(doc.getLength(), "\n Upcoming: \n", style);
		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), display.getUpcoming(), style);

		StyleConstants.setForeground(style, Color.BLUE.brighter());
		doc.insertString(doc.getLength(), "\n Someday: \n", style);
		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), display.getSomeday(), style);

	}

	private void ViewToday() throws BadLocationException {
		// TODO Auto-generated method stub
		StyledDocument doc = showToUser.getStyledDocument();
		Style style = showToUser.addStyle("Style", null);
		StyleConstants.setForeground(style, Color.BLUE.brighter());
		doc.insertString(doc.getLength(), " Today: \n", style);

		StyleConstants.setForeground(style, Color.BLACK);
		//doc.insertString(doc.getLength(), display.getToday(), style);
		
	}

	private void colourCommand() {
		StyleContext cont = StyleContext.getDefaultStyleContext();
		AttributeSet attr = cont.addAttribute(cont.getEmptySet(),
				StyleConstants.Foreground, Color.RED);
		AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(),
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
								.matches(
										"(\\W)*(add|delete|edit|search|from|to|by)"))
							setCharacterAttributes(wordL, wordR - wordL, attr,
									false);
						else
							setCharacterAttributes(wordL, wordR - wordL,
									attrBlack, false);
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
						.matches("(\\W)*(add|delete|edit|search|from|to|by)")) {
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