package com.rentals.apartment.domain;

import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tokens")
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_id_seq")
    @SequenceGenerator(name = "token_id_seq", sequenceName = "token_id_seq", allocationSize = 1)
    private Long id;
    private String token;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expired_at")
    private Date expiredAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public TokenEntity(String token, Date expiredAt, UserEntity user) {
        this.token = token;
        this.createdAt = new Date();
        this.expiredAt = expiredAt;
        this.user = user;
    }

    public TokenEntity(String token, UserEntity user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
        Date oneHourFromNow = calendar.getTime();
        this.token = token;
        this.createdAt = new Date();
        this.expiredAt = oneHourFromNow;
        this.user = user;
    }

    public TokenEntity() {

    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public UserEntity getUserEntity() {
        return user;
    }

    public void setUserEntity(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenEntity that = (TokenEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(token, that.token) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(expiredAt, that.expiredAt) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, createdAt, expiredAt, user);
    }
}
