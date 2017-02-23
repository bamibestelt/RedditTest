package reddit.bamibestelt.com.operareddit;

import java.io.Serializable;

/**
 * Created by bamibestelt on 22/02/2017.
 */

public class RedditModel implements Serializable {

    private int type = ItemState.TYPE_DATA;

    private String score;
    private String subreddit;
    private String title;

    public RedditModel() {
        super();
    }

    public RedditModel(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScore() {
        return score;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }
}
