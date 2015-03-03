import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.border.EtchedBorder;

public class SimpleGui {

	private JFrame frame;
	private JTextField commandFromUser;
	private JTextPane showToUser;

	/**
	 * Launch the application here.
	 */
	public static void main(String[] args) {
		//initialize Controller
		new Controller();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimpleGui window = new SimpleGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SimpleGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(204, 255, 255));
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

		showToUser.setText(Controller.display());
		
		commandFromUser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String command = commandFromUser.getText();
				String showText = Controller.executeCommand(command);
				showToUser.setText(showText);
				commandFromUser.setText("");

			}
		});
	}
}
