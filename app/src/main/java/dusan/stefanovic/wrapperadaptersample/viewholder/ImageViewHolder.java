package dusan.stefanovic.wrapperadaptersample.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import dusan.stefanovic.wrapperadaptersample.R;

/**
 * @author Dusan Stefanovic <dusan@zedge.net>
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {

    public static final int LAYOUT = R.layout.list_item_image;

    ImageView mImageView;

    public ImageViewHolder(View itemView) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        setupItemSize();
    }

    public void bind(String uri) {
        Glide.with(itemView.getContext())
             .load(uri)
             .into(mImageView);
    }

    void setupItemSize() {
        int size = itemView.getResources().getDimensionPixelSize(R.dimen.image_view_size);
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.height = size;
    }
}
