package course.concurrency.m3_shared.immutable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class OrderService {

    private Map<Long, ImmutableOrder> currentOrders = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong(0);

    private long nextId() {
        return nextId.incrementAndGet();
    }

    public long createOrder(List<Item> items) {
        long id = nextId();
        ImmutableOrder order = new Order(items, id);
        currentOrders.put(id, order);
        return id;
    }

    public void updatePaymentInfo(long orderId, PaymentInfo paymentInfo) {
        var newImmutableOrder = currentOrders.computeIfPresent(orderId, (key, value) -> value.withPaymentInfo(paymentInfo));
        if (newImmutableOrder != null && newImmutableOrder.checkStatus()) {
            deliver(newImmutableOrder);
        }
    }

    public void setPacked(long orderId) {
        var newImmutableOrder = currentOrders.computeIfPresent(orderId, (key, value) -> value.withPacked(true));
        if (newImmutableOrder != null && newImmutableOrder.checkStatus()) {
            deliver(newImmutableOrder);
        }
    }

    private void deliver(ImmutableOrder order) {
        /* ... */
        currentOrders.computeIfPresent(order.getId(), (key, value) -> value.withStatus(Order.Status.DELIVERED));
    }

    public boolean isDelivered(long orderId) {
        return currentOrders.get(orderId).getStatus().equals(Order.Status.DELIVERED);
    }
}
