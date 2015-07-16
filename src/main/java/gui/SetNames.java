package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Game;

/**
 * Name setter window frame class.
 * 
 * @author Jockay
 *
 */
public class SetNames extends JFrame {
	
	/** Serial version identifier. */
	private static final long serialVersionUID = -5391294160109000288L;
	/** Contains elements of the frame. */
	private JPanel contentPane;
	/** Text field to modify the name of player 1. */
	private JTextField textField_1;
	/** Text field to modify the name of player 2. */
	private JTextField textField_2;
	
	/**
	 * Create the frame.
	 * 
	 * @param agui Game frame.
	 * @param gameState Game state.
	 */
	public SetNames(final AmobaGUI agui, final Game gameState) {
		setTitle("Settings");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 304, 159);
		setLocation((int)agui.getWidth()/2, (int)agui.getHeight()/2 - 40);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.BLACK);
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		
		JLabel lblPlayerName_2 = new JLabel("Player 1 name:");
		lblPlayerName_2.setForeground(Color.LIGHT_GRAY);
		lblPlayerName_2.setBounds(23, 24, 116, 15);
		contentPane.add(lblPlayerName_2);
		
		JLabel lblNewLabel = new JLabel("Player 2 name:");
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setBounds(23, 51, 105, 15);
		contentPane.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(142, 22, 114, 19);
		textField_1.setText(gameState.getP1().getName());
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(142, 49, 114, 19);
		textField_2.setText(gameState.getP2().getName());
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnCancel.setBounds(23, 96, 117, 25);
		contentPane.add(btnCancel);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameState.getP1().setName(textField_1.getText());
				gameState.getP2().setName(textField_2.getText());
				agui.updateNotification();
				setVisible(false);
			}
		});
		btnOk.setBounds(173, 96, 117, 25);
		contentPane.add(btnOk);

		
		setResizable(false);
		setVisible(true);
	}
}
