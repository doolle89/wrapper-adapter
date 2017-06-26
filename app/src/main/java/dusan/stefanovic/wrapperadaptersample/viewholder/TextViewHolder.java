package dusan.stefanovic.wrapperadaptersample.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * @author Dusan Stefanovic <dusan@zedge.net>
 */
public class TextViewHolder extends RecyclerView.ViewHolder {

    public static final int LAYOUT = android.R.layout.simple_list_item_1;

    TextView mTextView;

    public TextViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(android.R.id.text1);
    }

    public void bind(String text) {
        mTextView.setText(text);
    }
}
