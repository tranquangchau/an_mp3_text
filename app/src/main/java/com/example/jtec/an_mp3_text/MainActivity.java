package com.example.jtec.an_mp3_text;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Chương trình ABC");

        Button button = (Button) findViewById(R.id.button);
        button.setVisibility(View.INVISIBLE);
        Button button6 = (Button) findViewById(R.id.button6);
        button6.setVisibility(View.INVISIBLE);


        try {
            // parse our XML
            Toast.makeText(getApplicationContext(), "Dang xu ly xml b1", Toast.LENGTH_SHORT).show();
            new parseXmlAsync().execute();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Xu ly xong xml b2?", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public int maxSize=0;
    public void btn_Xulyview_action(View v){
        maxSize = parsedDataSet.size();
        ganbien();
        xuly_view();
    }
    public void action(){
        maxSize = parsedDataSet.size();
        ganbien();
        xuly_view();
    }

    String[] itemname_1 = new String[1]; //setdefault
    String[] itemname_description = new String[1]; //age of xml
    String[] itemname_link = new String[1]; //edmail
    private void ganbien() {
        itemname_1 = new String[maxSize]; //setdefault -to- update max size
        itemname_description = new String[maxSize];
        itemname_link = new String[maxSize];
        for (int m=0;m<maxSize;m++){
            //itemname_1[0]="12083";
            itemname_1[m]=parsedDataSet.get(m).getName();
            itemname_description[m]=parsedDataSet.get(m).getAge();
            itemname_link[m]=parsedDataSet.get(m).getEmailAddress();
        }
    }

    Integer[] imgid={
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5,
            R.drawable.pic6,
            R.drawable.pic7,
            R.drawable.pic8,
            R.drawable.pic8,
    };
    private void xuly_view() {
        CustomListAdapter adapter=new CustomListAdapter(this, itemname_1,itemname_description, imgid);
//		CustomListAdapter adapter=new CustomListAdapter(this, a2, imgid);
        list=(ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem = itemname_1[+position];
//				String Slecteditem = parsedDataSet.get(+position).getName();
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
                String position_folder="";
                if (position==0){
                    position_folder="c0";
                }else{
                    position_folder ="c"+Integer.toString(position);
                }

                btn_viewPage_default(itemname_1[+position], itemname_description[+position], itemname_link[+position], position_folder);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void btn_viewPage_default(String title_chapter, String description_chapter, String file_of_xml, String postion){
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("title_chapter_value", title_chapter);
        intent.putExtra("description_chapter_value", description_chapter);
        intent.putExtra("link_xml_value1", file_of_xml);
        intent.putExtra("folder_postion", postion);
        startActivity(intent);
    }
    //hien thi trang khac
    public void btn_viewPage(View view){
        Intent intent = new Intent(this, Main2Activity.class);
        String message = "test";
        intent.putExtra("title_chapter_value", message);
        intent.putExtra("title_chapter_value1", message);
        startActivity(intent);
    }

    public void btn_delete_allfile(View view){
        File dir = new File(Environment.getExternalStorageDirectory()+"/a1");
        Toast.makeText(getApplicationContext(), "bat dau xoa "+dir, Toast.LENGTH_SHORT).show();
        DeleteRecursive(dir);
        Toast.makeText(getApplicationContext(), "Da xoa het ", Toast.LENGTH_SHORT).show();
    }
    void DeleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                DeleteRecursive(child);

        fileOrDirectory.delete();
    }

    public static final String LOG_TAG = "MainActivity.java";
    public List list1= new ArrayList();
    public List<ParsedDataSet> parsedDataSet;
    public String filexml ="sample.xml";
    private class parseXmlAsync extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {

				/*
				 * You may change the value of x to try different sources of XML
				 *
				 * @1 = XML from SD Card
				 *
				 * @2 = XML from URL
				 *
				 * @3 = XML from assets folder
				 */
                int x = 3;

                // initialize our input source variable
                InputSource inputSource = null;

                // XML from sdcard
                if (x == 1) {

                    // make sure sample.xml is in your root SD card directory
                    File xmlFile = new File(
                            Environment.getExternalStorageDirectory()
                                    + "/"+filexml);
                    FileInputStream xmlFileInputStream = new FileInputStream(
                            xmlFile);
                    inputSource = new InputSource(xmlFileInputStream);
                }

                // XML from URL
                else if (x == 2) {
                    // specify a URL
                    // make sure you are connected to the internet
                    URL url = new URL(
                            "http://demo.codeofaninja.com/AndroidXml/sample.xml");
                    inputSource = new InputSource(url.openStream());
                }

                // XML from assets folder
                else if (x == 3) {
                    inputSource = new InputSource(getAssets()
                            .open(filexml));
                }

                // instantiate SAX parser
                SAXParserFactory saxParserFactory = SAXParserFactory
                        .newInstance();
                SAXParser saxParser = saxParserFactory.newSAXParser();

                // get the XML reader
                XMLReader xmlReader = saxParser.getXMLReader();

                // prepare and set the XML content or data handler before
                // parsing
                XmlContentHandler xmlContentHandler = new XmlContentHandler();
                xmlReader.setContentHandler(xmlContentHandler);

                // parse the XML input source
                xmlReader.parse(inputSource);

                // put the parsed data to a List

                parsedDataSet = xmlContentHandler.getParsedData();

                // we'll use an iterator so we can loop through the data
                Iterator<ParsedDataSet> i = parsedDataSet.iterator();
                ParsedDataSet dataItem;

                while (i.hasNext()) {

                    dataItem = (ParsedDataSet) i.next();

					/*
					 * parentTag can also represent the main type of data, in
					 * our example, "Owners" and "Dogs"
					 */
                    String parentTag = dataItem.getParentTag();
                    Log.v(LOG_TAG, "parentTag: " + parentTag);

                    if (parentTag.equals("Owners")) {
                        Log.v(LOG_TAG, "Name: " + dataItem.getName());
                        Log.v(LOG_TAG, "Age: " + dataItem.getAge());
                        Log.v(LOG_TAG,
                                "EmailAddress: " + dataItem.getEmailAddress());
                    }

//					else if (parentTag.equals("Dogs")) {
//						Log.v(LOG_TAG, "Name: " + dataItem.getName());
//						Log.v(LOG_TAG, "Birthday: " + dataItem.getBirthday());
//					}
                }
                list1 = parsedDataSet;
                //textView1.setText();
                //parsedDataSet.get(0).getAge();

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String lenghtOfFile) {
            // your do stuff after parsing the XML

            action();
//            try {
//                JSONObject jsonObject = new JSONObject(this.result);
//                // call callback
//                listener.onTaskCompleted(jsonObject);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }

    }

}
