package com.example.expressapp.config;

import java.util.List;

public class CellInfo {
    private int code;
    private int status;
    private int type;
    private int connCode;

    public int getConnCode() {
        return connCode;
    }

    public void setConnCode(int connCode) {
        this.connCode = connCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
