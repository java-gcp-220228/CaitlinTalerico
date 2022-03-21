package com.revature.utility;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.common.collect.Lists;
import io.javalin.http.UploadedFile;

import java.io.FileInputStream;
import java.io.IOException;


public class UploadImageUtility {
    private static String projectId = System.getenv("project_id");
    private static String bucketName = System.getenv("bucket_name");

    // Solution by Mani on StackOverflow
    //https://stackoverflow.com/questions/42893395/upload-image-to-google-cloud-storage-java
    public static String uploadImage(UploadedFile uploadedFile) throws IOException {
        Bucket bucket = getBucket(bucketName);
        Blob blob = bucket.create(uploadedFile.getFilename(), uploadedFile.getContent(), uploadedFile.getContentType());

        return "https://storage.googleapis.com/reimb-receipt-images/" + blob.getName();
    }

    private static Bucket getBucket(String bucketName) throws IOException {
        try{
            System.out.println("Here");
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(System.getenv("GOOGLE_APP_CREDENTIALS")))
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
            System.out.println(credentials);
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            System.out.println(storage);
            Bucket bucket = storage.get(bucketName);
            System.out.println(bucket);
            if (bucket == null) {
                throw new IOException("Bucket not found:"+bucketName);
            }
            return bucket;
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
        return null;
    }
}