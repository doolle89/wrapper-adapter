package dusan.stefanovic.wrapperadapter;

import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;

import dusan.stefanovic.wrapperadapter.viewholder.PersistentViewStateViewHolder;


/**
 * @author Dusan Stefanovic <dusan@zedge.net>
 */
public abstract class PersistentViewStateWrapperRecycleViewAdapter<ViewHolder extends PersistentViewStateViewHolder, EmbeddedItem>
        extends WrapperRecycleViewAdapter<ViewHolder, EmbeddedItem> {

    final SparseArrayCompat<SparseArray<Parcelable>> mEmbeddedViewStates;

    public PersistentViewStateWrapperRecycleViewAdapter(RecyclerView.Adapter originalAdapter, SparseArrayCompat<EmbeddedItem> embeddedItems) {
        super(originalAdapter, embeddedItems);
        mEmbeddedViewStates = new SparseArrayCompat<>();
    }

    @Override @CallSuper
    public void onEmbeddedViewAttachedToWindow(ViewHolder holder) {
        holder.restoreViewState(getSavedViewStateForPosition(holder.getAdapterPosition()));
    }

    @Override @CallSuper
    public void onEmbeddedViewDetachedFromWindow(ViewHolder holder) {
        holder.saveViewState(getSavedViewStateForPosition(holder.getAdapterPosition()));
    }

    SparseArray<Parcelable> getSavedViewStateForPosition(int position) {
        SparseArray<Parcelable> stateContainer = mEmbeddedViewStates.get(position);
        if (stateContainer == null) {
            stateContainer = new SparseArray<>();
            mEmbeddedViewStates.put(position, stateContainer);
        }
        return stateContainer;
    }
}
