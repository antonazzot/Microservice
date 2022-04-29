package com.tsyrkunou.StorageService.repository;

import com.tsyrkunou.StorageService.model.StorageSong;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository <StorageSong, Integer> {
}
