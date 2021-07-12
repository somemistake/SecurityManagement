package com.test.task.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "securities")
public class Security {
    @Column(name = "id")
    private Long id;
    @Id
    @Column(name = "secid")
    private String secid;
    @Column(name = "regnumber")
    private String regnumber;
    @Column(name = "name")
    private String name;
    @Column(name = "emitent_title")
    private String emitentTitle;

    @OneToMany(
            mappedBy = "security",
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REMOVE,
            },
            fetch = FetchType.LAZY
    )
    private Set<History> histories;

    public Security() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<History> getHistories() {
        return histories;
    }

    public void setHistories(Set<History> histories) {
        this.histories = histories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Security security = (Security) o;
        return Objects.equals(id, security.id) &&
                Objects.equals(secid, security.secid) &&
                Objects.equals(regnumber, security.regnumber) &&
                Objects.equals(name, security.name) &&
                Objects.equals(emitentTitle, security.emitentTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, secid, regnumber, name, emitentTitle);
    }
}
