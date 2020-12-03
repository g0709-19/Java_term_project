package Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import Operator.Sales.SalesWindow;

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
	
	// ��� ����. ����� ��� ��ȯ
	public int addItem(int amount) {
		this.amount += amount;
		return this.amount;
	}
	
	public boolean buy() {
		if (isExist()) {
			--amount;
			SalesWindow.sales_window.insertToSalesList(this);	// ���� ���
			
			// ǰ�� �� ���� ���
			if (amount <= 0)
				saveSoldOut();
			return true;
		}
		return false;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int value) {
		amount = value;
	}
	
	// ��� ������ false
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
	
	private void saveSoldOut() {
		
		File file = new File(".\\src\\data\\soldout.txt");
		
		try {
			
			FileWriter writer = new FileWriter(file, true);
			
			String date = LocalDate.now().toString();
			
			writer.write(String.format("%s %s %d ��� ����\n", date, type, price));
			
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
