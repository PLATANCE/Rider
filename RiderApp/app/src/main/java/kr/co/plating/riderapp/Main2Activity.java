package kr.co.plating.riderapp;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements DelieverActionInterface {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        ((TabLayout) findViewById(R.id.tabs)).setupWithViewPager(mViewPager);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.refreshButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshData();
            }
        });

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };

        new TedPermission(this).setPermissionListener(permissionListener).setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE)
                .check();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void callAt(String phone) {
        String uri = "tel:" + phone;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

    @Override
    public void launchNavi(String address, String lt, String ln) {
        String kakaoURI = "kimgisa://navigate?key=b0f8361422d14078b96e795ddf4f3d60&coord_type=wgs84&name=" + address + "&pos_x=" + ln + "&pos_y=" + lt + "&fee=true&vehicle_type=1";

        Log.e("카카오 쒸빠", kakaoURI);

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(kakaoURI));
        i.setComponent(new ComponentName("com.locnall.KimGiSa", "com.locnall.KimGiSa.Engine.SMS.CremoteActivity"));
        startActivity(i);
    }

    public static class PlaceholderFragment extends Fragment implements RefreshInterface {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int position = getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
            if(position == 1){
                setDeliveringListData(rootView);
            }

            return rootView;
        }

        private void setDeliveringListData(View rootView) {

            //TODO SERVER API완성되면 서버에서 데이터 가져오기
            Gson gson = new Gson();
            Type typeToken = new TypeToken<ArrayList<DelieverData>>() {
            }.getType();

            JsonReader jsonReader = null;
            try {
                jsonReader = new JsonReader(new InputStreamReader(getActivity().getAssets().open("orderInfo.json")));
            } catch (IOException e) {
                Log.e("씨빠 제이슨 잘못됫어", "ㅇ ㅈㅅ");
                return;
            }

            final ArrayList<DelieverData> delieverDatas = gson.fromJson(jsonReader, typeToken);
            final DelieverAdapter delieverAdapter = new DelieverAdapter(getActivity(), delieverDatas);
            ((ListView) rootView.findViewById(R.id.listView)).setAdapter(delieverAdapter);
            ((ListView) rootView.findViewById(R.id.listView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), DetailInfoActivity.class);
                    Log.e("포지쎤","" +  position);
                    if(((DelieverData)delieverAdapter.getItem(position)).isTitle) return;
                    intent.putExtra("menus",((DelieverData)delieverAdapter.getItem(position)).getMenus());
                    startActivity(intent);
                }
            });
        }

        @Override
        public void refresh() {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }
    }

    private void refreshData() {
        //TODO SERVER API완성되면 서버에서 데이터 가져오기
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.detach(mSectionsPagerAdapter.getItem(mViewPager.getCurrentItem())).attach(mSectionsPagerAdapter.getItem(mViewPager.getCurrentItem()));
        transaction.commit();

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "배달 목록";
                case 1:
                    return "완료 목록";

            }
            return null;
        }
    }
}
