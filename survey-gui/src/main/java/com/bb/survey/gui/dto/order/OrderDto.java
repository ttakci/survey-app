package com.bb.survey.gui.dto.order;

import org.springframework.hateoas.ResourceSupport;

public class OrderDto extends ResourceSupport {
	private Long userId;
	private String orderName;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

}
