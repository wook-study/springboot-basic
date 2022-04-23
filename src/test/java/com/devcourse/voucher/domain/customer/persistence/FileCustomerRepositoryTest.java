package com.devcourse.voucher.domain.customer.persistence;

import com.devcourse.voucher.domain.customer.entity.Customer;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.devcourse.voucher.fixture.Fixture.createCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class FileCustomerRepositoryTest {
    private final static String PATH = "data/";

    private final FileCustomerRepository fileCustomerRepository = new FileCustomerRepository(PATH);

    @BeforeEach
    void setUp() {
        clear();
    }


    @AfterEach
    void tearDown() {
        clear();
    }

    private void clear() {
        final File filePath = new File(PATH);

        if (!filePath.exists()) {
            filePath.mkdir();
        }

        val files = filePath.listFiles();

        for (File file : files) {
            file.delete();
        }
    }

    @Test
    void 고객_생성_성공() {
        val customer = fileCustomerRepository.save(createCustomer());

        val foundCustomer = fileCustomerRepository.findById(customer.getId()).orElseThrow();

        assertThat(foundCustomer).isEqualTo(customer);
    }

    @Test
    void 중복된_이메일_있으면_생성_실패() {
        fileCustomerRepository.save(createCustomer());

        assertThatIllegalArgumentException().isThrownBy(() -> fileCustomerRepository.save(createCustomer()));
    }

    @Test
    void 전체_고객_조회() {
        val customer1 = fileCustomerRepository.save(new Customer("customer1", "customer1@gmail.com"));
        val customer2 = fileCustomerRepository.save(new Customer("customer2", "customer2@gmail.com"));
        val customer3 = fileCustomerRepository.save(new Customer("customer3", "customer3@gmail.com"));
        val customer4 = fileCustomerRepository.save(new Customer("customer4", "customer4@gmail.com"));
        val customer5 = fileCustomerRepository.save(new Customer("customer5", "customer5@gmail.com"));

        val customers = fileCustomerRepository.findAll();

        assertThat(customers).containsExactlyInAnyOrder(customer1, customer2, customer3, customer4, customer5);
    }
}