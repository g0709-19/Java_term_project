package Main;

import javax.swing.ImageIcon;

public class Beverage {
	
	private final ImageIcon icon;
	private String type;
	private int price;
	private int amount;
	
	private static final int BEVERAGE_AMOUNT = 5;
	
	public Beverage(ImageIcon icon, String type, int price) {
		this(icon, type, price, BEVERAGE_AMOUNT);
	}
	
	private Beverage(ImageIcon icon, String type, int price, int amount) {
		this.icon = icon;
		this.type = type;
		this.price= price;
		this.amount = amount;
	}
	
	// 재고 보충. 변경된 재고를 반환
	public int addItem(int amount) {
		this.amount += amount;
		return this.amount;
	}
	
	// 재고가 없으면 false
	public boolean isExist() {
		boolean is_exist = amount > 0;
		return is_exist;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getPrice() {
		return price;
	}
	
	public ImageIcon getIcon() {
		return icon;
	}
}
