package com.saber.site.services;

import com.saber.site.model.Attachment;
import com.saber.site.model.MyTicket;
import com.saber.site.model.TicketComment;
import com.saber.site.model.UserPrincipal;
import com.saber.site.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private TicketCommentRepository ticketCommentRepository;
    private TicketRepository ticketRepository;
    private UserRepository userRepository;
    private AttachmentRepository attachmentRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAttachmentRepository(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @Autowired
    public void setTicketCommentRepository(TicketCommentRepository ticketCommentRepository) {
        this.ticketCommentRepository = ticketCommentRepository;
    }

    @Autowired
    public void setTicketRepository(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    @Transactional
    public Page<TicketComment> getComments(MyTicket ticket, Pageable pageable) {
        return this.ticketCommentRepository.findByTicket(ticket,pageable);
    }

    @Override
    @Transactional
    public void saveTicketComment(TicketComment ticketComment, MyTicket ticket, Principal principal) {
       try {
           if (ticketComment.getId()<1){
               ticketComment.setTicket(ticket);
             ticketComment.setDateCreated(new Date());
             ticketComment.setUserPrincipal(this.userRepository.findByUsername(principal.getName()));
           }
           this.ticketCommentRepository.save(ticketComment);
       }catch (Exception ex){
           ex.printStackTrace();
       }

    }

    @Override
    @Transactional
    public Page<SearchResult<MyTicket>> search(String query, Pageable pageable) {
        return this.ticketRepository.search(query,pageable);
    }

    @Override
    @Transactional
    public List<MyTicket> getTickets(long userId) {
        Optional<UserPrincipal> userPrincipalOptional = this.userRepository.findById(userId);
        UserPrincipal userPrincipal = userPrincipalOptional.orElse(null);
        if (userPrincipal==null){
            return null;
        }
        return this.ticketRepository.findAllByUserPrincipal(userPrincipal);
    }

    @Override
    @Transactional
    public void saveTicket(MyTicket ticket) {
        if (ticket.getId()<1){
           ticket.setDateCreated(new Date());
        }
         this.ticketRepository.save(ticket);

    }

    @Override
    @Transactional
    public MyTicket getTicket(long ticketId) {
        Optional<MyTicket> ticketOptional = this.ticketRepository.findById(ticketId);
        return ticketOptional.orElse(null);
    }

    @Override
    @Transactional
    public Attachment findAttachment(long attachmentId) {
        Optional<Attachment> attachmentOptional = this.attachmentRepository.findById(attachmentId);
        return attachmentOptional.orElse(null);
    }

    @Override
    @Transactional
    public UserPrincipal findUserByUsername(String username) {
       return this.userRepository.findByUsername(username);

    }
}
