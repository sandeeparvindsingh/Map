package Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import app.location.sunny.map.Images;
import app.location.sunny.map.R;

/**
 * Created by sunny on 3/11/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
private ArrayList<String> mDataset;
    Context context;
// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public class ViewHolder extends RecyclerView.ViewHolder {

    public ImageView img ;


    public ViewHolder(View v) {
        super(v);

        img=(ImageView)v.findViewById(R.id.imageView1);

    }
}

    public void add(int position, String item) {

        notifyItemInserted(position);
    }

    public void remove(String item) {

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<String> myDataset, Context applicationContext) {
        mDataset = myDataset;
        this.context =applicationContext;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_multiphoto_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        /*Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(mDataset.get(position)));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        holder.img.setImageURI(Uri.parse(mDataset.get(position)));
       // holder.img.setImageBitmap(bitmap);
}
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}