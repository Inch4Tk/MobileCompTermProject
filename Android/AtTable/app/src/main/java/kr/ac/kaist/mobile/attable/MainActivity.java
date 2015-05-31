package kr.ac.kaist.mobile.attable;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import kr.ac.kaist.mobile.attable.api.ApiPicture;
import kr.ac.kaist.mobile.attable.shared.SharedStorage;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    Button startScan;
    private int fetches = 0;
    private int requiredFetches = 2;

    ///////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetches = 0;
        requiredFetches = 2;

        startScan = (Button) findViewById(R.id.buttonscn);

        startScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.initiateScan();
            }
        });

        // Execute emulator only startup code
        if(Build.FINGERPRINT.startsWith("generic")) {
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
        // Get the previous orders
        GetOrders(SharedStorage.get().getTableId());
        // Check if we already have the menu from this table
        if (SharedStorage.get().getFetchMenu()){
            GetMenu(SharedStorage.get().getTableId());
        }
        else {
            fetches += 1;
            CheckFetches();
        }
    }

    public void CheckFetches(){
        if (fetches >= requiredFetches) {
            // Start new intent displaying the menu and allowing order choosing
            Intent orderSelectIntent = new Intent(MainActivity.this, PreOrder.class);
            startActivity(orderSelectIntent);
        }
    }

    public void GetMenu(String tableId) {
        // Reset the pictures array
        SharedStorage.get().resetMenuPictures();
        // Get the menu from the server api
        RestClient.get().getMenu(tableId, new Callback<List<ApiMenuItem>>(){
            @Override
            public void success(List<ApiMenuItem> menuResponse, Response response) {
                // Store menu in a singleton shared storage
                SharedStorage.get().setMenu(menuResponse);

                // Go over the fetched menu and check for pictures to resolve
                for (ApiMenuItem i : menuResponse) {
                    if (i.getPicture() != null) {
                        requiredFetches += 1;
                        RestClient.get().getMenuPicture(i.getPicture(), new Callback<ApiPicture>() {
                            @Override
                            public void success(ApiPicture apiPicture, Response response) {
                                // Convert picture to bitmap and store in shared storage
                                if (apiPicture != null) {
                                    List<Byte> buffer = apiPicture.getData().getData();
                                    byte[] byteArray = new byte[buffer.size()];
                                    int i = 0;
                                    for (Byte current : buffer) {
                                        byteArray[i] = current;
                                        i++;
                                    }
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                                    SharedStorage.get().addMenuPictures(apiPicture.get_id(), decodedByte);
                                }
                                // Check fetches
                                fetches += 1;
                                CheckFetches();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.e("App", error.toString());
                            }
                        });
                    }
                }

                // Increment fetches
                fetches += 1;
                CheckFetches();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("App", error.toString());
            }
        });
    }

    public void GetOrders(String tableId) {
        // Get the orders of a table from the server api
        RestClient.get().getOrders(tableId, new Callback<List<ApiOrder>>() {
            @Override
            public void success(List<ApiOrder> orders, Response response) {
                SharedStorage.get().setPrevOrders(orders);
                fetches += 1;
                CheckFetches();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("App", error.toString());
            }
        });
    }
}
