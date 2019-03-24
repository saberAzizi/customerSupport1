package com.saber.site.controllers;

import com.saber.site.model.*;
import com.saber.site.services.DownloadTicket;
import com.saber.site.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.ConstraintViolationException;
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
           ticket.addAttachment(getAttachment(multipartFile));
        }
        this.ticketService.saveTicket(ticket);
        return new ModelAndView(new RedirectView("/ticket/list",true,false));
    }

    @GetMapping(value = "viewTicket",params = "ticketId")
    public ModelAndView viewTicket(Map<String,Object> model,
                                   @RequestParam(name = "ticketId")long ticketId, Pageable pageable){
        model.put("ticketCommentForm",new TicketCommentForm());
        MyTicket ticket = this.ticketService.getTicket(ticketId);
        model.put("ticket",ticket);
        model.put("ticketId",String.valueOf(ticketId));
        model.put("comments",this.ticketService.getComments(ticket,pageable));
        return new ModelAndView("ticket/view");
    }
    @GetMapping(value = "download/{attachmentId}")
    public View downloadViewTicket(@PathVariable(name = "attachmentId") long attachmentId) {
        Attachment attachment = ticketService.findAttachment(attachmentId);
        if (attachment==null){
            System.out.println("Attachment Doest Not Exist .......................\n\n\n");
            return new RedirectView("ticket/list",true,false);
        }
      return new DownloadTicket(attachment.getContentType(),attachment.getAttachmentName(),attachment.getContents());

    }
    @PostMapping(value = "addComment",params = "ticketId")
    public ModelAndView addComment(Map<String,Object> model,@Valid TicketCommentForm ticketCommentForm,
                                   Errors errors , Pageable pageable,
                                   @RequestParam(name = "ticketId")long ticketId,Principal principal
                                  ) throws IOException {
        if (errors.hasErrors()){
            model.put("errors",errors.getAllErrors());
           System.out.println("ERROR .................................................\n\n\n\n\n\n\n");
           return this.viewTicket(model,ticketId,pageable);
        }
        MyTicket ticket = this.ticketService.getTicket(ticketId);
        if (ticket==null){
            System.out.println("Ticket Does Not Exist ....................\n\n\n\n");
            return new ModelAndView(new RedirectView("ticket/list",true,false));
        }
        TicketComment ticketComment = new TicketComment();
        ticketComment.setBody(ticketCommentForm.getBody());
        for (MultipartFile multipartFile:ticketCommentForm.getAttachments()){
          ticketComment.addAttachment(getAttachment(multipartFile));
        }
        try {
            this.ticketService.saveTicketComment(ticketComment,ticket,principal);

        }catch (ConstraintViolationException ex){
            model.put("errors",ex.getConstraintViolations());
           return this.viewTicket(model,ticketId,pageable);
        }
        return new ModelAndView(new RedirectView("/ticket/viewTicket?ticketId="+ticketId,true,false));
    }

    private Attachment getAttachment(MultipartFile multipartFile) throws IOException {
        Attachment attachment= new Attachment();
        attachment.setContentType(multipartFile.getContentType());
        attachment.setAttachmentName(multipartFile.getOriginalFilename());
        attachment.setContents(multipartFile.getBytes());
        return attachment;
    }
}
