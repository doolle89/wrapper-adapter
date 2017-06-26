package dusan.stefanovic.wrapperadaptersample.adapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dusan.stefanovic.wrapperadapter.PersistentViewStateWrapperRecycleViewAdapter;
import dusan.stefanovic.wrapperadaptersample.R;
import dusan.stefanovic.wrapperadaptersample.viewholder.HorizontalScrollingListViewHolder;

import java.util.List;

/**
 * @author Dusan Stefanovic <dusan@zedge.net>
 */
public class HorizontalScrollingListWrapperAdapter
        extends PersistentViewStateWrapperRecycleViewAdapter<HorizontalScrollingListViewHolder, List<String>> {

    public HorizontalScrollingListWrapperAdapter(RecyclerView.Adapter originalAdapter, SparseArrayCompat<List<String>> embeddedItems) {
        super(originalAdapter, embeddedItems);
    }

    @Override
    public HorizontalScrollingListViewHolder onCreateEmbeddedViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new HorizontalScrollingListViewHolder(view);
    }

    @Override
    public void onBindEmbeddedViewHolder(HorizontalScrollingListViewHolder holder, int position) {
        holder.bind(getEmbeddedItem(position));
    }

    @Override
    public void onEmbeddedViewRecycled(HorizontalScrollingListViewHolder holder) {
        holder.recycle();
    }

    @Override
    public int getEmbeddedItemViewType(int position) {
        return HorizontalScrollingListViewHolder.LAYOUT;
    }

    @Override
    public boolean isEmbeddedViewType(int viewType) {
        return viewType == HorizontalScrollingListViewHolder.LAYOUT;
    }
}
