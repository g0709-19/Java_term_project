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
	
	public static void addMoney(LinkedList<Money> money, LinkedList<Money> value) {
		int size = money.size();
		int value_size = value.size();
		
		for (int i=0; i<size; ++i) {
			boolean b = false;
			Money m = money.get(i);
			
			for (int j=0; j<value_size; ++j) {
				Money value_m = value.get(j);
				
				if (m.price == value_m.price) {
					m.amount += value_m.amount;
					b = true;
					break;
				}
			}
			
			if (b) break;
		}
	}
	
	public static LinkedList<Money> subMoney(LinkedList<Money> money, int value) {
		// ���Ǳ��� money ���� ���ðŰ�(�Ž�����), inputed ������ ���ð�
		
		LinkedList<Money> removed = new LinkedList<>();
		
		do {
			int size = money.size();
			// ȭ��� ������������ ���ĵǾ� �ֱ� ������ �ڿ��� ���� ���鼭 ���°� ȿ����
			for (int i=size-1; i>=0; --i) {
				Money m = money.get(i);
				
				int price = m.price;
				int amount = m.amount;
				
				// ������ ���� Money �� �������� ������ �װ� �������� �۰ų� ������ ������ ����ǰ�
				// �������� ũ�ٸ� Money �� ���ֹ����� ���ο� Money �� ã���� ����
				int sub = value/price;
				if (sub <= amount) {
					amount -= sub;
					value = 0;
				}
				else {
					sub = amount;
					amount = 0;
					value -= amount * price;
				}
				
				removed.add(Money.createMoneyWithType(price, sub));
				m.setAmount(amount);
				
				if (value == 0) break; // �� ������
				else continue;
			}
		} while (value > 0);
		

		return removed;
	}
	
	/*
	 * �ݾ��� 1000���� ������������ ������ ��� ����������(��) ���� Money �����ϰ�
	 * �� * �ݾ��� value ���� ���ش�! �׸��� �������� �ִ��� �˻��ؼ� ������ �ݺ��ϰ�
	 * �������� ���ٸ� �״�� �Ž����� ��ȯ!
	 */
	public static LinkedList<Money> addChange(LinkedList<Money> money, int value) {
		int prices[] = { 1000, 500, 100, 50, 10 };
		
		int mok;
		int namoji;
		
		for (int i=0; i<prices.length; ++i) {
			int price = prices[i];
			if (value < price) continue;
			
			mok = value / price;
			namoji = value % price;
			
			for (int j=0; j<mok; ++j)
				Money.addMoney(money, price);
			
			value -= mok * price;
			
			if (namoji == 0) break;
		}
		
		return money;
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
