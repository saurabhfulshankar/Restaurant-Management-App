package com.alchemist.yoru.repo;

import com.alchemist.yoru.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository <MenuItem,Long>{
    List<MenuItem> findByNameContainsAndTenantId(String name,String tenantId);

    List<MenuItem> findByTenantId(String id);

    Optional<MenuItem> findByIdAndTenantId(long id, String tenantId);

    List<MenuItem> findByPriceLessThanAndTenantId(double price,String tenantId);


//    @Query(value = "SELECT m.name " +
//            "FROM MenuItem m " +
//            "JOIN OrderItem o ON m.id = o.menu_item_id " +
//            "ORDER BY m.price DESC " +
//            "LIMIT 1", nativeQuery = true)
//    String findMaxAmountDish();

    @Query(value = "SELECT m.name FROM menu_items m JOIN OrderItem o ON m.id = o.menu_item_id WHERE m.tenantId =:tenantId and m.isActive= true and o.isActive= true and " +
            "price = (SELECT max(price) FROM menu_items m JOIN OrderItem o ON m.id = o.menu_item_id WHERE o.tenantId = :tenantId " +
            "and m.isActive= true and o.isActive= true order by o.quantity desc LIMIT 1) LIMIT 1"
            , nativeQuery = true)
    String findMaxAmountDish(@Param("tenantId") String tenantId);

}
