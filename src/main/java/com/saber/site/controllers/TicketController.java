package com.saber.site.controllers;

import com.saber.site.model.Attachment;
import com.saber.site.model.MyTicket;
import com.saber.site.model.TicketForm;
import com.saber.site.model.UserPrincipal;
import com.saber.site.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/ticket")
public class TicketController {
    private TicketService ticketService;

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping(value = "list")
     public String list(Map<String,Object> model, Principal principal){
        UserPrincipal userPrincipal = (UserPrincipal) principal;
        List<MyTicket> tickets = this.ticketService.getTickets(userPrincipal.getUserId());
        model.put("tickets",tickets);
        return "ticket/list";
    }
    @GetMapping(value = "createTicket")
    public String createTicket(Map<String,Object> model){
        model.put("ticketForm",new TicketForm());
        return "ticket/create";
    }
    @PostMapping(value = "createTicket")
    public ModelAndView createTicket(Map<String,Object> model, @Valid TicketForm ticketForm,
                                     Errors errors , Principal principal) throws IOException {
         if (errors.hasErrors()){
            model.put("errors",errors.getAllErrors());
            model.put("ticketForm",ticketForm);
            return new ModelAndView("ticket/create");
        }
        UserPrincipal userPrincipal = (UserPrincipal) principal;
        MyTicket ticket = new MyTicket();
        ticket.setSubject(ticketForm.getSubject());
        ticket.setBody(ticketForm.getBody());
        ticket.setUserPrincipal(userPrincipal);
        for (MultipartFile multipartFile : ticketForm.getAttachments()){
            Attachment attachment = new Attachment();
            attachment.setContentType(multipartFile.getContentType());
            attachment.setAttachmentName(multipartFile.getOriginalFilename());
            attachment.setContents(multipartFile.getBytes());
            ticket.addAttachment(attachment);
        }
        this.ticketService.saveTicket(ticket);
        return new ModelAndView(new RedirectView("/ticket/list",true,false));
    }
}
