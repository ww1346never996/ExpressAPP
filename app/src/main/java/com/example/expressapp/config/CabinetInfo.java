package com.example.expressapp.config;

import java.util.ArrayList;
import java.util.HashMap;

public class CabinetInfo {
    private int code;
    private String error;
    private String addr;
    private String name;
    private int bigCellCount;
    private int middleCellCount;
    private int smallCellCount;
    private int tinyCellCount;

    public int getBigCellCount() {
        return bigCellCount;
    }

    public void setBigCellCount(int bigCellCount) {
        this.bigCellCount = bigCellCount;
    }

    public int getMiddleCellCount() {
        return middleCellCount;
    }

    public void setMiddleCellCount(int middleCellCount) {
        this.middleCellCount = middleCellCount;
    }

    public int getSmallCellCount() {
        return smallCellCount;
    }

    public void setSmallCellCount(int smallCellCount) {
        this.smallCellCount = smallCellCount;
    }

    public int getTinyCellCount() {
        return tinyCellCount;
    }

    public void setTinyCellCount(int tinyCellCount) {
        this.tinyCellCount = tinyCellCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }


    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
}
