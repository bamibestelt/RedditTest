package reddit.bamibestelt.com.operareddit;

/**
 * Created by bamibestelt on 23/02/2017.
 */

public class Constants {

    private static Constants constants;

    private String AFTER = "";
    private final String MAIN_URL = "https://www.reddit.com";
    private final String TOP_API = MAIN_URL + "/r/gaming/top.json";

    public Constants() {}

    public static Constants getInstance() {
        if(constants == null) {
            constants = new Constants();
        }
        return constants;
    }

    public String getMAIN_URL() {
        return MAIN_URL;
    }

    public void setAFTER(String AFTER) {
        this.AFTER = AFTER;
    }

    public String getAFTER() {
        return AFTER;
    }

    public String getAPI_URL() {
        return TOP_API + "?after=" + AFTER;
    }
}
