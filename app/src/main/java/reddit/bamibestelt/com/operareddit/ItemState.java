package reddit.bamibestelt.com.operareddit;

/**
 * Created by bamibestelt on 22/02/2017.
 */

public class ItemState {
    private String title;
    private int type;

    public static int TYPE_FETCH = 0;
    public static int TYPE_DATA = 1;

    public ItemState() {
        super();
    }

    public ItemState(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }
}
