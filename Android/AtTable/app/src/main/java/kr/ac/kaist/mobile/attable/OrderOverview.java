package kr.ac.kaist.mobile.attable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.kaist.mobile.attable.api.ApiMenuItem;
import kr.ac.kaist.mobile.attable.api.ApiOrder;
import kr.ac.kaist.mobile.attable.api.ApiOrderPlace;
import kr.ac.kaist.mobile.attable.api.ApiOrderPlaceItem;
import kr.ac.kaist.mobile.attable.shared.SharedStorage;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class OrderOverview extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_overview);

        // Filter items
        List<ApiOrderPlaceItem> filteredOrders = new ArrayList<ApiOrderPlaceItem>();
        List<ApiMenuItem> filteredMenu = new ArrayList<ApiMenuItem>();
        for (int i=0; i<SharedStorage.get().getOrderItems().size(); ++i) {
            if (SharedStorage.get().getOrderItems().get(i).getAmount() > 0) {
                filteredOrders.add(SharedStorage.get().getOrderItems().get(i));
                filteredMenu.add(SharedStorage.get().getMenu().get(i));
            }
        }
        // Only do any work if we actually have an order
        if (filteredOrders.size() == 0) {
            Cancel();
        }
        else {
            // Set filtered items to list adapter
            SharedStorage.get().setFilteredOrderItems(filteredOrders);
            SharedStorage.get().setFilteredMenu(filteredMenu);
            // Configure the list view
            final ListView orderList = (ListView) findViewById(R.id.listView);
            orderList.setAdapter(new OrderOverviewAdapter(this, R.layout.list_item_order_place,
                    filteredOrders));

            // Configure cancel button
            Button cancel = (Button) findViewById(R.id.cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cancel();
                }
            });
            // Configure proceed button
            Button proceed = (Button) findViewById(R.id.submitOrder);
            proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SubmitOrder();
                }
            });

            // Aggregate amount
            TextView aggregate = (TextView) findViewById(R.id.aggregate);
            List<ApiMenuItem> menu = SharedStorage.get().getMenu();
            List<ApiOrderPlaceItem> placed = SharedStorage.get().getOrderItems();
            int agg = 0;
            for (int i = 0; i < filteredMenu.size(); ++i) {
                agg += filteredMenu.get(i).getPrice() * filteredOrders.get(i).getAmount();
            }
            aggregate.setText(String.format("Total price: %,d \u20A9", agg));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_overview, menu);
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

    public void Cancel() {
        // Remove all of the currently set shared storage orders
        SharedStorage.get().setOrderItems(null);
        // Redirect to main activity
        Intent toStartIntent = new Intent(OrderOverview.this, MainActivity.class);
        startActivity(toStartIntent);
    }

    public void SubmitOrder() {
        // Create order
        ApiOrderPlace order = new ApiOrderPlace(SharedStorage.get().getFilteredOrderItems());

        // Submit the order to the server
        RestClient.get().order(SharedStorage.get().getTableId(), order, new Callback<ApiOrder>() {
            @Override
            public void success(ApiOrder order, Response response) {
                Log.i("App", "Created order: " + order.toString());
                // Reset order
                SharedStorage.get().setOrderItems(null);
                // Redirect to main activity
                Intent toStartIntent = new Intent(OrderOverview.this, MainActivity.class);
                startActivity(toStartIntent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("App", error.toString());
            }
        });
    }
}
