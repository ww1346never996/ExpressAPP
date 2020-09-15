package com.example.expressapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

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

public class CellInfoActivity extends AppCompatActivity {
    private Button bigDetail_btn;
    private Button middleDetail_btn;
    private Button smallDetail_btn;
    private Button tinyDetail_btn;
    private ProgressBar bigCell_pb;
    private ProgressBar middleCell_pb;
    private ProgressBar smallCell_pb;
    private ProgressBar tinyCell_pb;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private String type;
    private String cabNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cellinfo);
        bigCell_pb = findViewById(R.id.bigcell_pb);
        middleCell_pb = findViewById(R.id.middlecell_pb);
        smallCell_pb = findViewById(R.id.smallcell_pb);
        tinyCell_pb = findViewById(R.id.tinycell_pb);
        bigDetail_btn = findViewById(R.id.bigdetail_btn);
        middleDetail_btn = findViewById(R.id.middledetail_btn);
        smallDetail_btn = findViewById(R.id.smalldetail_btn);
        tinyDetail_btn = findViewById(R.id.tinydetail_btn);
        listView = findViewById(R.id.cell_list);
        swipeRefreshLayout = findViewById(R.id.swipeCell_ly);
        //调用接口获取信息
        final MyHttpConnection conn = new MyHttpConnection();
        final CellListJson cellListJson = new CellListJson();
        final Intent intentFormNum = getIntent();
        cabNum = intentFormNum.getStringExtra("numCab");
        GlobalData.setCabNum(cabNum);
        if (!cabNum.equals("")){
            final String content = cellListJson.setCellListJson(cabNum);
            String result = conn.getJson(GlobalData.basic_path + "9002/capp/cabinet/allcell_list", content);
            //解析json,获取全部列表
            List<CellInfo> allList = cellListJson.parseCellListJson(result);
            //获取各类信息列表
            List<CellInfo> freeList = cellListJson.getFreeCellList(allList);
            List<CellInfo> bigList = cellListJson.getBigCellList(allList);
            List<CellInfo> middleList = cellListJson.getMiddleCellList(allList);
            List<CellInfo> smallList = cellListJson.getSmallCellList(allList);
            List<CellInfo> tinyList = cellListJson.getTinyCellList(allList);
            List<CellInfo> freeBigList = cellListJson.getFreeCellList(bigList);
            List<CellInfo> freeMiddleList = cellListJson.getFreeCellList(middleList);
            List<CellInfo> freeSmallList = cellListJson.getFreeCellList(smallList);
            List<CellInfo> freeTinyList = cellListJson.getFreeCellList(tinyList);
            //显示进度
            bigCell_pb.setProgress(100 * freeBigList.size() / bigList.size());
            middleCell_pb.setProgress(100 * freeMiddleList.size() / middleList.size());
            smallCell_pb.setProgress(100 * freeSmallList.size() / smallList.size());
            tinyCell_pb.setProgress(100 * freeTinyList.size() / tinyList.size());
            //设置按钮
            bigDetail_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    type = "big";
                    //调取api
                    final String content = cellListJson.setCellListJson(cabNum);
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
                    CellListAdapter adapter = new CellListAdapter(CellInfoActivity.this,R.layout.celllist_item,targetList);
                    listView.setAdapter(adapter);
                    //设置下拉刷新
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            String Rresult = conn.getJson(GlobalData.basic_path + "9002/capp/cabinet/allcell_list", content);
                            //解析json,获取全部列表
                            List<CellInfo> RallList = cellListJson.parseCellListJson(Rresult);
                            //获取各类信息列表
                            List<CellInfo> RbigList = cellListJson.getBigCellList(RallList);
                            List<CellInfo> RmiddleList = cellListJson.getMiddleCellList(RallList);
                            List<CellInfo> RsmallList = cellListJson.getSmallCellList(RallList);
                            List<CellInfo> RtinyList = cellListJson.getTinyCellList(RallList);
                            List<CellInfo> RfreeBigList = cellListJson.getFreeCellList(RbigList);
                            List<CellInfo> RfreeMiddleList = cellListJson.getFreeCellList(RmiddleList);
                            List<CellInfo> RfreeSmallList = cellListJson.getFreeCellList(RsmallList);
                            List<CellInfo> RfreeTinyList = cellListJson.getFreeCellList(RtinyList);
                            //显示进度
                            bigCell_pb.setProgress(100 * RfreeBigList.size() / RbigList.size());
                            middleCell_pb.setProgress(100 * RfreeMiddleList.size() / RmiddleList.size());
                            smallCell_pb.setProgress(100 * RfreeSmallList.size() / RsmallList.size());
                            tinyCell_pb.setProgress(100 * RfreeTinyList.size() / RtinyList.size());
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
                            CellListAdapter rAdapter = new CellListAdapter(CellInfoActivity.this,R.layout.celllist_item,rTargetList);
                            listView.setAdapter(rAdapter);
                            if (!rAllList.isEmpty() && rAllList.get(0).getConnCode() == 0){
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    });
                }
            });
            middleDetail_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    type = "middle";
                    //调取api
                    final String content = cellListJson.setCellListJson(cabNum);
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
                    CellListAdapter adapter = new CellListAdapter(CellInfoActivity.this,R.layout.celllist_item,targetList);
                    listView.setAdapter(adapter);
                    //设置下拉刷新
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            String Rresult = conn.getJson(GlobalData.basic_path + "9002/capp/cabinet/allcell_list", content);
                            //解析json,获取全部列表
                            List<CellInfo> RallList = cellListJson.parseCellListJson(Rresult);
                            //获取各类信息列表
                            List<CellInfo> RbigList = cellListJson.getBigCellList(RallList);
                            List<CellInfo> RmiddleList = cellListJson.getMiddleCellList(RallList);
                            List<CellInfo> RsmallList = cellListJson.getSmallCellList(RallList);
                            List<CellInfo> RtinyList = cellListJson.getTinyCellList(RallList);
                            List<CellInfo> RfreeBigList = cellListJson.getFreeCellList(RbigList);
                            List<CellInfo> RfreeMiddleList = cellListJson.getFreeCellList(RmiddleList);
                            List<CellInfo> RfreeSmallList = cellListJson.getFreeCellList(RsmallList);
                            List<CellInfo> RfreeTinyList = cellListJson.getFreeCellList(RtinyList);
                            //显示进度
                            bigCell_pb.setProgress(100 * RfreeBigList.size() / RbigList.size());
                            middleCell_pb.setProgress(100 * RfreeMiddleList.size() / RmiddleList.size());
                            smallCell_pb.setProgress(100 * RfreeSmallList.size() / RsmallList.size());
                            tinyCell_pb.setProgress(100 * RfreeTinyList.size() / RtinyList.size());
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
                            CellListAdapter rAdapter = new CellListAdapter(CellInfoActivity.this,R.layout.celllist_item,rTargetList);
                            listView.setAdapter(rAdapter);
                            if (!rAllList.isEmpty() && rAllList.get(0).getConnCode() == 0){
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    });
                }
            });
            smallDetail_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    type = "small";
                    //调取api
                    final String content = cellListJson.setCellListJson(cabNum);
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
                    CellListAdapter adapter = new CellListAdapter(CellInfoActivity.this,R.layout.celllist_item,targetList);
                    listView.setAdapter(adapter);
                    //设置下拉刷新
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            String Rresult = conn.getJson(GlobalData.basic_path + "9002/capp/cabinet/allcell_list", content);
                            //解析json,获取全部列表
                            List<CellInfo> RallList = cellListJson.parseCellListJson(Rresult);
                            //获取各类信息列表
                            List<CellInfo> RbigList = cellListJson.getBigCellList(RallList);
                            List<CellInfo> RmiddleList = cellListJson.getMiddleCellList(RallList);
                            List<CellInfo> RsmallList = cellListJson.getSmallCellList(RallList);
                            List<CellInfo> RtinyList = cellListJson.getTinyCellList(RallList);
                            List<CellInfo> RfreeBigList = cellListJson.getFreeCellList(RbigList);
                            List<CellInfo> RfreeMiddleList = cellListJson.getFreeCellList(RmiddleList);
                            List<CellInfo> RfreeSmallList = cellListJson.getFreeCellList(RsmallList);
                            List<CellInfo> RfreeTinyList = cellListJson.getFreeCellList(RtinyList);
                            //显示进度
                            bigCell_pb.setProgress(100 * RfreeBigList.size() / RbigList.size());
                            middleCell_pb.setProgress(100 * RfreeMiddleList.size() / RmiddleList.size());
                            smallCell_pb.setProgress(100 * RfreeSmallList.size() / RsmallList.size());
                            tinyCell_pb.setProgress(100 * RfreeTinyList.size() / RtinyList.size());
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
                            CellListAdapter rAdapter = new CellListAdapter(CellInfoActivity.this,R.layout.celllist_item,rTargetList);
                            listView.setAdapter(rAdapter);
                            if (!rAllList.isEmpty() && rAllList.get(0).getConnCode() == 0){
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    });
                }
            });
            tinyDetail_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    type = "tiny";
                    //调取api
                    final String content = cellListJson.setCellListJson(cabNum);
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
                    CellListAdapter adapter = new CellListAdapter(CellInfoActivity.this,R.layout.celllist_item,targetList);
                    listView.setAdapter(adapter);
                    //设置下拉刷新
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            String Rresult = conn.getJson(GlobalData.basic_path + "9002/capp/cabinet/allcell_list", content);
                            //解析json,获取全部列表
                            List<CellInfo> RallList = cellListJson.parseCellListJson(Rresult);
                            //获取各类信息列表
                            List<CellInfo> RbigList = cellListJson.getBigCellList(RallList);
                            List<CellInfo> RmiddleList = cellListJson.getMiddleCellList(RallList);
                            List<CellInfo> RsmallList = cellListJson.getSmallCellList(RallList);
                            List<CellInfo> RtinyList = cellListJson.getTinyCellList(RallList);
                            List<CellInfo> RfreeBigList = cellListJson.getFreeCellList(RbigList);
                            List<CellInfo> RfreeMiddleList = cellListJson.getFreeCellList(RmiddleList);
                            List<CellInfo> RfreeSmallList = cellListJson.getFreeCellList(RsmallList);
                            List<CellInfo> RfreeTinyList = cellListJson.getFreeCellList(RtinyList);
                            //显示进度
                            bigCell_pb.setProgress(100 * RfreeBigList.size() / RbigList.size());
                            middleCell_pb.setProgress(100 * RfreeMiddleList.size() / RmiddleList.size());
                            smallCell_pb.setProgress(100 * RfreeSmallList.size() / RsmallList.size());
                            tinyCell_pb.setProgress(100 * RfreeTinyList.size() / RtinyList.size());
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
                            CellListAdapter rAdapter = new CellListAdapter(CellInfoActivity.this,R.layout.celllist_item,rTargetList);
                            listView.setAdapter(rAdapter);
                            if (!rAllList.isEmpty() && rAllList.get(0).getConnCode() == 0){
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    });
                }
            });
        }
    }
}
