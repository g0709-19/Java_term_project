package DataStructure;

public class Queue<T> {

    private ListNode<T> front;
    private ListNode<T> rear;
    private int size;

    public Queue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    public void enqueue(T data) {
    	ListNode<T> node = new ListNode<T>(data);
        
    	if (empty()) {
        	front = node;
        }
        else {
        	rear.link(node);
        }
    	
    	rear = node;
    	++size;
    }

    public T dequeue() {
    	if (empty())
    		return null;
    	else {
    		T temp = front.get();
    		front = front.next();
    		--size;
    		return temp;
    	}
    }
    
    public T peek() {
        return front.get();
    }

//    public int search(T item) {
//    	ListNode<T> search = top;
//        int index = 1;
//        while(true) {
//            if (search.get() == item) {
//                return index;
//            } else {
//            	search = search.next();
//                ++index;
//                if (search == null)
//                    break;
//            }
//        }
//        return -1;
//    }

    private boolean empty() {
        return size == 0;
    }
    
}