package com.bb.survey.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.bb.survey.dao.domain.Order;

public interface OrderService {

	public Order add(Order user);

	public Order update(Long id, Order book);

	public void delete(Long id);

	public boolean exists(Long id);

	public Order findById(Long id);

	public Page<Order> findAll(Pageable pageable);

	public Page<Order> findByUserId(Pageable pageable, Long userId);
}
