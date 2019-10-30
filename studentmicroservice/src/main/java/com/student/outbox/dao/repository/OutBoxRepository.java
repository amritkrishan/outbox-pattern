package com.student.outbox.dao.repository;

import com.student.outbox.dao.entity.OutBoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OutBoxRepository extends JpaRepository<OutBoxEntity, Integer> {

}