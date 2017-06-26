package dusan.stefanovic.wrapperadaptersample.viewholder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import dusan.stefanovic.wrapperadapter.viewholder.PersistentViewStateViewHolder;
import dusan.stefanovic.wrapperadaptersample.R;
import dusan.stefanovic.wrapperadaptersample.adapter.SimpleImageAdapter;

import java.util.List;

/**
 * @author Dusan Stefanovic <dusan@zedge.net>
 */
public class HorizontalScrollingListViewHolder extends PersistentViewStateViewHolder {

    public static final int LAYOUT = R.layout.list_item_horizontal_scrolling_list;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    public HorizontalScrollingListViewHolder(View itemView) {
        super(itemView);
        mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    public void bind(List<String> mUriList) {
        mAdapter = new SimpleImageAdapter(mUriList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void recycle() {
        mRecyclerView.setAdapter(null);
        mAdapter = null;
    }
}
