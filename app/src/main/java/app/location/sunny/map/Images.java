package app.location.sunny.map;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import Adapters.MyAdapter;

public class Images extends AppCompatActivity {
Button LoadSingle,LoadMultiple;
    ImageView image;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static int RESULT_LOAD_IMAGE = 1;

    private static int RESULT_LOAD_MULTIPLE_IMAGE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

LoadSingle=(Button) findViewById(R.id.loadsingle);
        LoadMultiple=(Button) findViewById(R.id.loadmultiple);
image = (ImageView) findViewById(R.id.imageView);
        LoadSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        LoadMultiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(i,"Select"), RESULT_LOAD_MULTIPLE_IMAGE);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<String> all_images = new ArrayList<String>();

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            image.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


        Log.d("Images Class","On activity Result");
        if(requestCode == RESULT_LOAD_MULTIPLE_IMAGE)
        {

            Log.d("Images Class","Check Version");
            if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
                    && (null == data.getData()))
            {

                Log.d("Images Class","On activity Result Multiple Selection");
                ClipData clipdata = data.getClipData();
                for (int i=0; i<clipdata.getItemCount();i++)
                {
                    try {

Log.d("For Loop", String.valueOf(clipdata.getItemAt(i).getUri()));
                       // Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), clipdata.getItemAt(i).getUri());
                        Log.d("Images Class","For Loop  "+i);
                       all_images.add(String.valueOf(clipdata.getItemAt(i).getUri()));

                        //DO something
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                Log.d("Images Class","For Loop Completed");
                setContentView(R.layout.image_gallery);
                mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                mRecyclerView.setHasFixedSize(false);

                // use a linear layout manager
                mLayoutManager = new LinearLayoutManager(this);
                mRecyclerView.setLayoutManager(mLayoutManager);

                // specify an adapter (see also next example)
                mAdapter = new MyAdapter(all_images,getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);

            }
            else
            {
                Toast.makeText(getApplicationContext(),"Not Supported",Toast.LENGTH_SHORT).show();
            }
        }
 }
}
