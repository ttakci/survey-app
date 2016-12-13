package com.bb.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bb.survey.dao.domain.Order;
import com.bb.survey.dao.repository.OrderRepository;
import com.bb.survey.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository repository;

	@Override
	public Order add(Order entity) {
		return repository.save(entity);
	}

	@Override
	public Order update(Long id, Order user) {
		if(!repository.exists(id)){
			return null;
		}
		user.setId(id);
		return repository.save(user);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public Order findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public Page<Order> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public boolean exists(Long id) {
		return repository.exists(id);
	}

	@Override
	public Page<Order> findByUserId(Pageable pageable, Long userId) {
		return repository.findByUserId(pageable, userId);
	}
}
