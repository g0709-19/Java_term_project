package Money;

import DataStructure.LinkedList;
import DataStructure.MaxHeap;
import DataStructure.Queue;

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
		
		if (money instanceof MaxHeap && size <= 1) return 0;
		System.out.println("��! "+size);
		
		int sum = 0;
		
		{
			int i = 0;
			if (money instanceof MaxHeap)
				i = 1;
			for (; i<size; ++i)
				sum += money.get(i).getFullPrice();
		}
		
		return sum;
	}
	
	public static int getBillPrice(LinkedList<Money> money) {
		int size = money.size();
		int sum = 0;
		
		{
			int i = 0;
			if (money instanceof MaxHeap)
				i = 1;
			for (; i<size; ++i) {
				Money m = money.get(i);
				if (m instanceof Bill)
					sum += m.getFullPrice();
			}
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
		
		// MaxHeap �� ��쿡�� ������ ȭ�� �� �� ��ü
		if (money instanceof MaxHeap) {
			System.out.println("�ƽ����ƽ����ƽ����ƽ����ƽ����ƽ����ƽ����ƽ����ƽ����ƽ��� " + value);
			Money m = Money.createMoneyWithType(value, 1);
			((MaxHeap<Money>) money).insert(m);
		}
		else {
			int size = money.size();
			
			for (int i=0; i<size; ++i) {
				Money m = money.get(i);
				if (m.price == value) {
					++m.amount;
					break;
				}
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
	
	public static LinkedList<Money> subMoney(LinkedList<Money> money, int value) {
		
		if (money instanceof MaxHeap) {
			MaxHeap<Money> money_h = new MaxHeap<>();
			for (int i=0, size=money.size(); i<size; ++i)
				money_h.add(money.get(i));
			Queue<Money> q = new Queue<>();
			
			// heap ���� �������� ū ������ ���� ���̹Ƿ� ������� �����ϸ� ��
			// ������ ���� ���� ���ݺ��� ũ�� ť�� �־�״ٰ� �ٽ� �־���
			
			System.out.println("������ ���� ���� " + money_h.size());
			
			while (value > 0) {
				Money removed = money_h.delete();
				if (removed == null) break; // �Է��� ������ �ذ��� �� ��
				System.out.println("�����̶����� " + value + " " + removed.price);
				if (value < removed.price)
					q.enqueue(removed);
				else
					value -= removed.price;
			}
			System.out.println("������ ���� ���� " + money_h.size());
			
			// �ݺ��� ������ value ���� ū ���鸸 ť�� �������
			if (!q.isEmpty()) {
				Money m = q.dequeue();
				int change = m.price - value;
				System.out.println("�Ž����� "+change+" ����");
				
				money = getChange(change);
				
				if (money != null) {
					for (int i=0, size=money.size(); i<size; ++i) {
						m = money.get(i);
						System.out.println("�󸶰�? " + m.getPrice() + " " + m.getAmount() + "\n�ε���: "+money_h.size());
						money_h.insert(m);
					}			
				}
				
				
			} else {
				return null;
			}
			
			return money_h;
		}
		
		return null;
	}
	
	/*
	 * �ݾ��� 1000���� ������������ ������ ��� ����������(��) ���� Money �����ϰ�
	 * �� * �ݾ��� value ���� ���ش�! �׸��� �������� �ִ��� �˻��ؼ� ������ �ݺ��ϰ�
	 * �������� ���ٸ� �״�� �Ž����� ��ȯ!
	 */
	private static LinkedList<Money> getChange(int value) {
		LinkedList<Money> money = new LinkedList<>();
		
		int prices[] = { 1000, 500, 100, 50, 10 };
		
		int mok;
		int namoji;
		
		for (int i=0; i<prices.length; ++i) {
			int price = prices[i];
			if (value < price) continue;
			
			mok = value / price;
			namoji = value % price;
			
			Money m = Money.createMoneyWithType(price, mok);
			money.add(m);
			
			value -= mok * price;
			
			if (namoji == 0) break;
		}
		
		return money;
	}
	
	public static void initMoneyList(LinkedList<Money> list, int amount) {
		if (list != null) {
			for (int i=0; i<money_unit.length; ++i) {
				Money m = Money.createMoneyWithType(money_unit[i], amount);
				if (list instanceof MaxHeap)
					((MaxHeap<Money>)list).insert(m);
				else
					list.add(m);
			}
		}
	}
	
	public static Money createMoneyWithType(int price, int amount) {
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
