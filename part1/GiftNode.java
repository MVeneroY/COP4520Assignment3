public class GiftNode {

    enum Location {
        bag,
        list,
        out
    }
    
    private GiftNode next = null;
    private GiftNode prev = null;
    private Location location = Location.bag; 
    private int tag;

    GiftNode(int tag) { this.tag = tag; }
    public int getTag() { return this.tag; }
    public Location getLocation() { return this.location; }
    public void setLocation(Location location) { this.location = location; }

    public void link(GiftNode prev) {
        prev.next = this;
    }

    public GiftNode unlink() {
        GiftNode curr = this;
        this.prev.next = this.next;

        return curr;
    }
}
