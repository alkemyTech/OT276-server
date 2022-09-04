package com.alkemy.ong.ports.output.s3;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucketName;


    @Override
    public String uploadFile(MultipartFile multipartFile) {

        String fileUrl;
        try {

            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = s3client.getUrl(bucketName, fileName).toString();
            uploadFileTos3bucket(fileName, file);
            file.delete();

        } catch (Exception e) {

            log.error("error uploading file", e);
            throw new RuntimeException(e);
        }
        return fileUrl;
    }

    @Override
    public String uploadFile(String fileBase64, String fileName) {
        String fileUrl;
        byte[] data = DatatypeConverter.parseBase64Binary(fileBase64);
        File file = new File(fileName);
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(data);
            fileUrl = s3client.getUrl(bucketName, fileName).toString();
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            log.error("error uploading file", e);
            throw new RuntimeException(e);
        }


        return fileUrl;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
        return "Successfully deleted";
    }
}

