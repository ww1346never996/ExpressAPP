package com.example.expressapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expressapp.R;

public class GetNumActivity extends AppCompatActivity {
    private EditText getnum_et;
    private String numCab;
    private Button getnum_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getnum);
        getnum_btn = findViewById(R.id.btn_getnum);
        getnum_et = findViewById(R.id.et_getnum);
        getnum_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numCab = getnum_et.getText().toString();
                if(!numCab.equals("")){
                    Intent intentToCell = new Intent(GetNumActivity.this, CellInfoActivity.class);
                    intentToCell.putExtra("numCab",numCab);
                    startActivity(intentToCell);
                }
                else{
                    Toast.makeText(GetNumActivity.this,"请输入柜体编号", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
