package DataStructure;

import Money.Money;

public class MaxHeap <T> extends LinkedList<T> {
	private LinkedList<T> heap;
	
	public MaxHeap() {
		heap = new LinkedList<T>();
		heap.add(null);	// �ε����� 1���� ����
	}
	
	public void insert(T data) {
		
		// ���׸��� ���� �񱳸� �����ϴ� ���� ���� Money �� ��츸 ó��
		if (data instanceof Money) {
			heap.add(data);
			int i = heap.size() - 1;

			Money data_m = (Money)data;
			while (i != 1 && data_m.getPrice() > ((Money)heap.get(i/2)).getPrice()) {
				heap.set(i/2,  data);
				i /= 2;
			}
			heap.set(i, data);
		}
	}
	
	public T delete(T data) {
		
		if (data instanceof Money) {
			int parent, child;
			T item, temp;
			int size = heap.size();
			
			item = heap.get(1);
			temp = heap.remove(size--);
			parent = 1;
			child = 2;
			
			while (child <= size) {
				// ���� �ڽĿ��� �ٿ��ֱ� ���� ���ǹ�
				if ((child < size) &&
					((Money)heap.get(child)).getPrice() > ((Money)heap.get(child+1)).getPrice())
					++child;
				// �� ��� temp �� child �� ���� ������ ����
				if (((Money)temp).getPrice() <= ((Money)heap.get(child)).getPrice())
					break;
				heap.set(parent, heap.get(child));
				parent = child;
				child *= 2;
			}
			
			heap.set(parent, temp);
			
			return item;
		}
		
		return null;
	}
}
