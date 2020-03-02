package com.test.resume.parser.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Favorite {

    @Id
    @GeneratedValue
    long favoriteId;

    @Column(name = "status")
    int status;

    public long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
