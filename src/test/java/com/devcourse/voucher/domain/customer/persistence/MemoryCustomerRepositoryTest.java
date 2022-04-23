package com.devcourse.voucher.domain.customer.persistence;

import com.devcourse.voucher.domain.customer.entity.Customer;
import lombok.val;
import org.junit.jupiter.api.Test;

import static com.devcourse.voucher.fixture.Fixture.createCustomer;
import static org.assertj.core.api.Assertions.*;

class MemoryCustomerRepositoryTest {

    private final MemoryCustomerRepository memoryCustomerRepository = new MemoryCustomerRepository();

    @Test
    void 고객_생성_성공() {
        val customer = memoryCustomerRepository.save(createCustomer());

        val foundCustomer = memoryCustomerRepository.findById(customer.getId()).orElseThrow();

        assertThat(foundCustomer).isEqualTo(customer);
    }

    @Test
    void 중복된_이메일_있으면_생성_실패() {
        memoryCustomerRepository.save(createCustomer());

        assertThatIllegalArgumentException().isThrownBy(() -> memoryCustomerRepository.save(createCustomer()));
    }

    @Test
    void 전체_고객_조회() {
        val customer1 = memoryCustomerRepository.save(new Customer("customer1", "customer1@gmail.com"));
        val customer2 = memoryCustomerRepository.save(new Customer("customer2", "customer2@gmail.com"));
        val customer3 = memoryCustomerRepository.save(new Customer("customer3", "customer3@gmail.com"));
        val customer4 = memoryCustomerRepository.save(new Customer("customer4", "customer4@gmail.com"));
        val customer5 = memoryCustomerRepository.save(new Customer("customer5", "customer5@gmail.com"));

        val customers = memoryCustomerRepository.findAll();

        assertThat(customers).containsExactly(customer1, customer2, customer3, customer4, customer5);
    }
}