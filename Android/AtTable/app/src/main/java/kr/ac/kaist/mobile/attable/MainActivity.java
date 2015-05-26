package kr.ac.kaist.mobile.attable;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;


import java.util.List;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    //Button startButton;
    TextView scanView;
    Button startScan;
    Button chooseOrder;
    static String barcodeId = "hi";

    public final String barcodeParamName = "barcodeID";
    public final static int GET = 1;
    public final static int POST = 2;
    public final String url = "dummyGetURL";
    ///////////////////////

    ///////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //startButton = (Button) findViewById(R.id.startbtn);
        startScan = (Button) findViewById(R.id.buttonscn);
        scanView = (TextView) findViewById(R.id.scanview);
        chooseOrder = (Button) findViewById(R.id.orderchoice);
        scanView.setText(barcodeId);


        startScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent signup = new Intent(this,SignUp.class);
                //startActivity(tab_init);
                //startActivity(signup);
                //setContentView(R.layout.sign_up);
                //Intent intent = new Intent(SignIn.this, SignUp.class);
                //startActivity(intent);
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.initiateScan();
                //finish();
            }
        });

        chooseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listMenu = new Intent(MainActivity.this, ParseMenuItems.class);
                startActivity(listMenu);
            }
        });
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            barcodeId = scanResult.getContents();
            scanView.setText(barcodeId);

            /*List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(barcodeParamName, barcodeId));
            ServiceHandler service = new ServiceHandler();
            String jsonResponse = service.makeServiceCall(url, GET, params);
            */

            //String jsonResponse = "[ {'name': 'takgalbi','price': '1', 'picture': 'some pic', 'description': 'nice takgalbi' },"  +
            //        "{'name': 'takgalbi2','price': '2', 'picture': 'some pic2', 'description': 'nice takgalbi2' }]";

            //ParseMenuItems menu = new ParseMenuItems();


        }
    }




}
