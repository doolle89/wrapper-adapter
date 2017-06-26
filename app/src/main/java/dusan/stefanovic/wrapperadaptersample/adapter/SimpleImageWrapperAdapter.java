package dusan.stefanovic.wrapperadaptersample.adapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dusan.stefanovic.wrapperadapter.WrapperRecycleViewAdapter;
import dusan.stefanovic.wrapperadaptersample.viewholder.ImageViewHolder;

/**
 * @author Dusan Stefanovic <dusan@zedge.net>
 */
public class SimpleImageWrapperAdapter extends WrapperRecycleViewAdapter<ImageViewHolder, String> {

    public SimpleImageWrapperAdapter(RecyclerView.Adapter originalAdapter, SparseArrayCompat<String> embeddedItems) {
        super(originalAdapter, embeddedItems);
    }

    @Override
    public ImageViewHolder onCreateEmbeddedViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindEmbeddedViewHolder(ImageViewHolder holder, int position) {
        holder.bind(getEmbeddedItem(position));
    }

    @Override
    public boolean isEmbeddedViewType(int viewType) {
        return viewType == ImageViewHolder.LAYOUT;
    }

    @Override
    public int getEmbeddedItemViewType(int position) {
        return ImageViewHolder.LAYOUT;
    }
}
