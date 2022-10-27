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
@Table(name = "album")
public class Album {

    @Id
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "in_collection")
    private Boolean inCollection;

    @Column(name = "deluxe")
    private Boolean deluxe;

    @Column(name = "remaster")
    private Boolean remaster;

    @Column(name = "format")
    private String format;

    @Column(name = "music")
    private Integer music;

    @Column(name = "artist")
    private String artist;

    @Column(name = "master_release")
    private Boolean masterRelease;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "drummers_to_album",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "drummer_id")
    )
    private List<Drummer> drummers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Album that = (Album) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
