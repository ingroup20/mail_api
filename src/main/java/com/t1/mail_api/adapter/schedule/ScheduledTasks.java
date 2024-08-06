package com.t1.mail_api.adapter.schedule;

import com.t1.mail_api.adapter.schedule.in.EmailEntity;
import com.t1.mail_api.service.MailService;
import jakarta.mail.MessagingException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.cfg.CoercionInputShape.Array;

@Component
public class ScheduledTasks {

    private final MailService mailService;
     TemplateEngine templateEngine;


    @Autowired
    ScheduledTasks(MailService mailService,TemplateEngine templateEngine){
        this.mailService= mailService;
        this.templateEngine=templateEngine;
    }

    @Scheduled(cron = "${schedule.cron}")
    public void sendDailyReport() {
//        log.info("*** Start SendEverydayReport ***");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(); //計時

//        指定發送
        String orderEmail = "xxx@xxx";


        List<EmailEntity> emailList = new ArrayList<>(); //email列表
        emailList.add(new EmailEntity());
        emailList.add(new EmailEntity());
        ArrayList<String> emailArray= new ArrayList<>();

        for(EmailEntity emailEntity :emailList) {
            emailArray.add(emailEntity.getEmail());
        }

        Set<String> businessNoSet = new HashSet<>();//指定過濾businessNoSet列表
        businessNoSet.add("1");
        businessNoSet.add("2");

        if (businessNoSet != null && !businessNoSet.isEmpty()) {//指定過濾businessNoSet
            emailList = emailList.stream()
                    .filter(businessNo -> businessNoSet.contains(businessNo))
                    .collect(Collectors.toList());
        }


            String title = "信件主旨";
            Map<String, Object> mineMessage_1 = new HashMap<>();
            Map<String, Object> mineMessage_2 = new HashMap<>();
            String reportHtmlString = generateEveryReport("MailContentModule.ftl", mineMessage_1);


                try {
                    final String[] toMailArray = orderEmail == null ? emailArray.toArray() : orderEmail;
                    mailService.sendMail(toMailArray, title, reportHtmlString);
                } catch (MessagingException e) {
//                    log.error(e.getMessage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


            stopWatch.stop();
//        log.info("*** End SendEverydayReport, total time: " + stopWatch.getLastTaskTimeMillis() + " ***");
        }
    }


        public String generateEveryReport (String template, Map < String, Object > mineMessage){

            Context context = new Context(); //內容模板
            mineMessage.forEach(context::setVariable); //mineMessage套入對應內容模板
            return templateEngine.process(template, context);//渲染模板
        }

        public String generateEveryReport (Map < String, Object > mineMessage){

            // 創建 Excel 工作簿和工作表
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Report");

            // 創建表頭
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Business Number");
            headerRow.createCell(1).setCellValue("Party Name");
            headerRow.createCell(2).setCellValue("Contact");

            // 填充數據
            int rowNum = 1;
            for (Object object : mineMessage) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(object.getBusinessNumber());
            }

            // 將工作簿寫入文件
            String filePath = "reportForm.xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            // 關閉工作簿
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return filePath;
        }



}
