/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.commons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;
import com.google.common.io.Files;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * word文档工具
 * @author smile.jing
 * @version $Id: DocUtil.java, v 0.1 Feb 11, 2019 4:21:51 PM smile.jing Exp $
 */
public class DocUtil {
    /**
     * 根据模板路径，值生成word文档
     * @param templateName 模板名称
     * @param data 值
     * @return
     * @throws TemplateException 
     * @throws IOException 
     */
    public static File createDoc(String templateName, Map<String, Object> data) throws IOException, TemplateException {
        return createDoc(UUID.randomUUID().toString(), templateName, data);
    }

    /**
     * 根据模板路径，值生成word文档
     * @param docName word文档名称
     * @param templateName 模板名称
     * @param data 值
     * @return
     * @throws IOException 
     * @throws TemplateException 
     */
    public static File createDoc(String docName, String templateName, Map<String, Object> data) throws IOException, TemplateException {
        Configuration configuration = getConfiguration();
        Template template = configuration.getTemplate(templateName);
        File file = File.createTempFile(docName, ".doc");
        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        template.process(data, writer);
        writer.close();
        return file;
    }

    private static Configuration getConfiguration() throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(new File(DocUtil.class.getResource("/static/template").getPath()));
        return configuration;
    }

    public static void main(String[] args) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("head_year", "2019");
        data.put("number", "01");
        data.put("content", "金控会议纪要文件哈哈哈哈哈哈哈金控会议纪要文件哈哈哈哈哈哈哈金控会议纪要文件哈哈哈哈哈哈哈金控会议纪要文件哈哈哈哈哈哈哈金控会议纪要文件哈哈哈哈哈哈哈金控会议纪要文件哈哈哈哈哈哈哈"
                            + "\r   金控会议纪要文件哈哈哈哈哈哈哈金控会议纪要文件哈哈哈哈哈哈哈金控会议纪要文件哈哈哈哈哈哈哈金控会议纪要文件哈哈哈哈哈哈哈\r" + "     金控会议纪要文件哈哈哈哈哈哈哈");
        data.put("meeting_data", "2019年1月20日");
        try {
            File file = DocUtil.createDoc("金控红头文件", "meeting.xml", data);
            Files.copy(file, new File("/Users/jingyongxiang/project/orange/factoring/target/金控红头文件.doc"));
            System.out.println(file.getName());
        } catch (IOException | TemplateException e) {
            System.out.print(e.getMessage());
        }
        System.out.println(DocUtil.class.getResource("/static/template").getPath());
    }
}
