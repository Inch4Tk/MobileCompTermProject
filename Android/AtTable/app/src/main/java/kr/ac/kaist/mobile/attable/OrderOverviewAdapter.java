package kr.ac.kaist.mobile.attable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import kr.ac.kaist.mobile.attable.api.ApiMenuItem;
import kr.ac.kaist.mobile.attable.api.ApiOrderPlaceItem;
import kr.ac.kaist.mobile.attable.shared.SharedStorage;

public class OrderOverviewAdapter extends ArrayAdapter<ApiOrderPlaceItem> {

    public OrderOverviewAdapter(Context context, int resource, List<ApiOrderPlaceItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_order_place, null);
        }

        ApiOrderPlaceItem p = getItem(position);
        ApiMenuItem mi = SharedStorage.get().getMenu().get(position);

        // Set items values to the layout
        if (p != null) {

            TextView name = (TextView) v.findViewById(R.id.name);
            TextView price = (TextView) v.findViewById(R.id.price);
            TextView amount = (TextView) v.findViewById(R.id.amount);
            TextView aggregate = (TextView) v.findViewById(R.id.aggregate);


            if (name != null) {
                name.setText(p.getName());
            }
            if (price != null) {
                String priceF = String.format("%,d \u20A9", mi.getPrice());
                price.setText(priceF);
            }
            if (amount != null) {
                amount.setText(Integer.toString(p.getAmount()));
            }
            if (aggregate != null) {
                String priceF = String.format("%,d \u20A9", mi.getPrice() * p.getAmount());
                aggregate.setText(priceF);
            }
        }

        return v;

    }
}
