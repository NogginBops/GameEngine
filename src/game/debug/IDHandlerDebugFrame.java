package game.debug;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import game.Game;
import game.controller.event.GameEvent;
import game.controller.event.engineEvents.GameQuitEvent;
import game.gameObject.GameObject;
import game.util.ID;
import game.util.IDHandler;

/**
 * @author Julius Häger
 * @param <T>
 *
 */
public class IDHandlerDebugFrame<T> extends JFrame implements Runnable {

	// TODO: Clean up
	
	//TODO: Either find a way to not replace the model but only add and remove the objects directly through the table or
	// find a way to retain the selection when updating the table model.

	// JAVADOC: IDHandlerDebugFrame<T>

	/**
	 * 
	 */
	private static final long serialVersionUID = 6735343486517710772L;

	private IDHandler<T> handler;
	
	private int width = 500;
	private int height = 500;
	
	private int refreshDelay = 200;

	private JPanel contentPane;
	private JSplitPane splitPane;
	private JPanel lowerPanel;
	private JLabel debugOutput;
	private JScrollPane scrollPane;
	private JScrollPane outputScrollPane;
	private JTable table;
	private JButton btnRefresh;
	private Timer debugOutputRefreshTimer;
	
	private ID<T>[] ids;

	private ListSelectionListener selectionListener;
	
	private ID<T> selectedProvider = null;
	

	/**
	 * Create the frame.
	 * 
	 * @param handler
	 * @param updateEvents 
	 */
	@SafeVarargs
	public IDHandlerDebugFrame(IDHandler<T> handler, Class<? extends GameEvent> ... updateEvents) {
		this.handler = handler;
		
		for (Class<? extends GameEvent> event : updateEvents) {
			Game.eventMachine.addEventListener(event, (e) -> updateIDs.run());
		}

		setTitle("ID Handler Debug Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		scrollPane = new JScrollPane();
		
		outputScrollPane = new JScrollPane();
		//outputScrollPane.setLayout(new BorderLayout(0, 0));
		
		debugOutput = new JLabel("Select a gameobject to see debug info");
		debugOutput.setFont(Font.getFont("Sanserif"));

		lowerPanel = new JPanel();
		lowerPanel.setBorder(new LineBorder(Color.lightGray));
		lowerPanel.setLayout(new BorderLayout(0, 0));
		lowerPanel.add(outputScrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		
		outputScrollPane.setViewportView(panel);
		
		panel.add(debugOutput, BorderLayout.NORTH);
		
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, lowerPanel);
		contentPane.add(splitPane, BorderLayout.CENTER);
		splitPane.setDividerLocation((height/10) * 5);

		table = new JTable();
		table.setModel(
				new DefaultTableModel(new Object[][] { { null, null, null }, }, new String[] { "Name", "ID", "Type" }) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 5104580526478729182L;
					@SuppressWarnings("rawtypes")
					Class[] columnTypes = new Class[] { String.class, Integer.class, String.class };

					@SuppressWarnings({ "unchecked", "rawtypes" })
					@Override
					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
				});
		table.setAutoCreateRowSorter(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectionListener = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false && table.getSelectedRow() != -1){
					selectedProvider = ids[table.getSelectedRow()];
					if(selectedProvider.object instanceof DebugOutputProvider){
						String text = "<html>";
						text += "<b><u>" + selectedProvider.name + "</u></b> - " + selectedProvider.id + "<br>";
						for (String line : ((DebugOutputProvider)selectedProvider.object).getDebugValues()) {
							text += line + "<br>";
						}
						text += "</html>";
						debugOutput.setText(text);
						
						outputScrollPane.setPreferredSize(debugOutput.getPreferredSize());
					}else{
						debugOutput.setText("<html>" + ids[table.getSelectedRow()].object.getClass() + " does not support debug print outs.<br>For debug printouts to work the object needs to implement DebugOutputProvider!</html>");
					}
				}
			}
		};
		
		table.getSelectionModel().addListSelectionListener(selectionListener);
		scrollPane.setViewportView(table);
		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					updateIDs(handler.getAllIDs());
				});
			}
		});
		contentPane.add(btnRefresh, BorderLayout.NORTH);

		SwingUtilities.invokeLater(() -> {
			updateIDs(handler.getAllIDs());
		});
		
		debugOutputRefreshTimer = new Timer(refreshDelay, new ActionListener() {
			//TODO: This is duplicate code. Extract to variable.
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedProvider != null){
					if(selectedProvider.object instanceof DebugOutputProvider){
						String text = "<html>";
						text += "<b><u>" + selectedProvider.name + "</u></b> - " + selectedProvider.id + "<br>";
						for (String line : ((DebugOutputProvider)selectedProvider.object).getDebugValues()) {
							text += line + "<br>";
						}
						text += "</html>";
						debugOutput.setText(text);
					}else{
						debugOutput.setText("<html>" + selectedProvider.object.getClass() + " does not support debug print outs.<br>For debug printouts to work the object needs to implement DebugOutputProvider!</html>");
					}
				}
				/*
				if(table.getSelectedRow() != -1){
					if(ids[table.getSelectedRow()].object instanceof DebugOutputProvider){
						String text = "<html>";
						text += "<b><u>" + ids[table.getSelectedRow()].name + "</u></b> - " + ids[table.getSelectedRow()].id + "<br>";
						for (String line : ((DebugOutputProvider)ids[table.getSelectedRow()].object).getDebugValues()) {
							text += line + "<br>";
						}
						text += "</html>";
						debugOutput.setText(text);
					}else{
						debugOutput.setText("<html>" + ids[table.getSelectedRow()].object.getClass() + " does not support debug print outs.<br>For debug printouts to work the object needs to implement DebugOutputProvider!</html>");
					}
				}
				*/
			}
		});
		
		debugOutputRefreshTimer.start();
	}
	
	/**
	 * @param ids
	 * 
	 */
	private void updateIDs(ID<T>[] ids) {

		Object[][] tableData = new Object[ids.length][3];

		for (int x = 0; x < tableData.length; x++) {
			tableData[x][0] = ids[x].name + (ids[x].object instanceof GameObject
					? ((GameObject) ids[x].object).isActive() ? " +" : " -" : "");
			tableData[x][1] = Integer.valueOf(ids[x].id);
			tableData[x][2] = ids[x].object.getClass().getName();
		}

		DefaultTableModel model = new DefaultTableModel(tableData, new String[] { "Name", "ID", "Type" }) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 650289327416122528L;

			Class<?>[] columnTypes = new Class[] { String.class, Integer.class, String.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
		};

		SwingUtilities.invokeLater(() -> {
			if (table != null) table.setModel(model);
			this.ids = ids;
		});
	}
	
	Runnable updateIDs = () -> updateIDs(handler.getAllIDs());
	
	@Override
	public void run() {
		this.setVisible(true);
		
		Game.eventMachine.addEventListener(GameQuitEvent.class, (event) -> { stopDebug(); });
	}
	
	/**
	 * 
	 */
	public void stopDebug() {
		dispose();
	}

	// TODO: Fix this in a more elegant way
	/**
	 * @param handler
	 */
	public void setHandler(IDHandler<T> handler) {
		this.handler = handler;
	}
}
