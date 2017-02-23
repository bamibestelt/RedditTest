package reddit.bamibestelt.com.operareddit;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bamibestelt on 22/02/2017.
 */

public class RedditRequest extends Request {

    //Gson gson = new Gson();
    private List<RedditModel> list = new ArrayList<RedditModel>();
    RedditCallback callback;

    public RedditRequest(MainActivity mainActivity) {
        super(Request.Method.GET, Constants.getInstance().getAPI_URL(), null);

        callback = mainActivity;
    }


    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Log.d("LIST_REDDIT", "jsOn:" + json);

            list = JsonUtils.getRedditsJson(json);
            if(list.isEmpty()) {
                callback.notifyDataEmpty();
            } else {
                callback.notifyDataAvailable(list);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(Object response) {

    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);

        Log.d("ERROR", "deliverError");
        callback.notifyRequestError();
    }

}
