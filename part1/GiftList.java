public class GiftList {

    GiftNode head = null;

    GiftList() {}
    GiftList(GiftNode head) { this.head = head; }

    public boolean isEmpty() { return head == null; }

    public void display() {
        if (head == null) return;

        GiftNode curr = head;
        while (curr != null) {
            System.out.printf("%d ->", curr.getTag());
        }

        System.out.println("X");
    }
}