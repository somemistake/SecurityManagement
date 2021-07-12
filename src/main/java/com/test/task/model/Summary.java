package com.test.task.model;

import java.sql.Date;
import java.util.Objects;

public class Summary {
    private String secid;
    private String regnumber;
    private String name;
    private String emitentTitle;
    private Date tradedate;
    private Integer numtrades;
    private Double open;
    private Double close;

    public Summary() {
    }

    public String getSecid() {
        return secid;
    }

    public void setSecid(String secid) {
        this.secid = secid;
    }

    public String getRegnumber() {
        return regnumber;
    }

    public void setRegnumber(String regnumber) {
        this.regnumber = regnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmitentTitle() {
        return emitentTitle;
    }

    public void setEmitentTitle(String emitentTitle) {
        this.emitentTitle = emitentTitle;
    }

    public Date getTradedate() {
        return tradedate;
    }

    public void setTradedate(Date tradedate) {
        this.tradedate = tradedate;
    }

    public Integer getNumtrades() {
        return numtrades;
    }

    public void setNumtrades(Integer numtrades) {
        this.numtrades = numtrades;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Summary summary = (Summary) o;
        return Objects.equals(secid, summary.secid) &&
                Objects.equals(regnumber, summary.regnumber) &&
                Objects.equals(name, summary.name) &&
                Objects.equals(emitentTitle, summary.emitentTitle) &&
                Objects.equals(tradedate, summary.tradedate) &&
                Objects.equals(numtrades, summary.numtrades) &&
                Objects.equals(open, summary.open) &&
                Objects.equals(close, summary.close);
    }

    @Override
    public int hashCode() {
        return Objects.hash(secid, regnumber, name, emitentTitle, tradedate, numtrades, open, close);
    }
}
