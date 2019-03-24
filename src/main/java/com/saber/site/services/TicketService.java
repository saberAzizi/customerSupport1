package com.saber.site.services;

import com.saber.site.model.Attachment;
import com.saber.site.model.MyTicket;
import com.saber.site.model.TicketComment;
import com.saber.site.model.UserPrincipal;
import com.saber.site.repositories.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TicketService {
    Page<TicketComment> getComments(MyTicket ticket,Pageable pageable);
    void saveTicketComment(TicketComment ticketComment ,MyTicket ticket);
    ///-------------------------------------------------
    Page<SearchResult<MyTicket>> search(String query,Pageable pageable);
    List<MyTicket> getTickets(long userId);
    void saveTicket(MyTicket ticket);
    MyTicket getTicket(long ticketId);
    //----------------------------------------------------
    Attachment findAttachment(long attachmentId);
}
