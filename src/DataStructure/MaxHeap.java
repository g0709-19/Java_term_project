package DataStructure;

import Money.Money;

public class MaxHeap <T> extends LinkedList<T> {
	
	@SuppressWarnings("unchecked")
	public MaxHeap() {
		((MaxHeap<Money>)this).add(Money.createMoneyWithType(0, 0));	// �ε����� 1���� ����
	}
	
	public void insert(T data) {
		
		// ���׸��� ���� �񱳸� �����ϴ� ���� ���� Money �� ��츸 ó��
		if (data instanceof Money) {
			System.out.printf("%d ===========================", this.size());
			this.add(data);
			System.out.printf("%d fajfwejpfeawjpio afewjpiaofewpjio", this.size());
			int i = this.size() - 1;

			Money data_m = (Money)data;
			System.out.println("��ø� ���� ���� �ε��� " + i);
			while (i > 1 && data_m.getPrice() > ((Money)this.get(i/2)).getPrice()) {
				this.set(i, this.get(i/2));
				i /= 2;
			}
			this.set(i, data);
		}
	}
	
//	public T delete() {
//		
//		if (this.size() <= 1) return null;
//		
//		if (this.get(1) instanceof Money) {
//			
//			T item = this.get(1);
//			int size = this.size();
//			
//			this.set(1, this.get(size-1));
//			this.remove(size-1);
//			
//			int i = 1;
//			
//			while (i*2 < size) {
//				T max = this.get(i*2);
//				int max_pos = i*2;
//				
//				if ((i*2+1) < size && ((Money)max).getPrice() < ((Money)this.get(i*2+1)).getPrice()) {
//					max = this.get(i*2+1);
//					max_pos = i*2+1;
//				}
//				
//				if (((Money)this.get(i)).getPrice() > ((Money)max).getPrice()) break;
//				
//				T temp = this.get(i);
//				this.set(i,  this.get(max_pos));
//				this.set(max_pos, temp);
//				i = max_pos;
//			}
//			return item;
//		}
//		
//		return null;
//	}
	public T delete() {
		
		if (this.size() <= 1) return null;
		
		if (this.get(1) instanceof Money) {
			int parent, child;
			T item, temp;
			int size = this.size() - 1;
			System.out.println("����"+size);
			
			item = this.get(1);
			temp = this.remove(size--);
			parent = 1;
			child = 2;
			
			while (child <= size) {
				// ū �ڽĿ��� �ٿ��ֱ� ���� ���ǹ�
				if ((child < size) &&
					((Money)this.get(child)).getPrice() < ((Money)this.get(child+1)).getPrice())
					++child;
				// �� ��� temp �� child �� ���� ������ ����
				if (((Money)temp).getPrice() >= ((Money)this.get(child)).getPrice())
					break;
				this.set(parent, this.get(child));
				parent = child;
				child *= 2;
			}
			
			this.set(parent, temp);
			
			return item;
		}
		
		return null;
	}
}
