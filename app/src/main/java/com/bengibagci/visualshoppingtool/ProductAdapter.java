package com.bengibagci.visualshoppingtool;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private List<ProductModel> rowItems;

    public ProductAdapter(Context context, List<ProductModel> items) {
        this.context = context;
        this.rowItems = items;
    }

    private class ViewHolder {
        ImageView image;
        TextView market;
        TextView title;
        TextView price;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.product_items, null);
            holder = new ViewHolder();
            holder.image = convertView.findViewById(R.id.ProductImage);
            holder.market = convertView.findViewById(R.id.Market);
            holder.title = convertView.findViewById(R.id.ProductTitle);
            holder.price = convertView.findViewById(R.id.ProductPrice);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ProductModel rowItem = (ProductModel) getItem(position);

        holder.image.setImageBitmap(rowItem.getImage());
        holder.market.setText(rowItem.getMarket());
        holder.title.setText(rowItem.getTitle());
        holder.price.setText(rowItem.getPrice());

        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}