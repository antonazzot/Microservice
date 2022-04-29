package com.tsyrkunou.StorageService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


@Service
@ConfigurationProperties(prefix = "bucketnamelist")
public class RandomBucketService {
    @Value("#{'${bucketnamelist}'.split(',')}")
    private List<String> bucketnamelist;

    public String getBucket() {
        Random random = new Random();
        return bucketnamelist.get(random.nextInt(0, bucketnamelist.size()));
    }
}
