package nowhere132.domain.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Order, String> {
}
