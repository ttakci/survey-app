package com.bb.survey.gui.dto.order;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.bb.survey.dao.domain.Order;
import com.bb.survey.gui.controller.order.OrderController;

@Component
public class OrderResourceAssembler extends ResourceAssemblerSupport<Order, OrderDto> {

	public OrderResourceAssembler() {
        super(OrderController.class, OrderDto.class);
    }

    @Override
    public List<OrderDto> toResources(Iterable<? extends Order> orders) {
        List<OrderDto> resources = new ArrayList<OrderDto>();
        for(Order order : orders) {
        	resources.add(toResource(order));
        }
        return resources;
    }

    @Override
    public OrderDto toResource(Order order) {
    	OrderDto dto = new OrderDto();
    	dto.setUserId(order.getUserId());
    	dto.setOrderName(order.getOrderName());
    	dto.add(linkTo(OrderController.class).slash(order.getId()).withSelfRel());
    	    	
    	return dto;
    }

}
