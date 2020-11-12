package Main;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class Beverage {
	
	private final ImageIcon icon;
	private String type;
	private int price;
	private int amount;
	
	private static final int BEVERAGE_AMOUNT = 5;
	
	public Beverage(String path, String type, int price) {
		this(path, type, price, BEVERAGE_AMOUNT);
	}
	
	private Beverage(String path, String type, int price, int amount) {
		this.icon = getImageIcon(path);
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
	
	private ImageIcon getImageIcon(String path) {
		path = String.format("../images/%s", path);
		URL url = Main.class.getResource(path);
		
		ImageIcon icon = new ImageIcon(url);
		Image img = icon.getImage();
		img = img.getScaledInstance(Main.PRODUCT_IMAGE_WIDTH, Main.PRODUCT_IMAGE_HEIGHT, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		
		return icon;
	}
}
