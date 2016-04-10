package game.debug.log.frame;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import game.debug.log.LogMessage;

/**
 * 
 * 
 * @author Julius Häger
 */
public class LogMessageComponent extends JPanel {

	//FIXME: Remove this. This class is a bad adhoc solution
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4347436709380301105L;

	/**
	 * Create the panel.
	 * @param message The message the component should display
	 */
	public LogMessageComponent(LogMessage message) {
		
		setBorder(new CompoundBorder(new EmptyBorder(2, 2, 2, 2), new LineBorder(new Color(64, 64, 64))));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {1, 0, 30, 178, 0};
		gridBagLayout.rowHeights = new int[] {1, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblMessage = new JLabel(message.getMessage());
		lblMessage.setForeground(Color.BLACK);
		GridBagConstraints gbc_lblMessage = new GridBagConstraints();
		gbc_lblMessage.gridwidth = 4;
		gbc_lblMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblMessage.insets = new Insets(0, 0, 5, 5);
		gbc_lblMessage.gridx = 0;
		gbc_lblMessage.gridy = 1;
		add(lblMessage, gbc_lblMessage);
		
		JLabel lblImortance = new JLabel(message.getImportance().toString());
		lblImortance.setForeground(Color.DARK_GRAY);
		GridBagConstraints gbc_lblImortance = new GridBagConstraints();
		gbc_lblImortance.gridwidth = 3;
		gbc_lblImortance.insets = new Insets(0, 0, 5, 0);
		gbc_lblImortance.gridx = 1;
		gbc_lblImortance.gridy = 2;
		add(lblImortance, gbc_lblImortance);
		
		JLabel lblTags = new JLabel("<dynamic>");
		lblTags.setHorizontalAlignment(SwingConstants.CENTER);
		lblTags.setForeground(Color.LIGHT_GRAY);
		if(message.getTagsString().isEmpty()){
			lblTags = new JLabel("None...");
		}else{
			String tags = message.getTagsString();
			tags = tags.replace(";", ", ");
			tags = tags.substring(0, tags.length() - 2);
			lblTags = new JLabel(tags);
		}
		GridBagConstraints gbc_lblTags = new GridBagConstraints();
		gbc_lblTags.gridwidth = 5;
		gbc_lblTags.insets = new Insets(0, 0, 5, 0);
		gbc_lblTags.gridx = 0;
		gbc_lblTags.gridy = 3;
		add(lblTags, gbc_lblTags);

	}

}
