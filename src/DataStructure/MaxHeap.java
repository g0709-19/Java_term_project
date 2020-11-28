package DataStructure;

import Money.Money;

public class MaxHeap <T> extends LinkedList<T> {
	
	public MaxHeap() {
		this.add(null);	// �ε����� 1���� ����
	}
	
	public void insert(T data) {
		
		// ���׸��� ���� �񱳸� �����ϴ� ���� ���� Money �� ��츸 ó��
		if (data instanceof Money) {
			this.add(data);
			int i = this.size() - 1;

			Money data_m = (Money)data;
			while (i != 1 && data_m.getPrice() > ((Money)this.get(i/2)).getPrice()) {
				this.set(i/2,  data);
				i /= 2;
			}
			this.set(i, data);
		}
	}
	
	public T delete(T data) {
		
		if (data instanceof Money) {
			int parent, child;
			T item, temp;
			int size = this.size();
			
			item = this.get(1);
			temp = this.remove(size--);
			parent = 1;
			child = 2;
			
			while (child <= size) {
				// ���� �ڽĿ��� �ٿ��ֱ� ���� ���ǹ�
				if ((child < size) &&
					((Money)this.get(child)).getPrice() > ((Money)this.get(child+1)).getPrice())
					++child;
				// �� ��� temp �� child �� ���� ������ ����
				if (((Money)temp).getPrice() <= ((Money)this.get(child)).getPrice())
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
