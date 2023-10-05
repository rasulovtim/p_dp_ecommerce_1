package com.gitlab.service;

import com.gitlab.model.Example;
import com.gitlab.model.Order;
import com.gitlab.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void should_find_all_orders() {
        List<Order> expectedOrders = generateOrders();
        when(orderRepository.findAll()).thenReturn(generateOrders());
        List<Order> actualOrders = orderService.findAll();
        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    void should_find_order_by_id() {
        Long id = 1L;
        Order expectedOrder = generateOrder();
        when(orderRepository.findById(id)).thenReturn(Optional.of(generateOrder()));
        Optional<Order> actualOrder = orderService.findById(id);

        assertEquals(expectedOrder, actualOrder.orElse(null));
    }

    @Test
    void should_save_order() {
        Order expectedResult = generateOrder();
        when(orderRepository.save(expectedResult)).thenReturn(expectedResult);

        Order actualResult = orderService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_update_order() {
        long id = 1L;
        Order orderToUpdate = new Order();
        orderToUpdate.setOrderCode("updatesOrder");

        Order orderBeforeUpdate = new Order(id, "unmodifiedCode");
        Order updatedOrder = new Order(id, "updatesOrder");

        when(orderRepository.findById(id)).thenReturn(Optional.of(orderBeforeUpdate));
        when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);

        Optional<Order> actualResult = orderService.update(id, orderToUpdate);

        assertEquals(updatedOrder, actualResult.orElse(null));
    }

    @Test
    void should_not_update_order_when_entity_not_found() {
        long id = 1L;
        Order orderToUpdate = new Order();
        orderToUpdate.setOrderCode("updatesOrder");

        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Order> actualResult = orderService.update(id, orderToUpdate);

        verify(orderRepository, never()).save(any());
        assertNull(actualResult.orElse(null));
    }

    @Test
    void should_not_updated_orderCode_field_if_null() {
        long id = 1L;
        Order orderToUpdate = new Order();
        orderToUpdate.setOrderCode(null);

        Order orderBeforeUpdate = new Order(id, "unmodifiedCode");


        when(orderRepository.findById(id)).thenReturn(Optional.of(orderBeforeUpdate));
        when(orderRepository.save(orderBeforeUpdate)).thenReturn(orderBeforeUpdate);

        Optional<Order> actualResult = orderService.update(id, orderToUpdate);

        verify(orderRepository).save(orderBeforeUpdate);
        assertEquals(orderBeforeUpdate, actualResult.orElse(null));
        assertEquals("unmodifiedCode", orderBeforeUpdate.getOrderCode());
    }

    @Test
    void should_delete_order() {
        long id = 1L;
        when(orderRepository.findById(id)).thenReturn(Optional.of(generateOrder()));

        orderService.delete(id);

        verify(orderRepository).deleteById(id);
    }

    @Test
    void should_not_delete_order_when_entity_not_found() {
        long id = 1L;
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        orderService.delete(id);

        verify(orderRepository, never()).deleteById(anyLong());
    }


    private List<Order> generateOrders() {
        return List.of(
                new Order(1L),
                new Order(2L),
                new Order(3L),
                new Order(4L),
                new Order(5L),
                new Order(6L)
        );
    }

    private Order generateOrder() {
        return new Order(1L);
    }

}
