package reddit.bamibestelt.com.operareddit;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bamibestelt on 22/02/2017.
 */

public class ListRedditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RedditModel> list;
    private MainActivity mainActivity;

    public ListRedditAdapter(List<RedditModel> list, MainActivity context) {
        this.list = list;
        this.mainActivity = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reddit_item, parent, false);
        RecyclerView.ViewHolder vh = new ItemViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ItemViewHolder) {
            if(list.get(position).getType() == ItemState.TYPE_FETCH) {
                ((ItemViewHolder)holder).content.setVisibility(View.GONE);
                ((ItemViewHolder)holder).progressBar.setVisibility(View.VISIBLE);
            } else {
                // insert data to the item
                ((ItemViewHolder)holder).content.setVisibility(View.VISIBLE);
                ((ItemViewHolder)holder).progressBar.setVisibility(View.GONE);

                ((ItemViewHolder)holder).tvTitle.setText(list.get(position).getTitle());
                ((ItemViewHolder)holder).tvScore.setText(list.get(position).getScore());
                ((ItemViewHolder)holder).tvSub.setText(list.get(position).getSubreddit());

                ((ItemViewHolder)holder).container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // set new intent to browser
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getPermalink()));
                        mainActivity.startActivity(browserIntent);
                    }
                });
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }




    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public TextView tvTitle;
        public TextView tvScore;
        public TextView tvSub;
        public RelativeLayout container;
        public LinearLayout content;

        public ItemViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pb_loading_data);
            container = (RelativeLayout) itemView.findViewById(R.id.rl_container);
            content = (LinearLayout) itemView.findViewById(R.id.ll_content_container);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_reddit_title);
            tvScore = (TextView) itemView.findViewById(R.id.tv_reddit_score);
            tvSub = (TextView) itemView.findViewById(R.id.tv_reddit_sub);
        }
    }
}
