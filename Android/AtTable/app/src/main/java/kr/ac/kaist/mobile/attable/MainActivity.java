package kr.ac.kaist.mobile.attable;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import kr.ac.kaist.mobile.attable.api.ApiMenuItem;
import kr.ac.kaist.mobile.attable.api.ApiOrder;
import kr.ac.kaist.mobile.attable.shared.SharedStorage;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    Button startScan;

    ///////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startScan = (Button) findViewById(R.id.buttonscn);

        startScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.initiateScan();
            }
        });

        // Execute emulator only startup code
        if(Build.FINGERPRINT.startsWith("generic"))
        {
            // Fake a scan, because we can't do anything else on emulator anyways
            SharedStorage.get().setTableId("5561cd3c85676ad4231e959d");
            HandleScanResult();
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String tableId = scanResult.getContents();
            SharedStorage.get().setTableId(tableId);

            HandleScanResult();
        }
    }

    public void HandleScanResult() {
        // Check if we already have the menu from this table
        if (SharedStorage.get().getFetchMenu()){
            // Get the menu from the server api
            RestClient.get().getMenu(SharedStorage.get().getTableId(), new Callback<List<ApiMenuItem>>(){
                @Override
                public void success(List<ApiMenuItem> menuResponse, Response response) {
                    // Store menu in a singleton shared storage
                    SharedStorage.get().setMenu(menuResponse);
                    // Start new intent displaying the menu and allowing order choosing
                    Intent orderSelectIntent = new Intent(MainActivity.this, OrderSelect.class);
                    startActivity(orderSelectIntent);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("App", error.toString());
                }
            });
        }
        else {
            // Start new intent displaying the menu and allowing order choosing
            Intent orderSelectIntent = new Intent(MainActivity.this, OrderSelect.class);
            startActivity(orderSelectIntent);
        }
    }

    public void GetOrders(String tableId) {
        // Get the orders of a table from the server api
        RestClient.get().getOrders(tableId, new Callback<List<ApiOrder>>() {
            @Override
            public void success(List<ApiOrder> orders, Response response) {
                Log.i("App", "Received orders from server: " + orders.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("App", error.toString());
            }
        });
    }
}
