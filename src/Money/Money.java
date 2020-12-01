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
		System.out.println("힙! "+size);
		
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
		
		// MaxHeap 인 경우에는 무조건 화폐 당 한 객체
		if (money instanceof MaxHeap) {
			System.out.println("맥스힙맥스힙맥스힙맥스힙맥스힙맥스힙맥스힙맥스힙맥스힙맥스힙 " + value);
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
			
			// heap 에서 가져오면 큰 값부터 나올 것이므로 순서대로 차감하면 됨
			// 가져온 값이 물건 가격보다 크면 큐에 넣어뒀다가 다시 넣어줌
			
			System.out.println("삭제전 나의 개수 " + money_h.size());
			
			while (value > 0) {
				Money removed = money_h.delete();
				if (removed == null) break; // 입력한 돈으로 해결이 안 됨
				System.out.println("가격이랑께롱 " + value + " " + removed.price);
				if (value < removed.price)
					q.enqueue(removed);
				else
					value -= removed.price;
			}
			System.out.println("삭제후 나의 개수 " + money_h.size());
			
			// 반복문 나오면 value 보다 큰 값들만 큐에 들어있음
			if (!q.isEmpty()) {
				Money m = q.dequeue();
				int change = m.price - value;
				System.out.println("거스름돈 "+change+" 들어가욧");
				
				money = getChange(change);
				
				if (money != null) {
					for (int i=0, size=money.size(); i<size; ++i) {
						m = money.get(i);
						System.out.println("얼마게? " + m.getPrice() + " " + m.getAmount() + "\n인덱스: "+money_h.size());
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
	 * 금액을 1000부터 내림차순으로 나눠서 몇번 나눠지는지(몫) 으로 Money 생성하고
	 * 몫 * 금액을 value 에서 빼준다! 그리고 나머지가 있는지 검사해서 있으면 반복하고
	 * 나머지가 없다면 그대로 거스름돈 반환!
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
