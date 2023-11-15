package course.concurrency.m3_shared.immutable;

import java.util.List;

public interface ImmutableOrder {

    Long getId();

    List<Item> getItems();

    PaymentInfo getPaymentInfo();

    Order.Status getStatus();

    boolean checkStatus();

    boolean isPacked();
}
