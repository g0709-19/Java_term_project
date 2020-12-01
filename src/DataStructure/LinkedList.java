package DataStructure;

public class LinkedList<T> {
	
	private ListNode<T> head;
	private ListNode<T> last;
	
	public LinkedList() {
		head = last = null;
	}
	
	public int size() {
		int size = 0;
    	for (ListNode<T> node = head; node != null; node = node.next())
    		++size;
		return size;
	}
	
	public void add(T data) {
		ListNode<T> node = new ListNode<>(data);
		
		if (head == null) {
			head = node;
			last = head;
		} else {
			last.link(node);
			last = node;
		}
	}
	
	public void set(int index, T data) {
		int i = 0;
		for (ListNode<T> node = head; node != null; node = node.next()) {
			if (i++ == index) {
				node.set(data);
				break;
			}
		}
	}
	
	public T remove(int removed) {
		int i = 0;
		ListNode<T> prev = null;
		for (ListNode<T> node = head; node != null; node = node.next()) {
			if (i == removed) {
				T temp = node.get();
				
				// 노드 삭제
				ListNode<T> next = node.next();
				if (node == head)
					head = next;
				if (prev != null)
					prev.link(next);

				return temp;
			}
			
			prev = node;
			++i;
		}
		throw new ArrayIndexOutOfBoundsException();		
	}
	
	public T remove(T removed) {
		ListNode<T> prev = null;
		for (ListNode<T> node = head; node != null; node = node.next()) {
			if (node.get() == removed) {
				
				// 노드 삭제
				ListNode<T> next = node.next();
				prev.link(next);
				
				return removed;
			}
			
			prev = node;
		}
		throw new ArrayIndexOutOfBoundsException();		
		
	}
	
    public T get(int search) {
    	int i = 0;
    	//System.out.println("get 을 하려는데 사이즈가 "+size+" 찾으려는건 " + search);
    	for (ListNode<T> node = head; node != null; node = node.next()) {
    		//System.out.printf("i: %d, search: %d\n", i, search);
    		if (i == search)
    			return node.get();
    		++i;
    	}
    	throw new ArrayIndexOutOfBoundsException();
    }
}
