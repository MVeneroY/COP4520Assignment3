public class GiftList {

    GiftNode head = null;
    // int lastTag = -1;
    // int nextTag;
    // boolean started = false;

    GiftList() {}
    GiftList(GiftNode head) { this.head = head; }

    public boolean isEmpty() { return head == null; }

    public void display() {
        if (head == null) return;

        GiftNode curr = head;
        while (curr != null) {
            System.out.printf("%d -> ", curr.getTag());
            curr = curr.next;
        }

        System.out.println("X");
    }

    public void add(GiftNode node) {
        if (node == null) return;
        // started = true;

        if (this.isEmpty()) {
            this.head = node;
            node.next = null;
            node.prev = null;
            // nextTag = node.getTag();
            return;
        }

        GiftNode curr = head;
        while (curr.next != null) { curr = curr.next; }
        curr.next = node;
        node.next = null;
        node.prev = curr;
        // nextTag = node.getTag();
    }

    public int getNextTag() {
        // if (!started) return 0;
        // return nextTag;
        if (this.isEmpty()) return 0;
        GiftNode curr = head;

        while (curr.next != null) curr = curr.next;
        return curr.getTag() + 1; 
    } 

    // removes the head from the list and returns it
    public GiftNode pop() {
        if (this.isEmpty()) return null;

        GiftNode curr = this.head;
        if (curr.next == null) {
            this.head = null;
            return curr;
        }

        this.head = this.head.next;
        this.head.prev = null;

        return curr;
    }
}