package nowhere132.domain.orders;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity(name = "orders")
public class Order {
    @Id
    public String id;

    public String side;

    public String custodycd;
    public String acctno;

    public String exchange;
    public String board;
    public String symbol;
    public Integer quote_price;
    public Integer quote_qtty;

    public BigDecimal exec_amt;
}
