package com.example.jtec.an_mp3_text;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main2Activity extends AppCompatActivity {

    ListView listView;
    private ArrayAdapter<String> listAdapter;
    TextView textView;

    String folder_postion_after="";
    //private String folder_postion=""; //folder save save mp3
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView = (TextView) findViewById(R.id.textView);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setVisibility(View.INVISIBLE);
        Button button4 = (Button) findViewById(R.id.button4);
        button4.setVisibility(View.INVISIBLE);

        //get title name file xml
        String newString = "";
        String newString1 = ""; //filename xml have .mp3
        String newString_desc = ""; //filename xml have .mp3
        String folder_postion="";

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle == null) {
                newString = null;
            } else {
                newString = bundle.getString("title_chapter_value");
                newString1 = bundle.getString("link_xml_value1");
                newString_desc = bundle.getString("description_chapter_value");
                folder_postion = bundle.getString("folder_postion");

            }
        } else {
            newString = (String) savedInstanceState.getSerializable("title_chapter_value");
            newString1 = (String) savedInstanceState.getSerializable("link_xml_value1");
            newString_desc = (String) savedInstanceState.getSerializable("description_chapter_value");
            folder_postion = (String) savedInstanceState.getSerializable("folder_postion");
        }

        setTitle(newString);
        //getSupportActionBar().hide();

        set_post(folder_postion);
        //tai file xml from server

       // Toast.makeText(getApplicationContext(), "TTTTT_ "+folder_postion, Toast.LENGTH_SHORT).show();

        get_file_xml(newString1);
        listView = (ListView) findViewById(R.id.listView2);
        listView.setCacheColorHint(0);
        //setCacheColorHint(0);

        /*
        ArrayList<String> planetList = new ArrayList<String>();
//        planetList.addAll(Arrays.asList(planets));
        planetList.addAll(Arrays.asList(newString_desc));
        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.mylist_detail, planetList);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                btn_Xulyview_action_2_action();
            }
        });*/

        btn_Xulyview_action_first_TEST();
        //btn_Xulyview_action_2_action();

    }
    public void set_post(String post_value){
        folder_postion_after=post_value;
    }

    //tro ve man hinh chu
    public void viewMain(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        //co the luu lai chuong dang noi hien tai
        PauseMediaPlay();
        startActivity(intent);
    }

    private boolean is_have_file_xml=false;
    public void btn_Xulyview_action_first_TEST() {
        try {
            // parse our XML
            Toast.makeText(getApplicationContext(), "Dang xu ly xml b1", Toast.LENGTH_SHORT).show();
            File xmlFile = new File(filexml);
            if (!xmlFile.exists()) {
                Toast.makeText(getApplicationContext(), "Chua co file xml have list .mp3 => khong xu ly duoc xml", Toast.LENGTH_SHORT).show();
            } else {
                new parseXmlAsync().execute();
                is_have_file_xml=true;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Xu ly xong xml b2?", Toast.LENGTH_SHORT).show();
    }public void btn_Xulyview_action_first(View v) {
        try {
            // parse our XML
            Toast.makeText(getApplicationContext(), "Dang xu ly xml b1", Toast.LENGTH_SHORT).show();
            File xmlFile = new File(filexml);
            if (!xmlFile.exists()) {
                Toast.makeText(getApplicationContext(), "Chua co file xml have list .mp3 => khong xu ly duoc xml", Toast.LENGTH_SHORT).show();
            } else {
                new parseXmlAsync().execute();
                is_have_file_xml=true;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Xu ly xong xml b2?", Toast.LENGTH_SHORT).show();
    }

    public int maxSize = 0;
    public void btn_Xulyview_action_2_action() {
        if (is_have_file_xml){
            maxSize = parsedDataSet.size();
            ganbien();
            xuly_view();
        }else{
            Toast.makeText(getApplicationContext(), "khong xu ly duoc xml => khong co data for show", Toast.LENGTH_SHORT).show();
        }
    }
    public void btn_Xulyview_action_2(View v) {
        if (is_have_file_xml){
            maxSize = parsedDataSet.size();
            ganbien();
            xuly_view();
        }else{
            Toast.makeText(getApplicationContext(), "khong xu ly duoc xml => khong co data for show", Toast.LENGTH_SHORT).show();
        }
    }

    String[] itemname_1 = new String[1]; //setdefault
    String[] itemname_description = new String[1]; //age

    private void ganbien() {
        textView.setText("Max part: "+maxSize); //gan cho tong quat hien thi
        itemname_1 = new String[maxSize]; //setdefault -to- update max size
        itemname_description = new String[maxSize];
        String change = "";
        for (int m = 0; m < maxSize; m++) {
            //itemname_1[0]="12083";
            change = parsedDataSet.get(m).getName();
            change = change.replace("\\n", System.getProperty("line.separator"));
            itemname_1[m] = change;
            //itemname_1[m] = parsedDataSet.get(m).getName();
            itemname_description[m] = parsedDataSet.get(m).getAge();
        }
    }

    Integer[] imgid = {
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


    //xu ly de nghe nhac
    private void xuly_view() {
        CustomListAdapter_detail adapter = new CustomListAdapter_detail(this, itemname_1);
//		CustomListAdapter adapter=new CustomListAdapter(this, a2, imgid);
        listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //String Slecteditem = itemname_1[+position];
                TextView txtTitle = (TextView) view.findViewById(R.id.textView_detail);
                txtTitle.setTextColor(Color.parseColor("#FF0000"));
//				String Slecteditem = parsedDataSet.get(+position).getName();
//                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), itemname_description[position], Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "click 1", Toast.LENGTH_SHORT).show();
                bai_hien_tai = position;
                textView.setText("Part: " + (position+1));
                
                //boi mau

                //play nhac
                change_music(itemname_description[position]);
                //view.setSelected(true);
            }
        });
    }

    private void change_music(String s1) {
        nghenhacde(s1);
        //listView.setSelection(bai_hien_tai);

    }

    private int bai_hien_tai = 1;
    private MediaPlayer mp;

    private void nghenhacde(String link_nhac) {

        PauseMediaPlay();
        mp = new MediaPlayer();
        try {
            mp.reset();
            //downfile(url, "a1/" + stt + ".mp3");
            //mp.setDataSource(url);
            //mp.setDataSource("/sdcard/a1/" + stt+".mp3");
            String fileplay = down_check_file_url(link_nhac);
            Uri myUri = Uri.parse(fileplay);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setDataSource(getApplicationContext(), myUri);
        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), "not set the URI! 1: " + e, Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(getApplicationContext(), "not set the URI! 2: " + e, Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "not set the URI! 3: " + e, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mp.prepare();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "not set the URI: 4!" + e, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "not set the URI 5:!" + e, Toast.LENGTH_LONG).show();
        }
        //mp.start();
        mp.start();

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(final MediaPlayer mp) {
                //showGui();

                int bien_truoc = bai_hien_tai;
                final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
                if (checkBox.isChecked()) {
                    if (bai_hien_tai < maxSize - 1 && bai_hien_tai >= 0) {
                        bai_hien_tai++;
                        int resId = getResources().getIdentifier("textView" + bai_hien_tai, "id", getPackageName());
                        //TextView Text_bai = (TextView) findViewById(resId);
//                        int resId = getResources().getIdentifier("textView"+bai_hien_tai,"id", getPackageName());
//                        TextView Text_bai = (TextView) findViewById(resId);
                        //chang_Color(Text_bai,bai_hien_tai);
                        //change_music(itemname_description[bai_hien_tai]);
                        listView.setSelection(bai_hien_tai);
                        // listView.setItemChecked(bai_hien_tai, true);
//                        listView.performItemClick(ListView,bai_hien_tai,1);
                        //listView.getAdapter().getView(bai_hien_tai, null, null).performClick();
                        //listView().performItemClick(null, 0, getListAdapter().getItemId(0));
//                        listView.performItemClick(
//                                listView.getAdapter().getView(bai_hien_tai, null, null),
//                                bai_hien_tai,
//                                listView.getAdapter().getItemId(bai_hien_tai));
                        listView.performItemClick(listView, bai_hien_tai, listView.getItemIdAtPosition(bai_hien_tai));

                        textView.setText("Part auto: " + (bai_hien_tai+1));

                    } else {
                        showGui();

                        //Text_bai.setTextColor(defaultTextColor);
                    }
                }
                //int resId1 = getResources().getIdentifier("textView" + bien_truoc, "id", getPackageName());
                //TextView Text_bai1 = (TextView) findViewById(resId1);
                //Text_bai1.setTextColor(defaultTextColor);

                //txtView.setTextColor(Color.parseColor("#FACC2E"));
            }
        });

    }

    public void showGui() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Da Play toi bai cuoi cung...");
        alertDialog.setMessage("Are you sure sure?");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
