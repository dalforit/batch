package com.ip.manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.manager.entity.AdresseIPEntity;
import com.ip.manager.utils.enums.StatusEnum;

@Repository
public interface AdresseIPRepository extends JpaRepository<AdresseIPEntity, String> {

	List<AdresseIPEntity> findByStatus(StatusEnum status);

}
