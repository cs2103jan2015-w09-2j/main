//@author A0112715R

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import java.awt.Font;

import javax.swing.border.EtchedBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.JPanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class HomeView {

	private JFrame frame;
	private JTextPane commandFromUser;
	private JTextPane showToUser;
	private Display display = Display.getInstanceOfDisplay();
	private Controller control;

	/**
	 * Launch the application here.
	 * 
	 * @throws BadLocationException
	 */
	public static void main(String[] args) throws BadLocationException {
		HomeView window = new HomeView();
		window.frame.setVisible(true);

	}

	private HomeView() throws BadLocationException {
		initialize();
	}

	private void initialize() throws BadLocationException {
		designOfInterface();
		control = new Controller();
		control.executeCommand("display 1");
		displayHomeView();
	}

	private void displayHomeView() throws BadLocationException {
		// Colouring and styling of text
		showToUser.setText("");
		StyledDocument doc = showToUser.getStyledDocument();
		Style style = showToUser.addStyle("Style", null);
		StyleConstants.setForeground(style, Color.BLUE.brighter());
		doc.insertString(doc.getLength(), "Today: \n", style);

		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), display.getToday(), style);

		StyleConstants.setForeground(style, Color.BLUE.brighter());
		doc.insertString(doc.getLength(), "\nUpcoming: \n", style);
		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), display.getUpcoming(), style);

		StyleConstants.setForeground(style, Color.BLUE.brighter());
		doc.insertString(doc.getLength(), "\nSomeday: \n", style);
		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), display.getSomeday(), style);

	}

	private void designOfInterface() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 628, 464);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		commandFromUser = new JTextPane();
		commandFromUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String command = commandFromUser.getText();
					control.executeCommand(command);

					try {
						displayHomeView();
					} catch (BadLocationException badlocation) {
						JOptionPane.showMessageDialog(null, "Error Message!");
					}
					commandFromUser.setText("");
				}
			}
		});
		commandFromUser.setFont(new Font("Calibri", Font.PLAIN, 20));
		commandFromUser.setBackground(new Color(255, 255, 255));

		commandFromUser.setBounds(20, 371, 573, 43);
		frame.getContentPane().add(commandFromUser);
		showToUser = new JTextPane();
		showToUser.setFont(new Font("Calibri", Font.PLAIN, 20));
		showToUser.setBackground(new Color(255, 255, 255));
		showToUser.setEditable(false);
		showToUser.setForeground(new Color(0, 0, 128));
		showToUser.setBorder(null);
		showToUser.setBounds(20, 10, 573, 350);
		frame.getContentPane().add(showToUser);
		commandFromUser.setForeground(new Color(0, 0, 0));
		commandFromUser.setBorder(new EtchedBorder(EtchedBorder.LOWERED,
				new Color(30, 144, 255), new Color(0, 0, 0)));

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 612, 425);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(30,
				144, 255), new Color(0, 0, 0)));
		frame.getContentPane().add(panel);
	}

	
}
