package Operator.ItemInfo;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Option extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private String option;
	private JLabel label;
	private JButton button;
	
	public Option(String option, String button) {
		
		this.option = option;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		label = new JLabel(option);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(0.5f);
		add(label);
		
		this.button = new JButton(button);
		this.button.setAlignmentX(0.5f);
		add(this.button);
	}
	
	public String getOption() {
		return option;
	}
	
	public void setOption(String option) {
		this.option = option;
		setLabel(option);
	}
	
	public JLabel getLabel() {
		return label;
	}
	
	private void setLabel(String label) {
		this.label.setText(label);
	}
	
	public JButton getButton() {
		return button;
	}
}