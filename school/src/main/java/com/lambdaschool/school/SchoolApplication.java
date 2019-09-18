package com.lambdaschool.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// if working with exception handling for endpoints, need to add @EnableWebMvc
// do not @EnableWebMvc if using Spring Boot App unless creating custom invalid endpoint handling

@EnableWebMvc
@SpringBootApplication
public class SchoolApplication
{

    public static void main(String[] args)
    {
        // capture application context in variable
        ApplicationContext ctx = SpringApplication.run(SchoolApplication.class, args);
        // Dispatcher decides which endpoint goes to which method
        // need to find specific bean inside the current application context
        // need to typecast ctx.getBean
        DispatcherServlet dispatcherServlet = (DispatcherServlet) ctx.getBean("dispatcherServlet");
        // we are setting our own exception throwing if not found (overwrite spring boot handling it)
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }

}
