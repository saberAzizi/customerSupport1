package com.saber.site.repositories;

import com.saber.site.model.MyTicket;
import com.saber.site.model.TicketComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TicketCommentRepository extends CrudRepository<TicketComment,Long> {
    Page<TicketComment> findALLByTicket(MyTicket ticket, Pageable pageable);
}
