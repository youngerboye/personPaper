package com.service;

import com.model.AttachmentConfig;
import com.repository.AttachmentConfigRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author: young
 * @project_name: upload-project
 * @description:
 * @date: Created in 2019-03-20  10:55
 * @modified by:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileUploadServiceTest {

    @Autowired
    private AttachmentConfigRepository attachmentConfigRepository;

    @Test
    public void upload() {
        AttachmentConfig attachmentConfig = attachmentConfigRepository.findByConfigTypeAndState(1, 0);

        System.out.println(attachmentConfig);
    }
}