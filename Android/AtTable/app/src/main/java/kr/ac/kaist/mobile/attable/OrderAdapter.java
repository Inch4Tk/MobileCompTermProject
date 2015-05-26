package kr.ac.kaist.mobile.attable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import kr.ac.kaist.mobile.attable.api.ApiMenuItem;

public class OrderAdapter extends ArrayAdapter<ApiMenuItem> {

    public OrderAdapter(Context context, int resource, List<ApiMenuItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_order, null);
        }

        ApiMenuItem p = getItem(position);

        // Set items values to the layout
        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.name);

            if (tt1 != null) {
                tt1.setText(p.getName());
            }
        }

        return v;

    }
}
