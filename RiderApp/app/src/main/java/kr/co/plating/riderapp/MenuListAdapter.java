package kr.co.plating.riderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gingeraebi on 2016. 9. 10..
 */
public class MenuListAdapter extends ArrayAdapter<Menu> {
    private Context context;
    private ArrayList<Menu> menus;

    public MenuListAdapter(Context context, int resource, ArrayList<Menu> menus) {
        super(context, resource, menus);
        this.context = context;
        this.menus = menus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_munu, null);
        TextView menuNameText = (TextView) view.findViewById(R.id.menuName);
        TextView menuCountText = (TextView) view.findViewById(R.id.menuCount);

        menuNameText.setText("" + menus.get(position).name);
        menuCountText.setText("" + menus.get(position).quantity);

        return view;
    }
}
