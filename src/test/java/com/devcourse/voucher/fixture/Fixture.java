package com.devcourse.voucher.fixture;

import com.devcourse.voucher.domain.customer.entity.Customer;

public class Fixture {

    public static Customer createCustomer() {
        return new Customer("우기", "woogie@gmail.com");
    }
}
