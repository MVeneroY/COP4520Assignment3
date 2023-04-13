public class GiftNode {

    enum Location {
        bag,
        list,
        out
    }
    
    GiftNode next = null;

    private Location location = Location.bag; 
    private int tag;
    private char card;

    GiftNode(int tag) { this.tag = tag; }

    public int getTag() { return this.tag; }
    public Location getLocation() { return this.location; }
    
    public void setLocation(Location location) { this.location = location; }
    public void setCard(char card) { this.card = card; }
}
