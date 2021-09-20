package org.prgrms.kdt.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long discount(long beforeDiscount);
    long getVoucherAmount();
    void setCustomer(String customerEmail);
    String getCustomerEmail();
    VoucherType getVoucherType();
}