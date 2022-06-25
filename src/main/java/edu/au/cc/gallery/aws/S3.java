package edu.au.cc.gallery.aws;

import java.io.InputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

public class S3 {
    private static final Region region = Region.US_EAST_2;
    private S3Client client;

    public void connect() {
        client = S3Client.builder().region(region).build();
    }

    public void createBucket(String bucketName) {
	CreateBucketRequest createBucketRequest = CreateBucketRequest
	    .builder()
	    .bucket(bucketName)
	    .createBucketConfiguration(CreateBucketConfiguration.builder()
				       .locationConstraint(region.id())
				       .build())
	    .build();
	client.createBucket(createBucketRequest);
    }

    public void putObject(String bucketName, String key, byte[] value, String contentType) {
	PutObjectRequest por = PutObjectRequest.builder()
	    .bucket(bucketName)
	    .key(key)
	    .contentType(contentType)
	    .build();
	client.putObject(por, RequestBody.fromBytes(value));
    }

    public void deleteObject(String bucketName, String key) {
        DeleteObjectRequest deleteObject = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        client.deleteObject(deleteObject);

    }

    public static void demo() {
//	String bucketName = "edu.au.cc.image-gallery";
//	S3 s3 = new S3();
//	s3.connect();
//	s3.createBucket(bucketName);
//	s3.putObject(bucketName, "banana", "green");
    }
}
