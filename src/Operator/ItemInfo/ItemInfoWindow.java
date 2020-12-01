package Operator.ItemInfo;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JButton;

public class ItemInfoWindow extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public ItemInfoWindow() {
		setBounds(100, 100, 402, 490);
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 74, 378, 56);
		add(panel);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("판매이름");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("수정");
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(btnNewButton);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
		
		JLabel label = new JLabel("판매가격");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(0.5f);
		panel_3.add(label);
		
		JButton button = new JButton("수정");
		button.setAlignmentX(0.5f);
		panel_3.add(button);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		JLabel label_1 = new JLabel("재고");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setAlignmentX(0.5f);
		panel_4.add(label_1);
		
		JButton button_1 = new JButton("보충");
		button_1.setAlignmentX(0.5f);
		panel_4.add(button_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 178, 378, 302);
		add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
	}

}
