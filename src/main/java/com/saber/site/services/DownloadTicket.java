package com.saber.site.services;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.View;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@SuppressWarnings("ALL")
public class DownloadTicket implements View {
    private final String contentType;
    private final String fileName ;
    private final byte[] contents;

    public DownloadTicket(String contentType, String fileName, byte[] contents) {
        this.contentType = contentType;
        this.fileName = fileName;
        this.contents = contents;
    }

    @Nullable
    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public void render(@Nullable Map<String, ?> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setContentType("application/octet-stream");
        response.setHeader("content-disposition","attachment;filename="+this.fileName);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(this.contents);
    }
}
