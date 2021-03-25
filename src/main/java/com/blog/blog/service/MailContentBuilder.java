package com.blog.blog.service;

import lombok.AllArgsConstructor;
import org.hibernate.sql.Template;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
@AllArgsConstructor
public class MailContentBuilder {


    private final TemplateEngine templateEngine;

    private String build(String message)
    {
        Context context =new Context();
        context.setVariable("message",message);
        return templateEngine.process("mailTemplate",context);

    }
}