// here you can add functions
            }
        });
        //alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
    }

    private void PauseMediaPlay() {
        if (mp != null) {
        }
        try {
            mp.pause();
            //mp.release();
            mp = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Stop(View v) {
        PauseMediaPlay();
    }

    public void get_file_xml(String xml_flie) {
        String fileplay = down_check_file_url(xml_flie);
        filexml = fileplay;
    }

    public String down_check_file_url(String url) {
        String filename1 = "";
        try {
            //downfile("http://192.168.0.15/a/music/because_of_you.mp3","a1/1.mp3");
            filename1 = downloadFile(url);
            //Toast.makeText(getBaseContext(), "Thanh cong'", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(),
                    "That bai Loi gi do: '" + e,
                    Toast.LENGTH_LONG).show();
            //Text1.setText("That bai Loi gi do"+e);
        }
        File fina = new File(saveFilePath);
        if (fina.exists()) {
            Toast.makeText(getBaseContext(), "Da co file: " + saveFilePath, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "Chua co file: " + saveFilePath, Toast.LENGTH_SHORT).show();
        }
        return filename1;
    }

    //private String dwnload_file_path = "http://192.168.0.15/a/music/because_of_you.mp3";
    private String saveDir = "/sdcard/";
    private String directory_parrent = "a1";
    private String directory = "";
    private String fileName = "";
    String saveFilePath = "";

    public String downloadFile(String dwnload_file_path) throws IOException {
        //check thu muc ext hay khong
        File f1 = new File(saveDir + directory_parrent);
        if (!f1.exists()) {
            f1.mkdir();
        }
        //check folder chuong
        directory = directory_parrent+"/chuong_"+folder_postion_after;
        fileName = dwnload_file_path.substring(dwnload_file_path.lastIndexOf("/") + 1, dwnload_file_path.length());
        File f = new File(saveDir + directory);
        if (!f.exists()) {
            f.mkdir();
        }
        //check file exit hay khong
        File saveFilePath1 = new File(saveDir + directory + File.separator + fileName);
        if (saveFilePath1.exists()) {
            saveFilePath = saveFilePath1.toString();
            return saveFilePath1.toString();
        } else {
            int BUFFER_SIZE = 4096;
            URL url = new URL(dwnload_file_path);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                String disposition = httpConn.getHeaderField("Content-Disposition");
                if (disposition != null) {
                    // extracts file name from header field
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 10, disposition.length() - 1);
                    }
                } else {
                    // extracts file name from URL
                    fileName = dwnload_file_path.substring(dwnload_file_path.lastIndexOf("/") + 1, dwnload_file_path.length());
                }
                // opens input stream from the HTTP connection
                InputStream inputStream = httpConn.getInputStream();

                f = new File(saveDir + directory);
                if (!f.exists()) {
                    f.mkdir();
                }
                saveFilePath = saveDir + directory + File.separator + fileName;

                // opens an output stream to save into file
                FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();
                Toast.makeText(getBaseContext(), "File downloaded", Toast.LENGTH_SHORT).show();
                //textView.setText("File xml downloaded");
            } else {
                Toast.makeText(getBaseContext(), "No file to download. Server replied HTTP code" + responseCode, Toast.LENGTH_SHORT).show();
               // textView.setText("No file .xml downloaded");
            }
            httpConn.disconnect();
            return saveFilePath;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return false;
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

    public static final String LOG_TAG = "MainActivity.java";
    public List list1 = new ArrayList();
    public List<ParsedDataSet> parsedDataSet;
    public String filexml = "machine_code.xml";
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
                int x = 1;

                // initialize our input source variable
                InputSource inputSource = null;

                // XML from sdcard
                if (x == 1) {

                    // make sure sample.xml is in your root SD card directory
//                    File xmlFile = new File( Environment.getExternalStorageDirectory() + "/"+filexml);
                    File xmlFile = new File(filexml);
//                    if (!xmlFile.exists()){
//                        stopLockTask();
//                    }

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
            btn_Xulyview_action_2_action();
        }
    }
}
