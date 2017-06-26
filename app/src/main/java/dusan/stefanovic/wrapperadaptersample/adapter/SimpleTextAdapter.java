package dusan.stefanovic.wrapperadaptersample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dusan.stefanovic.wrapperadaptersample.viewholder.TextViewHolder;

import java.util.List;

/**
 * @author Dusan Stefanovic <dusan@zedge.net>
 */
public class SimpleTextAdapter extends RecyclerView.Adapter<TextViewHolder> {

    List<String> mItemList;

    public SimpleTextAdapter(List<String> itemList) {
        mItemList = itemList;
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(TextViewHolder.LAYOUT, parent, false);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {
        holder.bind(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
