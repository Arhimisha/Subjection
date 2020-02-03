package com.arhimisha.intech.dto;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(generator = "MESSAGE_GENERATOR", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "MESSAGE_GENERATOR", allocationSize = 1, sequenceName = "message_id_seq")
    @Column(name = "id")
    private long id;

    @Column(name = "text_message")
    private String text;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Calendar creationDate;

    @Column(name = "deleted")
    private boolean deleted;

    public Message(long id, String text, Subject subject, User author, Calendar creationDate, boolean deleted) {
        this.id = id;
        this.text = text;
        this.subject = subject;
        this.author = author;
        this.creationDate = creationDate;
        this.deleted = deleted;
    }

    public Message() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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
