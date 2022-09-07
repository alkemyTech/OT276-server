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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
    public String uploadFile(String imgBase64, String fileName) {
        String fileUrl;
        try {
            File file = decodeBase64(imgBase64, fileName);
            fileUrl = s3client.getUrl(bucketName, file.getName()).toString();
            uploadFileTos3bucket(file.getName(), file);
            file.delete();
        } catch (Exception e) {
            log.error("error uploading file", e);
            throw new RuntimeException(e);
        }
        return fileUrl;
    }

    private File decodeBase64(String imgBase64, String fileName) {

        try {
            String extension;
            String[] strings = imgBase64.split(",");
            byte[] data;

            if (strings[0].contains("data")) {

                switch (strings[0]) {
                    case "data:image/jpeg;base64":
                        extension = ".jpeg";
                        break;
                    case "data:image/png;base64":
                        extension = ".png";
                        break;
                    default:
                        extension = ".jpg";
                        break;
                }
                data = DatatypeConverter.parseBase64Binary(strings[1]);
            } else {
                char firstChar = imgBase64.charAt(0);
                switch (firstChar) {
                    case '/':
                        extension = ".jpeg";
                        break;
                    case 'i':
                        extension = ".png";
                        break;
                    default:
                        extension = ".jpg";
                        break;
                }
                data = DatatypeConverter.parseBase64Binary(imgBase64);
            }

            String fileFullName = fileName + extension;
            OutputStream out = new FileOutputStream(fileFullName);
            out.write(data);
            out.close();
            File file = new File(fileFullName);
            return file;

        } catch (Exception e) {
            log.error("error writing decode base64 img", e);
            throw new RuntimeException(e);
        }

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

