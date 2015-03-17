import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextPane;

import java.awt.Font;

import javax.swing.border.EtchedBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JPanel;

public class HomeView {

	private JFrame frame;
	private JTextField commandFromUser;
	private JTextPane showToUser;

	/**
	 * Launch the application here.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeView window = new HomeView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws BadLocationException
	 */
	public HomeView() throws BadLocationException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws BadLocationException
	 */
	private void initialize() throws BadLocationException {
		designOfInterface();
		Controller control = new Controller();
		control.executeCommand("display 1");
		displayView();

		commandFromUser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String command = commandFromUser.getText();

				try{
					control.executeCommand(command);
				} catch(IndexOutOfBoundsException emptyField) {
					JOptionPane.showMessageDialog(null,
							"Command field is empty!");
				}

				try {
					displayView();
				} catch (BadLocationException badlocation) {
					JOptionPane.showMessageDialog(null, "Error Message!");
				}

				commandFromUser.setText("");

			}

		});
	}


	public void displayView() throws BadLocationException {
		// Colouring and styling of text
		String messageToShow = Display.getMessage();
		if(!messageToShow.isEmpty()){
		showToUser.setText("");
		StyledDocument doc = showToUser.getStyledDocument();
		Style style = showToUser.addStyle("Style", null);
		StyleConstants.setForeground(style, Color.BLUE.brighter());
		doc.insertString(doc.getLength(), "Today: \n", style);

		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), Display.getToday(), style);

		StyleConstants.setForeground(style, Color.BLUE.brighter());
		doc.insertString(doc.getLength(), "\nUpcoming: \n", style);
		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), Display.getUpcoming(), style);

		StyleConstants.setForeground(style, Color.BLUE.brighter());
		doc.insertString(doc.getLength(), "\nSomeday: \n", style);
		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), Display.getSomeday(), style);
		}
		
		else{
			JOptionPane.showMessageDialog(null, messageToShow);
		}
	}

	public void designOfInterface() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 628, 464);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		commandFromUser = new JTextField();
		commandFromUser.setFont(new Font("Calibri", Font.PLAIN, 20));
		commandFromUser.setBackground(new Color(255, 255, 255));

		commandFromUser.setBounds(20, 371, 573, 43);
		frame.getContentPane().add(commandFromUser);
		commandFromUser.setColumns(10);
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
