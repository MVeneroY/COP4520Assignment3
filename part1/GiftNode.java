public class GiftNode {

    enum Location {
        bag,
        list,
        out
    }
    
    GiftNode next = null;
    GiftNode prev = null;

    private Location location = Location.bag; 
    private int tag;
    private String card = null;

    GiftNode(int tag) { this.tag = tag; }
    public int getTag() { return this.tag; }
    public Location getLocation() { return this.location; }
    public void setLocation(Location location) { this.location = location; }
    public void setCard(String card) { this.card = card; }

    public void link(GiftNode prev) {
        prev.next = this;
    }

    public GiftNode unlink() {
        GiftNode curr = this;
        this.prev.next = this.next;

        return curr;
    }
}
