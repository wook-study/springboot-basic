package com.devcourse.voucher.domain.customer.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

@Getter
@EqualsAndHashCode(of = "id")
public class Customer implements Serializable {
    /* 고객 아이디 */
    private final UUID id = UUID.randomUUID();

    /* 고객 이름 */
    private String name;

    /* 고객 이메일 */
    private String email;

    /* 고객 상태 */
    private CustomerStatus status = CustomerStatus.CREATED;

    public Customer(String name, String email) {
        checkArgument(Strings.isNotBlank(name), "이름 필수");
        checkArgument(Strings.isNotBlank(email), "이메일 필수");

        this.name = name;
        this.email = email;
    }

    public void block() {
        checkState(this.status == CustomerStatus.CREATED, "차단 불가 상태");

        this.status = CustomerStatus.BLOCKED;
    }
}
