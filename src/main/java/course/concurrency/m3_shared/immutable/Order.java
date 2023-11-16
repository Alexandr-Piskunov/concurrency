package course.concurrency.m3_shared.immutable;

import java.util.List;

public class Order implements ImmutableOrder {

    public enum Status { NEW, IN_PROGRESS, DELIVERED }

    private Long id;
    private List<Item> items;
    private PaymentInfo paymentInfo;
    private boolean isPacked;
    private Status status;

    public Order(List<Item> items, long id) {
        this.id = id;
        this.items = items;
        this.status = Status.NEW;
    }
    private Order(long id, List<Item> items, PaymentInfo paymentInfo, boolean packed, Status status) {
        this.id = id;
        this.items = items;
        this.paymentInfo = paymentInfo;
        this.isPacked = packed;
        this.status = status;
    }
    public Order withPaymentInfo(PaymentInfo paymentInfo){
        return new Order(this.id,
                this.items,
                paymentInfo,
                this.isPacked,
                Status.IN_PROGRESS);
    }

    public Order withPacked(boolean packed) {
        return new Order(this.id,
                this.items,
                this.paymentInfo,
                packed,
                Status.IN_PROGRESS);
    }

    public Order withStatus(Status status){
        return new Order(this.id,
                this.items,
                this.paymentInfo,
                this.isPacked,
                status);
    }

    public boolean checkStatus() {
        if (this.items != null && !this.items.isEmpty() && this.paymentInfo != null && this.isPacked) {
            return true;
        }
        return false;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public PaymentInfo getPaymentInfo() {
        return this.paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
        this.status = Status.IN_PROGRESS;
    }

    public boolean isPacked() {
        return this.isPacked;
    }

    public void setPacked(boolean packed) {
        isPacked = packed;
        this.status = Status.IN_PROGRESS;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
