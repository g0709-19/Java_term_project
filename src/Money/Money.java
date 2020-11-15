package Money;

import DataStructure.LinkedList;

public abstract class Money implements Cloneable {
	
	public static final int[] money_unit = {
		10, 50, 100, 500, 1000
	};
	
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
	
	public static int getMoneyAmount(LinkedList<Money> money, int price) {
		int size = money.size();
		
		for (int i=0; i<size; ++i) {
			Money m = money.get(i);
			if (m.getPrice() == price)
				return m.amount;
		}
		
		return 0;
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
	
	public static void addMoney(LinkedList<Money> money, Money value) {
		int size = money.size();
		
		for (int i=0; i<size; ++i) {
			Money m = money.get(i);
			if (m.price == value.price) {
				m.amount += value.amount;
				break;
			}
		}
	}
	
	public static void subMoney(LinkedList<Money> money, int value) {
		// 힙 정렬 사용하고 큰거 순서대로 빼주면 될듯
	}
	
	public static void initMoneyList(LinkedList<Money> list, int amount) {
		if (list != null) {
			for (int i=0; i<money_unit.length; ++i) {
				Money m = Money.createMoneyWithType(money_unit[i], amount);
				list.add(m);
			}
		}
	}
	
	private static Money createMoneyWithType(int price, int amount) {
		Money m;
		if (price >= 1000)
			m = new Bill(price, amount);
		else
			m = new Coin(price, amount);
		return m;
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
