package DataStructure;

public class Queue<T> {

    ListNode<T> front;
    ListNode<T> rear;

    public Queue() {
        this.front = null;
        this.rear = null;
    }

    public void enqueue(T data) {
    	ListNode<T> node = new ListNode<T>(data);
        ListNode<T> temp;
    	if (empty()) {
        	front = node;
        	return;
        }
        else if (rear == null) {
        	temp = front;
        }
        else {
        	temp = rear;
        }
        temp.link(node);
        rear = node;
    }

    public T dequeue() {
    	if (empty())
    		throw new ArrayIndexOutOfBoundsException();
    	else {
    		T temp = front.get();
    		front = front.next();
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
        return front == rear;
    }
    
}
