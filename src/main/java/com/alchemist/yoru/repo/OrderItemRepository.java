package com.alchemist.yoru.repo;

import com.alchemist.yoru.Tenant.Context;
import com.alchemist.yoru.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{
    List<OrderItem> findByTenantId(String id);

  Optional<OrderItem> findByIdAndTenantId(long id, String tenantId);

    @Query(value = "SELECT m.name FROM menu_items m JOIN OrderItem o ON m.id = o.menu_item_id " +
            "WHERE o.quantity=(SELECT MAX(o.quantity) FROM menu_items m JOIN OrderItem o ON m.id = o.menu_item_id " +
            "where o.tenantId = :tenantId and m.isActive= true and o.isActive= true LIMIT 1)" +
            " and m.isActive= true and o.isActive= true LIMIT 1"
            , nativeQuery = true)
    String findFrequentlyOrderedDish(@Param("tenantId") String tenantId);

}
//value = "SELECT oi.menu_item_id,oi.order_id from OrderItem oi where(SELECT mi.name from MenuItem mi where oi.menu_item_id= mi.id ) AND (SELECT mi.name from MenuItem mi,Order o where o.id=oi.order_id AND MAX(mi.price)) "