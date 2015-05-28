package kr.ac.kaist.mobile.attable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import kr.ac.kaist.mobile.attable.api.ApiOrder;
import kr.ac.kaist.mobile.attable.api.ApiOrderItem;
import kr.ac.kaist.mobile.attable.shared.SharedStorage;


public class PreOrder extends ActionBarActivity {

    private ListAdapter prevOrderAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_order);

        // Configure the list view
        final ListView orderList = (ListView) findViewById(R.id.listView);
        prevOrderAdapter = new PreviousOrderAdapter(this, R.layout.list_item_previous_order,
                SharedStorage.get().getPrevOrders());
        orderList.setAdapter(prevOrderAdapter);

        // Configure cancel button
        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cancel();
            }
        });
        // Configure proceed button
        Button proceed = (Button) findViewById(R.id.orderMore);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderMore();
            }
        });

        // Aggregate amount
        TextView aggregate = (TextView) findViewById(R.id.aggregate);
        List<ApiOrder> prevOrders = SharedStorage.get().getPrevOrders();
        int agg = 0;
        for (int i = 0; i < prevOrders.size(); ++i) {
            for (ApiOrderItem item : prevOrders.get(i).getItems())
                agg += item.getPrice() * item.getAmount();
        }
        aggregate.setText(String.format("Total price: %,d \u20A9", agg));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pre_order, menu);
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
        Intent toStartIntent = new Intent(PreOrder.this, MainActivity.class);
        startActivity(toStartIntent);
    }

    public void OrderMore() {
        // Start new intent displaying the menu and allowing order choosing
        Intent orderSelectIntent = new Intent(PreOrder.this, OrderSelect.class);
        startActivity(orderSelectIntent);
    }
}
