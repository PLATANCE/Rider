package kr.co.plating.riderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kde713 on 2016. 8. 21..
 */
public class DelieverAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<DelieverData> mListData = new ArrayList<>();

    public DelieverAdapter(Context c, ArrayList<DelieverData> mData){
        super();
        this.mContext = c;
        addTitle(mData);
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int i) {
        return mListData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final DelieverData mItem = mListData.get(i);

        if(mItem.isTitle){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_deliever_clock, null);

            ((TextView)view.findViewById(R.id.clock)).setText(mItem.deliveryTime.split(":")[0] + ":" + (mItem.deliveryTime.split(":")[1]));
        } else{
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_deliver_info, null);
            final DelieverActionInterface mInterface = (DelieverActionInterface) mContext;
            ((TextView) view.findViewById(R.id.phoneNum)).setText(mItem.phoneNumber);
            ((TextView) view.findViewById(R.id.addressStreet)).setText(mItem.simpleAddress);
            ((TextView) view.findViewById(R.id.address)).setText(mItem.address);
            view.findViewById(R.id.callContainer).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mInterface.callAt(mItem.phoneNumber);
                }
            });
            view.findViewById(R.id.naviContainer).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mInterface.launchNavi(mItem.simpleAddress,"" + mItem.latitude, "" + mItem.longitude);
                }
            });
        }
        return view;
    }

    public void cleanData(){
        mListData = new ArrayList<>();
    }

    public void addTitle(ArrayList<DelieverData> mData){
        String title = "";
        for(DelieverData delieverData : mData) {
            if(!title.equals(delieverData.deliveryTime)) {
                title = delieverData.deliveryTime;
                mListData.add(new DelieverData(title));
            }
            mListData.add(delieverData);
        }
    }
}
