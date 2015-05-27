package kr.ac.kaist.mobile.attable;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import kr.ac.kaist.mobile.attable.api.ApiMenuItem;
import kr.ac.kaist.mobile.attable.api.ApiOrderPlaceItem;
import kr.ac.kaist.mobile.attable.shared.SharedStorage;


public class OrderOverview extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_overview);

        // Configure the list view
        final ListView menuList = (ListView) findViewById(R.id.listView);
        menuList.setAdapter(new OrderOverviewAdapter(this, R.layout.list_item_order_place,
                SharedStorage.get().getOrderItems()));

        // Configure proceed button
        Button proceed = (Button) findViewById(R.id.submitOrder);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("App", SharedStorage.get().getOrderItems().toString());
            }
        });

        // Aggregate amount
        TextView aggregate = (TextView) findViewById(R.id.aggregate);
        List<ApiMenuItem> menu = SharedStorage.get().getMenu();
        List<ApiOrderPlaceItem> placed = SharedStorage.get().getOrderItems();
        int agg = 0;
        for(int i=0; i<menu.size(); ++i) {
            agg += menu.get(i).getPrice() * placed.get(i).getAmount();
        }
        aggregate.setText(String.format("Total price: %,d \u20A9", agg));
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
}
