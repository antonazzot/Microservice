package com.tsyrkunou.StorageService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Proxy(lazy = false)
@Table(name = "storage")
public class StorageSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "storagetype")
    private StorageType storageType;
    @Column(name = "bucketname")
    private String bucketName;
    @Column(name = "filepath")
    private String filePath;
    @Column(name = "filename")
    private String fileName;
    @Column(name = "storagedate")
    private Date storageDate;

}
