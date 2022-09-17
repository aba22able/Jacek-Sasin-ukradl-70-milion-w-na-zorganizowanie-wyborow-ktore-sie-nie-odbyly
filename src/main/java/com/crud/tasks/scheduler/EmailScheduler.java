package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailScheduler {

    private static final String SUBJECT = "Tasks: Once a day email";
    private final SimpleEmailService simpleEmailService;
    private final TaskRepository taskRepository;
    private final AdminConfig adminConfig;
    private final JavaMailSender javaMailSender;

    @Scheduled(cron = "0 0 10 * * *")
//    @Scheduled(fixedDelay = 10000)
    public void sendInformationEmail() {
        long size = taskRepository.count();
        String quantityName = "tasks";

        if(size == 1){
            quantityName = "task";
        }

        simpleEmailService.send(
                new Mail(
                        adminConfig.getAdminMail(),
                        SUBJECT,
                        "Currently in database you got: " + size + " " + quantityName,
                        null
                )
            );
    }

    @Scheduled(cron = "0 0 59 * * *")
    public void sendDailyReminder(final Mail mail)
    {
        long size = taskRepository.count();

        simpleEmailService.sendDailyMail(new Mail(
                        adminConfig.getAdminMail(),
                        SUBJECT,
                        "Number of your tasks: " + size + ".",
                        null
                )
        );
    }
}