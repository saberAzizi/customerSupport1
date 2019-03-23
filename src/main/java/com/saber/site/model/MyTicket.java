package com.saber.site.model;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ticket")
@Indexed
public class MyTicket implements Serializable {

    private long id;
    private String subject;
    private String body;
    private Date dateCreated;
    private UserPrincipal userPrincipal;
    private List<Attachment> attachments = new ArrayList<>();

    @Id
    @DocumentId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "subject", nullable = false)
    @Field
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Lob
    @Column(name = "body", nullable = false)
    @Field
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Basic
    @Column(name = "date_created", nullable = false)
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    public UserPrincipal getUserPrincipal() {
        return userPrincipal;
    }

    public void setUserPrincipal(UserPrincipal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinTable(name = "ticket_attachment",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id"))
    @OrderColumn(name = "sortkey")
    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void addAttachment(Attachment attachment){
        this.attachments.add(attachment);
    }
}
