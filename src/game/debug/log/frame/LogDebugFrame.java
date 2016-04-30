package game.debug.log.frame;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import game.debug.log.Log;
import game.debug.log.LogMessage;
import game.debug.log.LogMessage.LogImportance;

/**
 * @author Julius Häger
 *
 */
public class LogDebugFrame extends JFrame implements Runnable{
	
	//TODO: Remove LogMessageComponent to optimize memory

	/**
	 * 
	 */
	private static final long serialVersionUID = -2659511895711944928L;
	
	private JPanel contentPane;
	private JTextField tagFilterTextField;
	
	private Log log;
	private JComboBox<LogImportance> importanceComboBox;

	/**
	 * Create the frame.
	 * @param log the log to display
	 */
	public LogDebugFrame(Log log) {
		this.log = log;
		
		setTitle("Debug log");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 400, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		logMessagePanel = new JPanel();
		scrollPane.setViewportView(logMessagePanel);
		logMessagePanel.setLayout(new BoxLayout(logMessagePanel, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblImportance = new JLabel("Importance Level:");
		GridBagConstraints gbc_lblImportance = new GridBagConstraints();
		gbc_lblImportance.anchor = GridBagConstraints.EAST;
		gbc_lblImportance.insets = new Insets(0, 0, 0, 5);
		gbc_lblImportance.gridx = 0;
		gbc_lblImportance.gridy = 0;
		panel.add(lblImportance, gbc_lblImportance);
		
		importanceComboBox = new JComboBox<LogImportance>();
		
		importanceComboBox.setModel(new DefaultComboBoxModel<LogImportance>(LogImportance.values()));
		importanceComboBox.setSelectedIndex(5);
		GridBagConstraints gbc_importanceComboBox = new GridBagConstraints();
		gbc_importanceComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_importanceComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_importanceComboBox.gridx = 1;
		gbc_importanceComboBox.gridy = 0;
		panel.add(importanceComboBox, gbc_importanceComboBox);
		
		JLabel lblTags = new JLabel("Tags");
		GridBagConstraints gbc_lblTags = new GridBagConstraints();
		gbc_lblTags.anchor = GridBagConstraints.EAST;
		gbc_lblTags.insets = new Insets(0, 0, 0, 5);
		gbc_lblTags.gridx = 2;
		gbc_lblTags.gridy = 0;
		panel.add(lblTags, gbc_lblTags);
		
		tagFilterTextField = new JTextField();
		
		GridBagConstraints gbc_tagFilterTextField = new GridBagConstraints();
		gbc_tagFilterTextField.insets = new Insets(0, 0, 0, 5);
		gbc_tagFilterTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_tagFilterTextField.gridx = 3;
		gbc_tagFilterTextField.gridy = 0;
		panel.add(tagFilterTextField, gbc_tagFilterTextField);
		tagFilterTextField.setColumns(10);
		
		JButton btnFilter = new JButton("Filter");
		
		GridBagConstraints gbc_btnFilter = new GridBagConstraints();
		gbc_btnFilter.gridx = 4;
		gbc_btnFilter.gridy = 0;
		panel.add(btnFilter, gbc_btnFilter);
		
		
		
		JButton btnTestmessage = new JButton("TestMessage");
		
		contentPane.add(btnTestmessage, BorderLayout.SOUTH);
		
		btnTestmessage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				log.logMessage(new LogMessage("TestMessage" + i++, LogImportance.DEBUG, "test"));
				updateMessages();
			}
		});
		
		ActionListener setFilterAndUpdate = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCurrentFilter();
				//updateMessages();
				forceUpdate();
			}
		};
		
		importanceComboBox.addActionListener(setFilterAndUpdate);
		
		tagFilterTextField.addActionListener(setFilterAndUpdate);
		
		btnFilter.addActionListener(setFilterAndUpdate);
		
		setCurrentFilter();
		updateMessages();
	}
	
	//TODO: Remove
	static int i = 0;

	private CopyOnWriteArrayList<LogMessage> messages = new CopyOnWriteArrayList<>();
	private JPanel logMessagePanel;
	
	private boolean closeRequested = false;
	
	@Override
	public void run() {
		this.setVisible(true);
		
		//TODO: Make this event driven instead
		while(!closeRequested){
			updateMessages();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setCurrentFilter(){
		if(tagFilterTextField.getText() == null || tagFilterTextField.getText().isEmpty()){
			currentTagFilter = new String[]{};
		}else{
			currentTagFilter = tagFilterTextField.getText().split(";");
		}
		
		currentImpotrance = (LogImportance)importanceComboBox.getSelectedItem();
	}
	
	private int oldMessages = 0;
	private int newMessages = 0;
	
	private LogImportance currentImpotrance;
	private String[] currentTagFilter;
	private JScrollPane scrollPane;
	
	private LogMessageComponent lastMessage;
	
	/**
	 * Updates to see if any new messages has been added to the log.
	 */
	public void updateMessages(){
		if(currentTagFilter.length <= 0){
			messages = log.getMessages(currentImpotrance);
		}else{
			messages = log.getMessages(currentImpotrance, currentTagFilter);
		}
		
		newMessages = messages.size();
		
		if(oldMessages < newMessages){
			for (int i = oldMessages; i < messages.size(); i++) {
				lastMessage = new LogMessageComponent(messages.get(i));
				logMessagePanel.add(lastMessage);
			}
					
			logMessagePanel.revalidate();
			
			JScrollBar scroll = scrollPane.getVerticalScrollBar();
			
			scroll.setValue(scroll.getMaximum() - scroll.getModel().getExtent());
			
			scrollPane.revalidate();
			scrollPane.repaint();	
			
			oldMessages = newMessages;
			
		}else if(oldMessages > newMessages){
			logMessagePanel.removeAll();
			
			for (LogMessage logMessage : messages) {
				lastMessage = new LogMessageComponent(logMessage);
				logMessagePanel.add(lastMessage);
			}
			
			logMessagePanel.revalidate();
			
			JScrollBar scroll = scrollPane.getVerticalScrollBar();
			
			scroll.setValue(scroll.getMaximum() - scroll.getModel().getExtent());
			
			scrollPane.revalidate();
			scrollPane.repaint();
			
			oldMessages = newMessages;
		}
	}
	
	/**
	 * Forces a update to all messages by removing all messages and recreating them.
	 */
	public void forceUpdate(){
		if(currentTagFilter.length <= 0){
			messages = log.getMessages(currentImpotrance);
		}else{
			messages = log.getMessages(currentImpotrance, currentTagFilter);
		}
		
		newMessages = messages.size();
		
		logMessagePanel.removeAll();
		
		for (LogMessage logMessage : messages) {
			lastMessage = new LogMessageComponent(logMessage);
			logMessagePanel.add(lastMessage);
		}
		
		logMessagePanel.revalidate();
		
		JScrollBar scroll = scrollPane.getVerticalScrollBar();
		
		scroll.setValue(scroll.getMaximum() - scroll.getModel().getExtent());
		
		scrollPane.revalidate();
		scrollPane.repaint();
		
		oldMessages = newMessages;
	}
	
	/**
	 * Stops the debugger.
	 */
	public void stopDebug(){
		closeRequested = true;
		dispose();
	}
}
