package Main;

public class Beverage {
	
	private String type;
	private int price;
	private int amount;
	private final String path;
	
	private static final int BEVERAGE_AMOUNT = 3;
	
	public Beverage(String path, String type, int price) {
		this(path, type, price, BEVERAGE_AMOUNT);
	}
	
	private Beverage(String path, String type, int price, int amount) {
		this.path = path;
		this.type = type;
		this.price= price;
		this.amount = amount;
	}
	
	// 재고 보충. 변경된 재고를 반환
	public int addItem(int amount) {
		this.amount += amount;
		return this.amount;
	}
	
	public boolean buy() {
		if (isExist()) {
			--amount;
			return true;
		}
		return false;
	}
	
	public int getAmount() {
		return amount;
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
	
	public String getPath() {
		return path;
	}
}
