package lk.lnas.ims.repos;

import lk.lnas.ims.domain.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Plant, Long> {
    @Query("SELECT oi.plant.id,oi.plant.name, SUM(oi.qty) AS total_order_qty, SUM(pi.qty) AS total_purchase_qty, p.purchasePrice, p.salesPrice, oi.dateCreated " +
            "FROM OrderItem oi " +
            "JOIN PurchaseItem pi ON oi.plant.id = pi.plant.id " +
            "JOIN Plant p ON oi.plant.id = p.id " +
            "GROUP BY oi.plant.id, p.purchasePrice, p.salesPrice ORDER BY oi.dateCreated")
    List<Object[]> getSalesSummary();


    @Query("SELECT p.qtyAtHand, p.qtyPotential, pr.farm.id, p.name " +
            "FROM Plant p " +
            "JOIN Production pr ON p.id = pr.plant.id " +
            "GROUP BY pr.farm.id")
    List<Object[]> getProductionSummary();


    @Query("SELECT YEAR(p.dateCreated) AS year, MONTH(p.dateCreated) AS month, WEEK(p.dateCreated) AS week, " +
            "p.id AS purchaseId, p.dateCreated AS purchaseDate, p.total AS purchaseTotal, " +
            "pl.id AS plantId, pl.name AS plantName, pl.qtyAtHand AS plantQtyAtHand, pl.salesPrice as unitPrice " +
            "FROM Purchase p " +
            "JOIN PurchaseItem pi ON p.id = pi.purchase.id " +
            "JOIN Plant pl ON pi.plant.id = pl.id " +
            "ORDER BY YEAR(p.dateCreated), MONTH(p.dateCreated), WEEK(p.dateCreated)")
    List<Object[]> getPurchaseSummary();

    @Query("SELECT e, s.amount AS salary_amount " +
            "FROM Employee e " +
            "JOIN Salary s ON e.id = s.employee.id " +
            "GROUP BY e.id, s.amount")
    List<Object[]> getSalarySummary();

    @Query("SELECT p.qtyAtHand, p.qtyPotential, pr.farm.id, p.name " +
            "FROM Plant p " +
            "JOIN Production pr ON p.id = pr.plant.id " +
            "GROUP BY pr.farm.id")
    List<Object[]> getFarmSummary();
}
