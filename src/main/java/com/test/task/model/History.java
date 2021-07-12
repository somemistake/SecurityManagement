package com.test.task.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "histories")
public class History {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "tradedate")
    private Date tradedate;
    @Column(name = "numtrades")
    private Integer numtrades;
    @Column(name = "open")
    private Double open;
    @Column(name = "close")
    private Double close;

    @ManyToOne
    @JoinColumn(name = "secid")
    private Security security;

    public History() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return Objects.equals(id, history.id) &&
                Objects.equals(tradedate, history.tradedate) &&
                Objects.equals(numtrades, history.numtrades) &&
                Objects.equals(open, history.open) &&
                Objects.equals(close, history.close) &&
                Objects.equals(security, history.security);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tradedate, numtrades, open, close, security);
    }
}
