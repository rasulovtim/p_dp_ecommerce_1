package com.gitlab.repository;

import com.gitlab.model.WorkingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingScheduleRepository extends JpaRepository<WorkingSchedule, Long> {
}
