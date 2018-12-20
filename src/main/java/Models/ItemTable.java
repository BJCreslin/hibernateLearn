package Models;

public class ItemTable {

    private int code;
    private String name;
    private Integer needed;
    private Integer central;
    private Integer vystavka;
    private String groupname;


    public ItemTable(int code, String name, Integer needed) {
        this.code = code;
        this.name = name;
        this.needed = needed;
    }
}
