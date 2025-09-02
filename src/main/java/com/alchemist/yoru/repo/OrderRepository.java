package com.alchemist.yoru.repo;

import com.alchemist.yoru.dto.OrderDto;
import com.alchemist.yoru.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
 
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
//    Optional<Order> findByOrderNo(String num);
    List<Order> findByTenantId(String id);
   Optional<Order> findByIdAndTenantId(long id, String tenantId);
    @Query(value = "SELECT COUNT(o.id) from Order o WHERE o.tenantId = :tenantId and o.isActive= true")
    long findOrdersPlaced(@Param("tenantId") String tenantId);


//    @Query(value="SELECT MAX(o.totalAmount) from order o")
//    long findMaxAmountFromOrder();

    @Query(value="SELECT SUM(o.totalAmount) from Order o WHERE o.tenantId = :tenantId and o.isActive= true")
    long findTotalAmount(@Param("tenantId") String tenantId);

//
//        @Query("SELECT oi.menuItem.name " +
//                "FROM Order o " +
//                "JOIN o.orderItems oi " +
//                "WHERE o.totalAmount = (SELECT MAX(o.totalAmount) FROM Order o) " +
//                "AND oi.amount = (SELECT MAX(oi.amount) FROM OrderItem oi)")
//        String findMenuItemNameWithMaxAmount();

}
