package com.github.smurd.repository.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "drummer_sub")
public class DrummerSub {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_release_id")
    private Integer lastReleaseId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "drummer_x_user",
            joinColumns = @JoinColumn(name = "drummer_sub_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<TelegramUser> users;

    public void addUser(TelegramUser telegramUser) {
        if (isNull(users)) {
            users = new ArrayList<>();
        }
        users.add(telegramUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DrummerSub that = (DrummerSub) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
