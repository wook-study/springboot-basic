package com.devcourse.voucher.domain.customer.persistence;

import com.devcourse.voucher.domain.customer.entity.Customer;
import com.devcourse.voucher.domain.customer.entity.CustomerRepository;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

public class MemoryCustomerRepository implements CustomerRepository {
    private final Map<UUID, Customer> customers = new LinkedHashMap<>();

    @Override
    public List<Customer> findAll() {
        return customers.values().stream().toList();
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return Optional.ofNullable(customers.get(id));
    }

    @Override
    public Customer save(Customer customer) {
        checkArgument(findAll().stream().noneMatch(it -> it.getEmail().equals(customer.getEmail())), "중복된 이메일");

        customers.put(customer.getId(), customer);

        return customer;
    }
}
