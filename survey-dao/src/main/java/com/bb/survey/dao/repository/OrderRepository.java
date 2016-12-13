package com.bb.survey.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bb.survey.dao.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	public Page<Order> findByUserId(Pageable pageable, Long userId);
}
