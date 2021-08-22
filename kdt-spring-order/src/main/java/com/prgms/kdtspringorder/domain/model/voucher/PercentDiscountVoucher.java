package com.prgms.kdtspringorder.domain.model.voucher;

import java.util.UUID;

import com.prgms.kdtspringorder.adapter.exception.InvalidDiscountException;
import com.prgms.kdtspringorder.ui.message.ErrorMessage;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long discountPercent;
    private boolean isUsed = false;

    public PercentDiscountVoucher(UUID voucherId, long discountPercent) {
        this.voucherId = voucherId;
        validateDiscountPercent(discountPercent);
        this.discountPercent = discountPercent;
    }

    @Override
    public long discount(long beforeDiscountAmount) {
        return (long)(beforeDiscountAmount * (1L - (float)discountPercent / 100L));
    }

    @Override
    public void useVoucher() {
        if (isUsed) {
            throw new RuntimeException("이미 사용한 바우처입니다.");
        }
        isUsed = true;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscount() {
        return discountPercent;
    }

    private void validateDiscountPercent(long discountPercent) {
        if (discountPercent > 100) {
            throw new InvalidDiscountException(ErrorMessage.INVALID_DISCOUNT_PERCENT.getMessage());
        }
    }
}