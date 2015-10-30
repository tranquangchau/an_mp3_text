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
public class CustomListAdapter_detail extends ArrayAdapter<String> {
    private final Activity context; //type
    private final String[] itemname; //name

    public CustomListAdapter_detail(Activity context, String[] itemname) {
        super(context, R.layout.mylist_detail, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist_detail, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView_detail);

        txtTitle.setText(Html.fromHtml(itemname[position]));

        return rowView;

    };
}
