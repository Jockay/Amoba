package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import controller.AmobaDAO;
import controller.AmobaServices;
import model.Coordinate;
import model.Game;

/**
 * Amoba (Go-Moku) game main class.                                          
 *	
 * @version 	1.1		12 jul 2014 
 * @author 		Jockay
 *
 */

public class AmobaGUI extends JFrame {
	
	/**
	 * A button in the game field.
	 * 
	 * @author 		Jockay
	 *
	 */
	class AmobaButton extends JButton {
		/** Serial version identifier. */
		private static final long serialVersionUID = 1L;
		/** Contains the button state about clicked before. */
		private boolean clicked;
		/** Coordinate of the button on the game field. */
		private Coordinate coordinate;
		
		/**
		 * Class constructor.
		 * 
		 * @param text Name of the button.
		 */
		public AmobaButton(String text) {
			super(text);
			this.clicked = false;
		}
		
		/**
		 * Coordinate of the game button on the game field.
		 * 
		 * @return Game button coordinate.
		 */
		public Coordinate getCoordinate() {
			return coordinate;
		}
		
		/**
		 * Sets the coordinate of the game button.
		 * 
		 * @param place Coordinate to set for the button.
		 */
		public void setCoordinate(Coordinate place) {
			this.coordinate = place;
		}
		
		/**
		 * Decides if the button is clicked.
		 * 
		 * @return True if the button clicked, else returns false.
		 */
		public boolean isClicked() {
			return clicked;
		}
		
		/**
		 * Modifies the clicked state of the button.
		 * 
		 * @param isClicked State to set.
		 */
		public void setClicked(boolean isClicked) {
			this.clicked = isClicked;
		}
		
		/**
		 * Returns the game button.
		 * 
		 * @return Itself.
		 */
		public AmobaButton getItself() {
			return this;
		}

	}
	
	/** List of game field buttons. */
	public List<AmobaButton> buttons;
	/** Serial version identifier. */
	private static final long serialVersionUID = 1L;
	/** Contains elements of the frame. */
	private static JPanel contentPane;
	/** Notification label for the game play. */
	private static JLabel notification;
	/** Contains database services. */
	private AmobaDAO amobaDAO;
	/** Contains game services. */
	private AmobaServices amobaServices;
	/** Size of buttons on the game field. */
	public static int buttonSize = 36;
	/** Game field distance from the frame border. */
	private static int offset = 10;
	/** Game state. */
	private Game game;

	/**
	 * Create the frame.
	 */
	public AmobaGUI() {
		this.game = new Game();
		this.amobaServices = new AmobaServices();
		this.amobaDAO = new AmobaDAO();
		this.buttons = new ArrayList<AmobaButton>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, ((buttonSize * 13) + (offset * 2)),
				((buttonSize * 13) + (offset * 2))); 
				
