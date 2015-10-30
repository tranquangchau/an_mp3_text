package com.example.jtec.an_mp3_text;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jtec on 10/2/2015.
 */
public class CustomListAdapter extends ArrayAdapter<String> {
    private final Activity context; //type
    private final String[] itemname; //name
    private final String[] itemdescription; //description
    private final Integer[] imgid; //image

    public CustomListAdapter(Activity context, String[] itemname, String[] itemdescription, Integer[] imgid) {
        super(context, R.layout.mylist, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.itemdescription = itemdescription;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);
        //imageView.setImageResource(imgid[position]); //list img
        imageView.setImageResource(R.drawable.pho1); //only 1 img for all list
        //extratxt.setText("Description "+itemname[position]);
        extratxt.setText(itemdescription[position]);
        //extratxt.setText(Html.fromHtml(itemdescription[position]));
        return rowView;

    };
}
