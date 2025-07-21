package com.example.demo.service.impl;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.demo.service.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Autowired
	public AmazonS3 amazonS3;
	
	@Value("${aws.s3.bucket.images}")
	private String imagesBucket;
	
	@Override
	public Boolean uploadFileS3(MultipartFile file) {
		
		String bucketName = null;
		try {
			bucketName = imagesBucket;
			String fileName = file.getOriginalFilename();
			InputStream inputStream = file.getInputStream();
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(file.getContentType());
			objectMetadata.setContentLength(file.getSize());
			
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,fileName,inputStream,objectMetadata);
			PutObjectResult saveData = amazonS3.putObject(putObjectRequest);
			if(!ObjectUtils.isEmpty(saveData)) {
				return true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
}
