package com.github.smurd.repository.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "drummers")
public class Drummer {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "born")
    private Date born;

    @Column(name = "died")
    private Date died;

    @Column(name = "live")
    private Boolean live;

    @ManyToMany(mappedBy = "drummers", fetch = FetchType.EAGER)
    private List<Album> albums;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Drummer that = (Drummer) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
