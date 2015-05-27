package kr.ac.kaist.mobile.attable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import kr.ac.kaist.mobile.attable.shared.SharedStorage;


public class OrderSelect extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_select);

        // Configure the list view
        final ListView menuList = (ListView) findViewById(R.id.listView);
        menuList.setAdapter(new OrderSelectAdapter(this, R.layout.list_item_order,
                SharedStorage.get().getMenu()));

        // Configure proceed button
        Button proceed = (Button) findViewById(R.id.submitOrder);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the overview
                Intent orderOverviewIntent = new Intent(OrderSelect.this, OrderOverview.class);
                startActivity(orderOverviewIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_select, menu);
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
