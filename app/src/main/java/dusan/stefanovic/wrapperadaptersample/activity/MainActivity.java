package dusan.stefanovic.wrapperadaptersample.activity;

import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import dusan.stefanovic.wrapperadaptersample.R;
import dusan.stefanovic.wrapperadaptersample.adapter.HorizontalScrollingListWrapperAdapter;
import dusan.stefanovic.wrapperadaptersample.adapter.SimpleImageWrapperAdapter;
import dusan.stefanovic.wrapperadaptersample.adapter.SimpleTextAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set layout
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // set SimpleTextAdapter
        SimpleTextAdapter simpleTextAdapter = new SimpleTextAdapter(generateTextItems());
        mRecyclerView.setAdapter(simpleTextAdapter);

        // add HorizontalScrollingListWrapperAdapter
        SparseArrayCompat<List<String>> horizontalScrollingEmbeddedItems = new SparseArrayCompat<>();
        horizontalScrollingEmbeddedItems.put(3, generateImageUriItems());
        horizontalScrollingEmbeddedItems.put(10, generateImageUriItems());
        horizontalScrollingEmbeddedItems.put(18, generateImageUriItems());
        HorizontalScrollingListWrapperAdapter horizontalScrollingListWrapperAdapter =
                new HorizontalScrollingListWrapperAdapter(simpleTextAdapter, horizontalScrollingEmbeddedItems);
        mRecyclerView.setAdapter(horizontalScrollingListWrapperAdapter);

        // add SimpleImageWrapperAdapter
        SparseArrayCompat<String> simpleImageEmbeddedItems = new SparseArrayCompat<>();
        simpleImageEmbeddedItems.put(3, generateImageUriItem());
        simpleImageEmbeddedItems.put(32, generateImageUriItem());
        simpleImageEmbeddedItems.put(27, generateImageUriItem());
        SimpleImageWrapperAdapter simpleImageWrapperAdapter = new SimpleImageWrapperAdapter(horizontalScrollingListWrapperAdapter, simpleImageEmbeddedItems);
        mRecyclerView.setAdapter(simpleImageWrapperAdapter);
    }

    // generate random items
    Random mRandom = new Random();

    String generateImageUriItem() {
        int colorCode = mRandom.nextInt(256*256*256);
        return String.format(Locale.ENGLISH, "http://via.placeholder.com/500x500/%06x/%06x", colorCode, colorCode);
    }

    List<String> generateImageUriItems() {
        int size = 10;
        ArrayList<String> arrayList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(generateImageUriItem());
        }
        return arrayList;
    }

    List<String> generateTextItems() {
        int size = 50;
        ArrayList<String> arrayList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(String.format(Locale.ENGLISH, "Item %d", i));
        }
        return arrayList;
    }
}
