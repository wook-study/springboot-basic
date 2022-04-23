package com.devcourse.voucher.domain.customer.entity;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.*;

class CustomerTest {

    @Test
    void 고객_생성_성공() {
        val customer = new Customer("우기", "woogie@gmail.com");

        assertThat(customer.getId()).isNotNull();
        assertThat(customer.getName()).isEqualTo("우기");
        assertThat(customer.getEmail()).isEqualTo("woogie@gmail.com");
        assertThat(customer.getStatus()).isEqualTo(CustomerStatus.CREATED);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 이름이_널이거나_공백만_있으면_생성_실패(String name) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Customer(name, "woogie@gmail.com"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 이메일이_널이거나_공백만_있으면_생성_실패(String email) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Customer("우기", email));
    }

    @Test
    void 고객_차단_성공() {
        val customer = new Customer("우기", "woogie@gmail.com");

        assertThat(customer.getStatus()).isEqualTo(CustomerStatus.CREATED);

        customer.block();

        assertThat(customer.getStatus()).isEqualTo(CustomerStatus.BLOCKED);
    }

    @Test
    void CREATED_상태가_아니면_실패() {
        val customer = new Customer("우기", "woogie@gmail.com");

        customer.block();

        assertThatIllegalStateException().isThrownBy(customer::block);
    }
}