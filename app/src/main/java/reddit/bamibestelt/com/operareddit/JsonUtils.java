package reddit.bamibestelt.com.operareddit;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bamibestelt on 22/02/2017.
 */

public class JsonUtils {

    public static List<RedditModel> getRedditsJson(String json) {
        List<RedditModel> redditModel = new ArrayList<RedditModel>();

        try {
            JSONObject obj = new JSONObject(json);
            JSONObject data = obj.getJSONObject("data");
            JSONArray arrays = data.getJSONArray("children");
            //Log.d("JsonUtils", "after:"+data.getString("after"));
            Constants.getInstance().setAFTER(data.getString("after"));
            //Log.d("JsonUtils", "after:"+Constants.getInstance().getAFTER());

            int i=0;
            while(i < arrays.length()) {
                //Log.d("JsonUtils", "i:"+i+" of size:"+arrays.length());
                RedditModel reddit = new RedditModel();
                JSONObject child = (JSONObject) arrays.get(i);
                JSONObject content = child.getJSONObject("data");
                reddit.setTitle(content.getString("title"));
                reddit.setScore(content.getString("score"));
                reddit.setSubreddit(content.getString("subreddit"));

                redditModel.add(reddit);
                i++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("JsonUtils", "size:"+redditModel.size());
        return redditModel;
    }
}
