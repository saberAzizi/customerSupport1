package com.saber.site.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ticketComment")
public class TicketComment implements Serializable {
    private long id;
    private String body;
    private Date dateCreated;
    private MyTicket ticket;
    private List<Attachment> attachments = new ArrayList<>();
    private UserPrincipal userPrincipal;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    @Lob
    @Column(name = "body",nullable = false)
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Basic
    @Column(name = "date_created",nullable = false)
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticket_id")
    public MyTicket getTicket() {
        return ticket;
    }

    public void setTicket(MyTicket ticket) {
        this.ticket = ticket;
    }
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "ticketComment_attachment",
    joinColumns = @JoinColumn(name = "comment_id"),
    inverseJoinColumns = @JoinColumn(name = "attachment_id"))
    @OrderColumn(name = "sortkey")
    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    public UserPrincipal getUserPrincipal() {
        return userPrincipal;
    }

    public void setUserPrincipal(UserPrincipal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    public void addAttachment(Attachment attachment){
        this.attachments.add(attachment);
    }
}
