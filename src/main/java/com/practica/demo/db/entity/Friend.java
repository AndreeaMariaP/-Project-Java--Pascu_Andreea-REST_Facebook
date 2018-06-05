package com.practica.demo.db.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="friend", schema = "public")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name="friend_id")
    private Integer friendId;

    @Column(name="request_date")
    private Date requestDate;

    @Column(name="confirmed")
    private boolean confirmed=false;

    public Friend() {
    }

    public Friend(User user, Integer friendId, Date requestDate, boolean confirmed) {
        this.user = user;
        this.friendId = friendId;
        this.requestDate = requestDate;
        this.confirmed = confirmed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
