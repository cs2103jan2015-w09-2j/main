import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
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

public class HomeView{

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
	 * @throws BadLocationException 
	 */
	public HomeView() throws BadLocationException {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 * @throws BadLocationException 
	 */
	private void initialize() throws BadLocationException {
		frame = new JFrame();	frame.getContentPane().setBackground(new Color(176, 224, 230));
		frame.setBounds(100, 100, 628, 464);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		commandFromUser = new JTextField();
		commandFromUser.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 15));
		commandFromUser.setBackground(new Color(255, 255, 255));

		commandFromUser.setBounds(20, 381, 573, 33);
		frame.getContentPane().add(commandFromUser);
		commandFromUser.setColumns(10);
		showToUser = new JTextPane();
		showToUser.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 16));
		showToUser.setBackground(new Color(255, 255, 255));
		showToUser.setEditable(false);
		showToUser.setForeground(new Color(0, 0, 128));
		showToUser.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(0, 204, 204), new Color(0, 0, 0)));
		showToUser.setBounds(20, 10, 573, 350);
		frame.getContentPane().add(showToUser);
		commandFromUser.setForeground(new Color(0, 51, 153));
		commandFromUser.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(0, 204, 204), new Color(0, 0, 0)));
		
		//Colouring and styling of text
		StyledDocument doc = showToUser.getStyledDocument();
		Style style = showToUser.addStyle("Style", null);
		StyleConstants.setForeground(style, Color.BLUE.darker());
		doc.insertString(doc.getLength(), "Today: \n", style);
		
		Controller control = new Controller();
		control.executeCommand("display 1");		
		StyleConstants.setForeground(style, Color.ORANGE.darker());
		doc.insertString(doc.getLength(), Display.getToday(),style);
		
		StyleConstants.setForeground(style, Color.BLUE.darker());
		doc.insertString(doc.getLength(), "\nUpcoming: \n", style);
		StyleConstants.setForeground(style, Color.ORANGE.darker());
		doc.insertString(doc.getLength(), Display.getUpcoming(),style);
		
		StyleConstants.setForeground(style, Color.BLUE.darker());
		doc.insertString(doc.getLength(), "\nSomeday: \n", style);
		StyleConstants.setForeground(style,Color.ORANGE.darker());
		doc.insertString(doc.getLength(), Display.getSomeday(),style);
		
		
		commandFromUser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String command = commandFromUser.getText();
				control.executeCommand(command);
				String showText = "";//Display.getTasks();
				showToUser.setText(showText);
				commandFromUser.setText("");


			}
		});
	}
	
	
}
