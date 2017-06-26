package dusan.stefanovic.wrapperadaptersample.viewholder;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author Dusan Stefanovic <dusan@zedge.net>
 */
public class FixedSquareImageViewHolder extends ImageViewHolder {

    public FixedSquareImageViewHolder(View itemView) {
        super(itemView);
    }

    void setupItemSize() {
        super.setupItemSize();
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.width = layoutParams.height;
    }
}
