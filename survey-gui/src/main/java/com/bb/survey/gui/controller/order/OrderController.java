package com.bb.survey.gui.controller.order;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bb.survey.dao.domain.Order;
import com.bb.survey.gui.dto.order.OrderDto;
import com.bb.survey.gui.dto.order.OrderResourceAssembler;
import com.bb.survey.service.OrderService;

@Controller
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
    private ReloadableResourceBundleMessageSource messageSource;
		
	@Autowired
	private OrderService service;

	@Autowired
	private OrderResourceAssembler orderResourceAssembler;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<PagedResources<OrderDto>> findAll(
			@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "count", defaultValue = "10", required = false) int count,
			@RequestParam(value = "order", defaultValue = "DESC", required = false) Sort.Direction direction,
			@RequestParam(value = "sort", defaultValue = "id", required = false) String sortProperty,
			PagedResourcesAssembler<Order> assembler) {
		Page<Order> result = service.findAll(new PageRequest(page, count, new Sort(direction, sortProperty)));

		if (CollectionUtils.isEmpty(result.getContent())) {
			return new ResponseEntity<PagedResources<OrderDto>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<PagedResources<OrderDto>>(assembler.toResource(result, orderResourceAssembler), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/{userid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<PagedResources<OrderDto>> findByUserId(
			@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "count", defaultValue = "10", required = false) int count,
			@RequestParam(value = "order", defaultValue = "DESC", required = false) Sort.Direction direction,
			@RequestParam(value = "sort", defaultValue = "id", required = false) String sortProperty, @PathVariable("userid") Long userId,
			PagedResourcesAssembler<Order> assembler) {
		Page<Order> result = service.findByUserId(new PageRequest(page, count, new Sort(direction, sortProperty)), userId);

		if (CollectionUtils.isEmpty(result.getContent())) {
			return new ResponseEntity<PagedResources<OrderDto>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<PagedResources<OrderDto>>(assembler.toResource(result, orderResourceAssembler), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<OrderDto> add(@Valid @RequestBody Order user) {
		try {
			return new ResponseEntity<OrderDto>(orderResourceAssembler.toResource(service.add(user)), HttpStatus.CREATED);
		} catch (DuplicateKeyException e) {
			return new ResponseEntity<OrderDto>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<OrderDto> update(@PathVariable("id") Long id, @RequestBody Order user) {
		Order updatedRecord = service.update(id, user);
		if (updatedRecord == null) {
			return new ResponseEntity<OrderDto>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<OrderDto>(orderResourceAssembler.toResource(updatedRecord), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<OrderDto> delete(@PathVariable("id") Long id) {
		if (!service.exists(id)) {
			return new ResponseEntity<OrderDto>(HttpStatus.NOT_FOUND);
		}
		service.delete(id);
		return new ResponseEntity<OrderDto>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<OrderDto> findById(@PathVariable("id") Long id) {
		Order order = service.findById(id);
		if (order == null) {
			return new ResponseEntity<OrderDto>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<OrderDto>(orderResourceAssembler.toResource(order), HttpStatus.OK);
	}

	@RequestMapping(value = "/reloadmessages", method = RequestMethod.GET)
	public ResponseEntity showMainPage() {
		messageSource.clearCache();
		return new ResponseEntity(HttpStatus.OK);
	}	
}
