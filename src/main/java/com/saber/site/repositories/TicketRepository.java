package com.saber.site.repositories;

import com.saber.site.model.MyTicket;
import com.saber.site.model.UserPrincipal;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketRepository extends CrudRepository<MyTicket,Long>,SearchableRepository<MyTicket> {
    List<MyTicket> findAllByUserPrincipal(UserPrincipal userPrincipal);
}
