package com.saber.site.model;

import com.saber.site.validations.NotBlank;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TicketForm {
    private String subject;
    private String body;
    private List<MultipartFile> attachments ;

    @NotBlank(message = "{validate.ticketForm.subject}")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @NotBlank(message = "{validate.ticketForm.body}")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    @NotNull(message = "{validate.ticketForm.attachments}")
    public List<MultipartFile> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<MultipartFile> attachments) {
        this.attachments = attachments;
    }
}
