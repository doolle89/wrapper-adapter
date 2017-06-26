package dusan.stefanovic.wrapperadapter;

import android.support.annotation.CallSuper;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Dusan Stefanovic <dusan@zedge.net>
 */
public abstract class WrapperRecycleViewAdapter<EmbeddedViewHolder extends RecyclerView.ViewHolder, EmbeddedItem> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final private RecyclerView.Adapter mOriginalAdapter;
    private SparseArrayCompat<EmbeddedItem> mEmbeddedItems;

    final private RecyclerView.AdapterDataObserver mAdapterDataObserver;
    final private SparseBooleanArray mEmbeddedItemViewTypeSet;
    final private SparseBooleanArray mOriginalItemViewTypeSet;

    public WrapperRecycleViewAdapter(RecyclerView.Adapter originalAdapter, SparseArrayCompat<EmbeddedItem> embeddedItems) {
        mOriginalAdapter = originalAdapter;
        mEmbeddedItems = embeddedItems.clone();

        mAdapterDataObserver = new WrapperAdapterDataObserver();
        mOriginalAdapter.registerAdapterDataObserver(mAdapterDataObserver);
        mEmbeddedItemViewTypeSet = new SparseBooleanArray();
        mOriginalItemViewTypeSet = new SparseBooleanArray();
    }

    @Override
    public final int getItemCount() {
        return mOriginalAdapter.getItemCount() + mEmbeddedItems.size();
    }

    @Override
    public final int getItemViewType(int position) {
        int viewType;
        if (isEmbeddedPosition(position)) {
            viewType = getEmbeddedItemViewType(position);
            validateEmbeddedItemViewType(viewType);
            return viewType;
        }
        int originalPosition = getOriginalAdapterPosition(position);
        viewType = mOriginalAdapter.getItemViewType(originalPosition);
        validateOriginalItemViewType(viewType);
        return viewType;
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isEmbeddedViewType(viewType)) {
            return onCreateEmbeddedViewHolder(parent, viewType);
        }
        return mOriginalAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isEmbeddedViewType(holder.getItemViewType())) {
            EmbeddedViewHolder embeddedViewHolder = (EmbeddedViewHolder) holder;
            onBindEmbeddedViewHolder(embeddedViewHolder, position);
            return;
        }
        int originalPosition = getOriginalAdapterPosition(position);
        mOriginalAdapter.onBindViewHolder(holder, originalPosition);
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (isEmbeddedViewType(holder.getItemViewType())) {
            EmbeddedViewHolder embeddedViewHolder = (EmbeddedViewHolder) holder;
            onBindEmbeddedViewHolder(embeddedViewHolder, position, payloads);
            return;
        }
        int originalPosition = getOriginalAdapterPosition(position);
        mOriginalAdapter.onBindViewHolder(holder, originalPosition, payloads);
    }

    @Override
    public final void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (isEmbeddedViewType(holder.getItemViewType())) {
            EmbeddedViewHolder embeddedViewHolder = (EmbeddedViewHolder) holder;
            onEmbeddedViewAttachedToWindow(embeddedViewHolder);
            return;
        }
        mOriginalAdapter.onViewAttachedToWindow(holder);
    }

    @Override
    public final void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (isEmbeddedViewType(holder.getItemViewType())) {
            EmbeddedViewHolder embeddedViewHolder = (EmbeddedViewHolder) holder;
            onEmbeddedViewDetachedFromWindow(embeddedViewHolder);
            return;
        }
        mOriginalAdapter.onViewDetachedFromWindow(holder);
    }

    @Override
    public final void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (isEmbeddedViewType(holder.getItemViewType())) {
            EmbeddedViewHolder embeddedViewHolder = (EmbeddedViewHolder) holder;
            onEmbeddedViewRecycled(embeddedViewHolder);
            return;
        }
        mOriginalAdapter.onViewRecycled(holder);
    }

    @Override
    public final boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        if (isEmbeddedViewType(holder.getItemViewType())) {
            EmbeddedViewHolder embeddedViewHolder = (EmbeddedViewHolder) holder;
            return onFailedToRecycleEmbeddedView(embeddedViewHolder);
        }
        return mOriginalAdapter.onFailedToRecycleView(holder);
    }

    @Override
    public final long getItemId(int position) {
        if (isEmbeddedPosition(position)) {
            return getEmbeddedItemId(position);
        }
        int originalPosition = getOriginalAdapterPosition(position);
        return mOriginalAdapter.getItemId(originalPosition);
    }

    @Override @CallSuper
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mOriginalAdapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override @CallSuper
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mOriginalAdapter.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public final void setHasStableIds(boolean hasStableIds) {
        mOriginalAdapter.setHasStableIds(hasStableIds);
        super.setHasStableIds(hasStableIds);
    }

    public final int getEmbeddedItemCount() {
        return mEmbeddedItems.size();
    }

    public final EmbeddedItem getEmbeddedItem(int position) {
        int offset = getOriginalAdapterPositionOffset(position);
        if (position - offset >= mOriginalAdapter.getItemCount()) {
            return mEmbeddedItems.valueAt(position - offset - mOriginalAdapter.getItemCount());
        }
        return mEmbeddedItems.get(position);
    }

    public final boolean isEmbeddedPosition(int position) {
        if (mEmbeddedItems.get(position) != null) {
            return true;
        }
        int offset = getOriginalAdapterPositionOffset(position);
        if (position - offset >= mOriginalAdapter.getItemCount()) {
            return true;
        }
        return false;
    }

    private int getOriginalAdapterPositionOffset(int position) {
        int size = mEmbeddedItems.size();
        int key;
        int index;
        for (index = 0; index < size; index++) {
            key = mEmbeddedItems.keyAt(index);
            if (key > position) {
                break;
            } else if (key == position) {
                index++;
                break;
            }
        }
        return index;
    }

    /**
     * This method translates wrapper adapter position to original (wrapped) adapter position,
     * returned position must be less or equal since wrapper adapter injects additional items
     * @param position wrapper adapter position
     * @return original (wrapped) adapter position
     */
    public final int getOriginalAdapterPosition(int position) {
        if (isEmbeddedPosition(position)) {
            throw new IllegalStateException("This position does not belong to the original adapter");
        }
        return position - getOriginalAdapterPositionOffset(position);
    }

    public final RecyclerView.Adapter getOriginalAdapter() {
        return mOriginalAdapter;
    }

    @CallSuper
    public void destroy() {
        mOriginalAdapter.unregisterAdapterDataObserver(mAdapterDataObserver);
        mEmbeddedItems = null;
    }

    private void validateEmbeddedItemViewType(int viewType) {
        if (mOriginalItemViewTypeSet.get(viewType)) {
            throw new IllegalStateException("Embedded view type collides with original adapter view type");
        }
        mEmbeddedItemViewTypeSet.put(viewType, true);
    }

    private void validateOriginalItemViewType(int viewType) {
        if (mEmbeddedItemViewTypeSet.get(viewType)) {
            throw new IllegalStateException("Embedded view type collides with original adapter view type");
        }
        mOriginalItemViewTypeSet.put(viewType, true);
    }

    // Override in subclass

    public abstract boolean isEmbeddedViewType(int viewType);

    public abstract int getEmbeddedItemViewType(int position);

    public abstract EmbeddedViewHolder onCreateEmbeddedViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindEmbeddedViewHolder(EmbeddedViewHolder holder, int position);

    public void onBindEmbeddedViewHolder(EmbeddedViewHolder holder, int position, List<Object> payloads) {
        onBindEmbeddedViewHolder(holder, position);
    }

    public void onEmbeddedViewAttachedToWindow(EmbeddedViewHolder holder) {}

    public void onEmbeddedViewDetachedFromWindow(EmbeddedViewHolder holder) {}

    public void onEmbeddedViewRecycled(EmbeddedViewHolder holder) {}

    public boolean onFailedToRecycleEmbeddedView(EmbeddedViewHolder holder) {
        return false;
    }

    public long getEmbeddedItemId(int position) {
        return RecyclerView.NO_ID;
    }

    class WrapperAdapterDataObserver extends RecyclerView.AdapterDataObserver {

        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            positionStart = positionStart + getOriginalAdapterPositionOffset(positionStart);
            notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            positionStart = positionStart + getOriginalAdapterPositionOffset(positionStart);
            notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            positionStart = positionStart + getOriginalAdapterPositionOffset(positionStart);
            notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            positionStart = positionStart + getOriginalAdapterPositionOffset(positionStart);
            notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            fromPosition = fromPosition + getOriginalAdapterPositionOffset(fromPosition);
            toPosition = toPosition + getOriginalAdapterPositionOffset(toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }
    }
}