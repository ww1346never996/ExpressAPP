package com.example.expressapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.expressapp.R;
import com.example.expressapp.config.CellInfo;
import com.example.expressapp.config.GlobalData;
import com.example.expressapp.utils.CellListAdapter;
import com.example.expressapp.utils.CellListJson;
import com.example.expressapp.utils.MyHttpConnection;

import java.util.ArrayList;
import java.util.List;

public class CellInfoDetailActivity extends AppCompatActivity {
    private String type;
    private String cabNum;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cellinfodetail);
        //从上一个activity获取数据
        final Intent intentFormCell = getIntent();
        type = intentFormCell.getStringExtra("type");
        cabNum = GlobalData.getCabNum();
        //调取api
        final MyHttpConnection conn = new MyHttpConnection();
        final CellListJson cellListJson = new CellListJson();
        final String content = cellListJson.setCellListJson(GlobalData.getCabNum());
        final String result = conn.getJson(GlobalData.basic_path + "9002/capp/cabinet/allcell_list",content);
        //解析json,获取全部列表
        List<CellInfo> allList = cellListJson.parseCellListJson(result);
        List<CellInfo> targetList = new ArrayList<CellInfo>();
        if (type.equals("big")){
            targetList = cellListJson.getFreeCellList(cellListJson.getBigCellList(allList));
        }else if (type.equals("middle")){
            targetList = cellListJson.getFreeCellList(cellListJson.getMiddleCellList(allList));
        }else if (type.equals("small")){
            targetList = cellListJson.getFreeCellList(cellListJson.getSmallCellList(allList));
        }else if (type.equals("tiny")){
            targetList = cellListJson.getFreeCellList(cellListJson.getTinyCellList(allList));
        }
        listView = findViewById(R.id.cell_list);
        swipeRefreshLayout = findViewById(R.id.swipeCell_ly);
        //设置listview
        CellListAdapter adapter = new CellListAdapter(CellInfoDetailActivity.this,R.layout.celllist_item,targetList);
        listView.setAdapter(adapter);
        //设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String rResult = conn.getJson(GlobalData.basic_path + "9002/capp/cabinet/allcell_list",content);
                List<CellInfo> rAllList = cellListJson.parseCellListJson(rResult);
                List<CellInfo> rTargetList = new ArrayList<CellInfo>();
               if (type.equals("big")){
                    rTargetList = cellListJson.getFreeCellList(cellListJson.getBigCellList(rAllList));
                }else if (type.equals("middle")){
                    rTargetList = cellListJson.getFreeCellList(cellListJson.getMiddleCellList(rAllList));
                }else if (type.equals("small")){
                    rTargetList = cellListJson.getFreeCellList(cellListJson.getSmallCellList(rAllList));
                }else if (type.equals("tiny")){
                    rTargetList = cellListJson.getFreeCellList(cellListJson.getTinyCellList(rAllList));
                }
                CellListAdapter rAdapter = new CellListAdapter(CellInfoDetailActivity.this,R.layout.celllist_item,rTargetList);
                listView.setAdapter(rAdapter);
                if (!rAllList.isEmpty() && rAllList.get(0).getConnCode() == 0){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }
}
