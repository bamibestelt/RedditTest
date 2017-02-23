package reddit.bamibestelt.com.operareddit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RedditCallback {

    static final int NOTIFY_AVAILABLE = 1;
    static final int NOTIFY_EMPTY = 2;
    static final int NOTIFY_ERROR = 3;
    static final int APPEND_LOADING = 4;
    static final int REMOVE_LOADING = 5;

    private int start = 0;
    private int itemCount = 0;
    private boolean loading = true;
    RequestQueue queue;
    private List<RedditModel> mList = new ArrayList<>();
    private ListRedditAdapter mAdapter;

    private ProgressBar progressLoadingReddit;
    private LinearLayout llNoData;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llNoData = (LinearLayout) findViewById(R.id.ll_no_data);
        llNoData.setVisibility(View.GONE);

        progressLoadingReddit = (ProgressBar) findViewById(R.id.pb_loading_data);

        // init UI components here
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list_reddit);
        mAdapter = new ListRedditAdapter(mList, this);
        mRecyclerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setVisibility(View.GONE);

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        progressLoadingReddit.setVisibility(View.VISIBLE);

        // Add the request to the RequestQueue.
        Log.d("REQUEST", Constants.getInstance().getAPI_URL());
        queue.add(new RedditRequest(this));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (loading)
                    return;
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    //End of list
                    requestNextPage();
                }
            }
        });
    }




    @Override
    public void notifyDataAvailable(List<RedditModel> data) {
        if(!mList.isEmpty()) {
            try {
                removeLoading();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        itemCount = data.size();
        mList.addAll(data);
        handlingData.sendEmptyMessage(NOTIFY_AVAILABLE);
    }

    @Override
    public void notifyDataEmpty() {
        if(mList.isEmpty()) {
            handlingData.sendEmptyMessage(NOTIFY_EMPTY);
        } else {
            removeLoading();
        }
    }

    @Override
    public void notifyRequestError() {
        if(mList.isEmpty()) {
            handlingData.sendEmptyMessage(NOTIFY_ERROR);
        } else {
            removeLoading();
        }
    }

    private void appendLoading() {
        handlingData.sendEmptyMessage(APPEND_LOADING);
    }

    private void removeLoading() {
        Log.d("TAG", "removeLoading "+mList.size());
        handlingData.sendEmptyMessage(REMOVE_LOADING);
    }

    private void requestNextPage() {
        loading = true;
        appendLoading();
        Log.d("REQUEST", Constants.getInstance().getAPI_URL());
        queue.add(new RedditRequest(this));
    }





    private Handler handlingData = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch(msg.what) {
                case NOTIFY_AVAILABLE:

                    //mAdapter.notifyItemRangeChanged(start, itemCount);
                    mAdapter.notifyItemRangeInserted(start, itemCount);

                    mRecyclerView.setVisibility(View.VISIBLE);
                    progressLoadingReddit.setVisibility(View.GONE);
                    llNoData.setVisibility(View.GONE);

                    start += itemCount;// next page
                    loading = false;
                    break;
                case NOTIFY_EMPTY:
                    mRecyclerView.setVisibility(View.GONE);
                    progressLoadingReddit.setVisibility(View.GONE);
                    llNoData.setVisibility(View.VISIBLE);
                    loading = false;
                    break;
                case NOTIFY_ERROR:
                    mRecyclerView.setVisibility(View.GONE);
                    progressLoadingReddit.setVisibility(View.GONE);
                    llNoData.setVisibility(View.VISIBLE);
                    loading = false;
                    break;
                case APPEND_LOADING:
                    mList.add(new RedditModel("Loading...", ItemState.TYPE_FETCH));
                    mAdapter.notifyDataSetChanged();
                    break;
                case REMOVE_LOADING:
                    mList.remove(start);
                    mAdapter.notifyItemRemoved(start);
                    mAdapter.notifyItemRangeChanged(start, mList.size());
                    break;
            }
        }
    };
}
