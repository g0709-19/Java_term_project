package Operator.ItemInfo;

import javax.swing.JPanel;

import Main.Beverage;

public class BeverageLabel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private Beverage beverage;
	
	public BeverageLabel(Beverage beverage) {
		
		this.beverage = beverage;
		
		String b_name = beverage.getType();
		String b_price = String.valueOf(beverage.getPrice());
		String b_amount = String.valueOf(beverage.getAmount());
		
		Option name = new Option(b_name, "����");
		Option price = new Option(b_price, "����");
		Option amount = new Option(b_amount, "����");
		
		add(name);
		add(price);
		add(amount);
	}
	
}