		/* Menubar begin */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);

		JMenuItem mntmNewGame = new JMenuItem("New Game");
		mntmNewGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				getGame().newGame();
				updateButtonIcons();
				updateNotification();
				setTitle("Amőba");
			}
		});
		mnGame.add(mntmNewGame);

		JMenuItem mntmSendResultTo = new JMenuItem("Send Result to database");
		mntmSendResultTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					getAmobaDAO().uploadGameState(game);
				} catch (SQLException exception) {
					exception.printStackTrace();
				}
			}
		});
		mnGame.add(mntmSendResultTo);

		JMenuItem saveToXML = new JMenuItem("Save to XML");
		saveToXML.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				getAmobaServices().SaveToXML(getGame());                                      
			}
		});
		mnGame.add(saveToXML);

		

		JMenuItem mntmSetNames = new JMenuItem("Set Names");               
		mntmSetNames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SetNames(getGUI(), game);
			}
		});
		mnGame.add(mntmSetNames);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
		mnGame.add(mntmExit);

		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);

		JMenuItem mntmAbout = new JMenuItem("Game");
		mntmAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new About(getGUI());
			}
		});
		mnAbout.add(mntmAbout);
		/* Menubar end */
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(50, 50, 50, 50));
		contentPane.setBounds(10, 10, ((buttonSize * 13) + (offset * 2)),
				((buttonSize * 13) + (offset * 2)) + 50);
		contentPane.setBackground(Color.BLACK);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel(new GridLayout(13, 13, 1, 1));
		panel.setBounds(10, 10, ((buttonSize * 13) + (offset * 2)) + 75,
				((buttonSize * 13) + (offset * 2)));
		panel.setBackground(Color.LIGHT_GRAY);
		for (int i = 0; i < 13; i++)
			for (int j = 0; j < 13; ++j) {
				final int idx = i;
				final int jdx = j;
				final Coordinate coordinate = new Coordinate(idx, jdx);
				
				final AmobaButton btn = new AmobaButton("");
				btn.setCoordinate(new Coordinate(idx, jdx));
				btn.setPreferredSize(new Dimension(10, 10));
				btn.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						if (getGame().isGameOver()) {
							return;
						}
						
						int checkValue = 0;
						if(getGame().putSign(coordinate)) {
							checkValue = getGame().checkBoard().size();
							if (checkValue == 5) {
								getGame().gameIsOver();
								getGame().actualPlayer().setWon(true);
								setWinnerButtons(getGame().checkBoard());
							} else { 
								getGame().switchPlayer();
							}
						}
						
						if(checkValue != 5) {
							if (getLastClicked() == null) {
								btn.setClicked(true);
							} else {
								clearLastClicked();
								btn.setClicked(true);
							}
						}
						
						updateButtonIcons();
						updateNotification();
					}
				});
				buttons.add(btn);
				panel.add(btn);
				updateButtonIcons();
			}
		contentPane.add(panel);

		notification = new JLabel(getGame().actualPlayer().getName()
				+ " starts the game!");
		notification.setFont(new Font("Tahoma", Font.BOLD, 13));
		notification.setHorizontalAlignment(SwingConstants.CENTER);
		notification.setForeground(Color.LIGHT_GRAY);
		notification.setBounds(offset, (buttonSize * 13 + offset * 2)
				+ buttonSize / 2, ((buttonSize * 14) + offset * 2) + buttonSize
				/ 2, 15);
		contentPane.add(notification);
		contentPane.updateUI();
	}
	
	/**
	 * Returns the game frame.
	 * 
	 * @return The game frame.
	 */
	public AmobaGUI getGUI() {
		return this;
	}
	
	/**
	 * Returns the AmobaDAO object.
	 * 
	 * @return AmobaDAO object.
	 */
	public AmobaDAO getAmobaDAO() {
		return this.amobaDAO;
	}
	
	/**
	 * Returns the AmobaServices object.
	 * 
	 * @return AmobaServices object.
	 */
	public AmobaServices getAmobaServices() {
		return this.amobaServices;
	}
	
	/**
	 * Returns the game state.
	 * 
	 * @return Game state.
	 */
	public Game getGame() {
		return this.game;
	}
	
	/**
	 * Returns a list of buttons.
	 * 
	 * @return List of buttons.
	 */
	public List<AmobaButton> getButtons() {
		return this.buttons;
	}
	
	/**
	 * Sets five buttons clicked using a list of coordinates. 
	 * 
	 * @param coords List of winner sign coordinates.
	 */
	public void setWinnerButtons(List<Coordinate> coords) {
		clearLastClicked();
		for (Coordinate c : coords)
			for (int i = 0; i < getButtons().size(); i++)
				if (getButtons().get(i).getCoordinate().equals(c))
					getButtons().get(i).setClicked(true);
	}
	
	/**
	 * Sets every button free from clicked state.
	 */
	public void clearLastClicked() {
		for (AmobaButton ab : getButtons())
			if (ab.isClicked())
				ab.setClicked(false);
	}

	/**
	 * Returns the last clicked button.
	 * 
	 * @return The last clicked button.
	 */
	public AmobaButton getLastClicked() {
		for (AmobaButton ab : getButtons())
			if (ab.isClicked())
				return ab;
		return null;
	}
	
	/**
	 * Refresh the user interface.
	 */
	public void updateGUI() {
		updateButtonIcons();
		updateNotification();
		frame.setSize((buttonSize * 16) + offset, (buttonSize * 16)
				+ offset);
		notification.setBounds(offset, (buttonSize * 13 + offset * 2)
				+ buttonSize / 2, ((buttonSize * 14) + offset * 2) + buttonSize
				/ 2, 15);
		contentPane.updateUI();
	}
	
	/**
	 * Refreshes every button icon.
	 */
	public void updateButtonIcons() {
		for (AmobaButton amobaButton : buttons)	{
			int value = getGame().getBoardValue(amobaButton.getCoordinate());
			if (value == 0) {
				amobaButton.setIcon(
						new ImageIcon(getClass().getClassLoader().getResource(
								"Empty.png")));
			} else if (value == 1) {
				if (amobaButton.isClicked()) {
					amobaButton.setIcon(
							new ImageIcon(getClass().getClassLoader()
									.getResource("O-highlight.png")));
				} else {
					amobaButton.setIcon(
							new ImageIcon(getClass().getClassLoader()
									.getResource("O.png")));
				}
			} else if (value == 2) {
				if (amobaButton.isClicked()) {
					amobaButton.setIcon(
							new ImageIcon(getClass().getClassLoader()
									.getResource("X-highlight.png")));
				} else {
					amobaButton.setIcon(
							new ImageIcon(getClass().getClassLoader()
									.getResource("X.png")));
				}
			}
		}
	}

	/**
	 * Refreshes notification label.
	 */
	public void updateNotification() {
		if (getGame().isGameOver()) {
			setTitle("Amőba - Game Over");
			notification.setText(getGame().actualPlayer().getName() + " wins!");
		} else if (getGame().checkBoard().size() == 1) {
			notification.setText("Drawn Game");
		} else {
			if (getGame().isEmptyBoard())
				notification.setText(getGame().actualPlayer().getName()
						+ " starts the game!");
			else
				notification
						.setText(getGame().actualPlayer().getName() + "'s ("
								+ (getGame().actualPlayer().isStarter() ? "O" : "X")
								+ ") turn");
		}
	}
	
	/**
	 * Changes the frame look and feel.
	 * 
	 * @param lafName Name of the look and feel theme.
	 */
	public static void setLAF(String lafName) {
		try {
			LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
			for (LookAndFeelInfo laf : lafs)
				if (lafName.equals(laf.getName())) {
					UIManager.setLookAndFeel(laf.getClassName());
				}
		} catch (Exception e) {
			System.out.println("Error setting" + lafName + "LAF: " + e);
		}
	}
	
	/**
	 * Game frame.
	 */
	public static AmobaGUI frame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// setLAF("Nimbus");
					// setLAF("CDE/Motif");
					AmobaGUI frame = new AmobaGUI();
					frame.setResizable(false);
					frame.setLocation(150, 50);
					frame.setDefaultCloseOperation(
							WindowConstants.EXIT_ON_CLOSE);
					// frame.setSize(570,570);
					frame.setSize((buttonSize * 16) + offset, (buttonSize * 16)
							+ offset);
					frame.setVisible(true);
					frame.setTitle("Amőba");
					// frame.pack();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
