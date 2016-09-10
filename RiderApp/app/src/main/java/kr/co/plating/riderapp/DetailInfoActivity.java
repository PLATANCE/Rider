package kr.co.plating.riderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        Intent intent = getIntent();
        ArrayList<Menu> menus = (ArrayList<Menu>)intent.getSerializableExtra("menus");

        ListView listView = (ListView) findViewById(R.id.listView);
        MenuListAdapter menuAdapter = new MenuListAdapter(this,R.layout.item_munu,menus);
        listView.setAdapter(menuAdapter);

        Button completeButton = (Button) findViewById(R.id.complete);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doComplete();
            }
        });

    }

    private void doComplete() {
        Toast.makeText(this,"완료 합니다. 서버 붙이세요",Toast.LENGTH_LONG).show();
    }
}
