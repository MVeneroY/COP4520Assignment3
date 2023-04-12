import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GiftList {

    GiftNode head = null;
    GiftNode tail = null;

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
        // if (node != null && node.getLocation() != GiftNode.Location.bag) System.out.println("lmao");
        if (node == null || node.getLocation() != GiftNode.Location.bag) return;

        lock.lock();
        try {
            if (this.isEmpty()) {
                // lock.lock();
                // try {
                this.head = node;
                this.tail = node;

                this.head.next = tail;
                this.head.prev = tail;

                this.tail.next = head;
                this.tail.prev = head;

                node.setLocation(GiftNode.Location.list);
                System.out.println("Just added: " + node.getTag());
                return;
                // } finally { lock.unlock(); }
            }

        // lock.lock();
        // try {
            System.out.println((head == null) + " " + (tail == null));
            this.tail.next = node;
            node.prev = this.tail;
            this.tail = node;

            this.tail.next = this.head;
            this.head.prev = this.tail;
            node.setLocation(GiftNode.Location.list);
            System.out.println("Just added: " + node.getTag());
        } finally { lock.unlock(); }
    }

    public int getNextTag() {
        if (this.isEmpty()) return 0;
        return tail.getTag() + 1;
    } 

    // removes the head from the list and returns it
    public GiftNode pop() {
        if (this.isEmpty()) return null;

        lock.lock();
        try {
            GiftNode gift = head;
            if (head == tail) {
                gift.setLocation(GiftNode.Location.out);
                head = null;
                tail = null;
                return gift;
            }

            head = head.next;
            head.prev = tail;
            tail.next = head;
            gift.setLocation(GiftNode.Location.out);
            return gift;
        } finally { lock.unlock(); }
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