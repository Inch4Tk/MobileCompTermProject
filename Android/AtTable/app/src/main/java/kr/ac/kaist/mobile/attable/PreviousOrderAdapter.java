package kr.ac.kaist.mobile.attable;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import kr.ac.kaist.mobile.attable.api.ApiOrder;
import kr.ac.kaist.mobile.attable.api.ApiOrderItem;

public class PreviousOrderAdapter extends ArrayAdapter<ApiOrder> {

    private List<List<View>> rows;

    public PreviousOrderAdapter(Context context, int resource, List<ApiOrder> items) {
        super(context, resource, items);

        rows = new ArrayList<List<View>>();
        for (ApiOrder o : items) {
            rows.add(new ArrayList<View>());
            for (ApiOrderItem i : o.getItems()) {
                int curIdx = rows.size()-1;
                LayoutInflater vi = LayoutInflater.from(getContext());
                rows.get(curIdx).add(vi.inflate(R.layout.list_item_previous_order_item, null));
            }
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_previous_order, null);
        }

        ApiOrder p = getItem(position);

        // Set items values to the layout
        if (p != null) {

            TextView status = (TextView) v.findViewById(R.id.status);
            TextView timestamp = (TextView) v.findViewById(R.id.timestamp);

            // Handle status
            if (status != null) {
                String stat = "";
                switch (p.getStatus()){
                    case 0:
                        stat = "Unseen";
                        break;
                    case 1:
                        stat = "Processing";
                        break;
                    case 2:
                        stat = "Delivered";
                        break;
                    default:
                        stat = "Unseen";
                }
                status.setText("Status: " + stat);
            }

            // Handle timezone
            if (timestamp != null) {
                TimeZone tZone = TimeZone.getDefault();
                DateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", Locale.ENGLISH);
                DateFormat printFormat = new SimpleDateFormat("kk:mm:ss MM/dd");
                printFormat.setTimeZone(tZone);
                Date date = new Date();
                try {
                    date = parseFormat.parse(p.getTimestamp());
                }
                catch (ParseException e) {
                    Log.e("App", e.toString());
                }
                timestamp.setText(printFormat.format(date));
            }

            // Generate a row for every item
            int aggPrice = 0;
            TableLayout itemTable = (TableLayout) v.findViewById(R.id.item_table);
            itemTable.removeAllViews();
            if (itemTable != null) {
                List<ApiOrderItem> items = p.getItems();
                for (int j=0; j < items.size(); ++j) {
                    ApiOrderItem i = items.get(j);
                    View row = rows.get(position).get(j);
                    itemTable.addView(row);

                    // Get the views
                    TextView name = (TextView) row.findViewById(R.id.name);
                    TextView amount = (TextView) row.findViewById(R.id.amount);
                    TextView price = (TextView) row.findViewById(R.id.price);
                    TextView agg = (TextView) row.findViewById(R.id.aggregate);

                    // Configure the views
                    name.setText(i.getName());

                    String priceF = String.format("%,d \u20A9", i.getPrice());
                    price.setText(priceF);

                    amount.setText(Integer.toString(i.getAmount()));

                    int ap = i.getPrice() * i.getAmount();
                    aggPrice += ap;
                    String aggPriceF = String.format("%,d \u20A9", ap);
                    agg.setText(aggPriceF);
                }
            }

            // Handle the item aggregate
            TextView aggregate = (TextView) v.findViewById(R.id.total);
            if (aggregate != null) {
                String priceF = String.format("%,d \u20A9", aggPrice);
                aggregate.setText(priceF);
            }

        }

        return v;

    }
}

