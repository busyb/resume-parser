package com.test.resume.parser.model;

import javax.persistence.*;

@Entity()
@Table(name="test_table")
public class Test {
    @Id
    @GeneratedValue
    private long id;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
