package game.debug;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import game.Game;
import game.controller.event.EventListener;
import game.controller.event.GameEvent;
import game.gameObject.GameObject;
import game.gameObject.handler.event.GameObjectEvent;
import game.util.ID;
import game.util.IDHandler;

/**
 * @author Julius Häger
 * @param <T>
 *
 */
public class IDHandlerDebugFrame<T> extends JFrame implements Runnable {
	
	//TODO: Clean up
	
	//JAVADOC: IDHandlerDebugFrame<T>

	/**
	 * 
	 */
	private static final long serialVersionUID = 6735343486517710772L;
	
	private IDHandler<T> handler;

	private JPanel contentPane;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnRefresh;
	
	private boolean closeRequested = false;

	/**
	 * Create the frame.
	 * 
	 * @param handler
	 */
	public IDHandlerDebugFrame(IDHandler<T> handler) {
		this.handler = handler;

		setTitle("ID Handler Debug Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);

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
		scrollPane.setViewportView(table);

		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateIDs(handler.getAllIDs());
			}
		});
		panel.add(btnRefresh, BorderLayout.NORTH);
		
		updateIDs(handler.getAllIDs());
	}

	/**
	 * 
	 */
	public void stopDebug() {
		closeRequested = true;
		dispose();
	}

	/**
	 * @param ids
	 * 
	 */
	
	private void updateIDs(ID<?>[] ids) {
		Object[][] tableData = new Object[ids.length][3];
		for (int x = 0; x < tableData.length; x++) {
			tableData[x][0] = ids[x].name + (ids[x].object instanceof GameObject ? ((GameObject)ids[x].object).isActive() ? " +" : " -" : "");
			tableData[x][1] = ids[x].id;
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
		table.setModel(model);
	}

	@Override
	public void run() {
		this.setVisible(true);
		
		EventListener listener = new EventListener() {
			@Override
			public <T2 extends GameEvent<?>> void eventFired(T2 event) {
				updateIDs(handler.getAllIDs());
			}
		};
		
		Game.eventMachine.addEventListener(GameObjectEvent.class, listener);
		
		while(!closeRequested){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//TODO: Fix this in a more elegant way
	/**
	 * @param handler
	 */
	public void setHandler(IDHandler<T> handler){
		this.handler = handler;
	}
	
}
