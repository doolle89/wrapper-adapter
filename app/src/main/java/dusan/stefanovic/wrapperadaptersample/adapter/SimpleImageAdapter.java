package dusan.stefanovic.wrapperadaptersample.adapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dusan.stefanovic.wrapperadapter.WrapperRecycleViewAdapter;
import dusan.stefanovic.wrapperadaptersample.viewholder.FixedSquareImageViewHolder;
import dusan.stefanovic.wrapperadaptersample.viewholder.ImageViewHolder;

import java.util.List;

/**
 * @author Dusan Stefanovic <dusan@zedge.net>
 */
public class SimpleImageAdapter extends RecyclerView.Adapter<FixedSquareImageViewHolder> {

    List<String> mItemList;

    public SimpleImageAdapter(List<String> itemList) {
        mItemList = itemList;
    }

    @Override
    public FixedSquareImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(FixedSquareImageViewHolder.LAYOUT, parent, false);
        return new FixedSquareImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FixedSquareImageViewHolder holder, int position) {
        holder.bind(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
