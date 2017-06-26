package dusan.stefanovic.wrapperadapter.viewholder;


import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * @author Dusan Stefanovic <dusan@zedge.net>
 */
public class PersistentViewStateViewHolder extends RecyclerView.ViewHolder {

    public PersistentViewStateViewHolder(View itemView) {
        super(itemView);
    }

    public void saveViewState(SparseArray<Parcelable> stateContainer) {
        itemView.saveHierarchyState(stateContainer);
    }

    public void restoreViewState(SparseArray<Parcelable> stateContainer) {
        itemView.restoreHierarchyState(stateContainer);
    }
}

