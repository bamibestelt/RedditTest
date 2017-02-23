package reddit.bamibestelt.com.operareddit;

import java.util.List;

/**
 * Created by bamibestelt on 22/02/2017.
 */

public interface RedditCallback {

    void notifyDataAvailable(List<RedditModel> data);
    void notifyDataEmpty();
    void notifyRequestError();
}
