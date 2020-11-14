package Money;

import DataStructure.LinkedList;

public abstract class Money implements Cloneable {
	private final int price;
	private int amount;
	
	public Money(int price, int amount) {
		this.price = price;
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int value) {
		amount = value;
	}
	
	public void buy() {
		--amount;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getFullPrice() {
		return amount * price;
	}
	
	public static int getFullPrice(LinkedList<Money> money) {
		int size = money.size();
		int sum = 0;
		
		for (int i=0; i<size; ++i)
			sum += money.get(i).getFullPrice();
		
		return sum;
	}
	
	public static int getBillPrice(LinkedList<Money> money) {
		int size = money.size();
		int sum = 0;
		
		for (int i=0; i<size; ++i) {
			Money m = money.get(i);
			if (m instanceof Bill)
				sum += m.getFullPrice();
		}
		
		return sum;
	}
	
	public static void addMoney(LinkedList<Money> money, int value) {
		int size = money.size();
		
		for (int i=0; i<size; ++i) {
			Money m = money.get(i);
			if (m.price == value) {
				++m.amount;
				break;
			}
		}
	}
	
	@Override
	public Money clone() {
		try {
			return (Money) super.clone();			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
