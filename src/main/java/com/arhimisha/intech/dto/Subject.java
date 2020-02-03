package com.arhimisha.intech.dto;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(generator = "SUBJECT_GENERATOR", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SUBJECT_GENERATOR", allocationSize = 1, sequenceName = "subject_id_seq")
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Calendar creationDate;

    @Column(name = "deleted")
    private boolean deleted;

    public Subject(long id, String name, String description, User author, Calendar creationDate, boolean deleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.creationDate = creationDate;
        this.deleted = deleted;
    }

    public Subject() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
