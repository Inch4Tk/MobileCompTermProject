package kr.ac.kaist.mobile.attable;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.kaist.mobile.attable.api.ApiMenuItem;
import kr.ac.kaist.mobile.attable.api.ApiOrderPlaceItem;
import kr.ac.kaist.mobile.attable.shared.SharedStorage;

public class OrderSelectAdapter extends ArrayAdapter<ApiMenuItem> {

    public OrderSelectAdapter(Context context, int resource, List<ApiMenuItem> items) {
        super(context, resource, items);

        // Add values to shared storage if they don't already exist
        if (SharedStorage.get().getOrderItems() == null) {
            List<ApiOrderPlaceItem> placeItems = new ArrayList<ApiOrderPlaceItem>();
            for (ApiMenuItem i : items)
                placeItems.add(new ApiOrderPlaceItem(i.getName(), 0));
            SharedStorage.get().setOrderItems(placeItems);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_order, null);
        }

        ApiMenuItem p = getItem(position);

        // Set items values to the layout
        if (p != null) {

            TextView name = (TextView) v.findViewById(R.id.name);
            TextView desc = (TextView) v.findViewById(R.id.description);
            TextView price = (TextView) v.findViewById(R.id.price);
            ImageView picture = (ImageView) v.findViewById(R.id.picture);

            NumberPicker amount = (NumberPicker) v.findViewById(R.id.amount);


            if (name != null) {
                name.setText(p.getName());
            }
            if (desc != null) {
                desc.setText(p.getDescription());
            }
            if (price != null) {
                String priceF = String.format("%,d \u20A9", p.getPrice());
                price.setText(priceF);
            }
            if (amount != null) {
                amount.setMinValue(0);
                amount.setMaxValue(50);
                amount.setValue(SharedStorage.get().getOrderItems().get(position).getAmount());
                amount.setWrapSelectorWheel(false);
                amount.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        SharedStorage.get().getOrderItems().get(position).setAmount(newVal);
                    }
                });
            }
            if (picture != null) {
                Bitmap bm = null;
                if (p.getPicture() != null) {
                    int i = 0;
                    // Run over storage to check for the right keyed picture
                    for (Bitmap b : SharedStorage.get().getMenuPictures()) {
                        // Check if the key is the same
                        if (SharedStorage.get().getMenuPictureKeys().get(i).compareTo(p.getPicture()) == 0) {
                            bm = b;
                            break;
                        }
                        ++i;
                    }
                }
                picture.setImageBitmap(bm);
            }
        }

        return v;

    }
}
