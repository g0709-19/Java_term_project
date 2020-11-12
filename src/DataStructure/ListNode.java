package DataStructure;

public class ListNode<T> {

    private T data;
    private ListNode<T> link;

    public ListNode(T data) {
        this.data = data;
        this.link = null;
    }

    protected void link(ListNode<T> node) {
        this.link = node;
    }

    protected T get() {
        return data;
    }

    protected ListNode<T> next() {
        return link;
    }
}