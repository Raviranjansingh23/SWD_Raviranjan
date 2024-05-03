package com.taskmaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.taskmaster.entity.Epic;
import com.taskmaster.enums.Status;

@Repository
@Transactional(readOnly = true)
public interface EpicRepo extends JpaRepository<Epic, Long> {

	List<Epic> findByStatus(Status status);

}
