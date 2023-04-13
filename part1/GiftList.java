import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GiftList {

    GiftNode head = null;
    GiftNode tail = null;
    GiftNode virtualhead = null;

    Lock lock;
    Lock popLock;

    // Current implementation: just locks
    // cores choke in add operation
    // Perhaps implement a circular queue to avoid traversing 
    // entire linked list for every add() call

    GiftList() { 
        this.lock = new ReentrantLock(); 
        this.popLock = new ReentrantLock();
    }

    GiftList(GiftNode head) { 
        this.head = head;
        this.lock = new ReentrantLock();
        this.popLock = new ReentrantLock();
    }

    public boolean isEmpty() { return head == null && tail == null; }

    public void display() {
        if (head == null) return;

        GiftNode curr = head;
        do {
            System.out.printf("%d -> ", curr.getTag());
            curr = curr.next;
        } while (curr != head);

        System.out.println("X");
    }

    public void add(GiftNode node) {
        if (node == null || node.getLocation() != GiftNode.Location.bag) return;

        lock.lock();
        try {
            if (this.isEmpty()) {
                head = node;
                virtualhead = head;
                tail = node;

                head.next = tail;
                tail.next = head;

                node.setLocation(GiftNode.Location.list);
                // System.out.println("Just added: " + node.getTag());
                return;
            }

            tail.next = node;
            tail = node;
            tail.next = this.head;
            node.setLocation(GiftNode.Location.list);
            // System.out.println("Just added: " + node.getTag());
        } finally { lock.unlock(); }
    }

    public int getNextTag() {
        if (this.isEmpty()) return 0;
        return tail.getTag() + 1;
    } 

    // removes the head from the list and returns it
    public GiftNode pop() {
        if (this.isEmpty()) return null;

        popLock.lock();
        try {
            GiftNode curr = virtualhead;
            virtualhead.setLocation(GiftNode.Location.out);
            if (virtualhead.next.getLocation() == GiftNode.Location.list)
                virtualhead = virtualhead.next;

            return curr;
            // GiftNode curr = head;
            // while (curr != null && curr.getLocation() == GiftNode.Location.out) {
            //     curr = curr.next;
            // }

            // curr.setLocation(GiftNode.Location.out);
            // // System.out.println("hi");
            // return curr;
        } finally { popLock.unlock(); }
    }

    public boolean searchNode(int tag) {
        if (this.isEmpty()) return false;

        GiftNode curr = virtualhead;
        do {
            if (curr.getLocation() == GiftNode.Location.list && curr.getTag() == tag) return true;
            curr = curr.next;
        } while (curr != head);

        return false;
    }

    // Verify that all nodes are in immediate ascending order
    public boolean verifyContinuity() {
        if (this.isEmpty()) return true;

        GiftNode prev = this.head;
        GiftNode curr = prev.next;
        while (curr != head) {
            if (prev.getTag() + 1 != curr.getTag()) return false;
            prev = curr;
            curr = curr.next;
        }

        return true;
    }
}