package nowhere132.domain.orders;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = "Order")
@Table(name = "ORDERS")
public class OrderEntity {
    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SIDE", nullable = false, updatable = false)
    private String side;

    @Column(name = "CUSTODYCD", nullable = false, updatable = false)
    private String custodycd;
    @Column(name = "ACCTNO", nullable = false, updatable = false)
    private String acctno;

    @Column(name = "EXCHANGE", nullable = false, updatable = false)
    private String exchange;
    @Column(name = "BOARD", nullable = false, updatable = false)
    private String board;
    @Column(name = "SYMBOL", nullable = false, updatable = false)
    private String symbol;
    @Column(name = "QUOTE_PRICE", nullable = false, updatable = false)
    private BigDecimal quotePrice;
    @Column(name = "QUOTE_QTTY", nullable = false, updatable = false)
    private Long quoteQtty;

    @Column(name = "EXEC_AMT", nullable = false)
    private BigDecimal execAmt;
    @Column(name = "EXEC_QTTY", nullable = false)
    private Long execQtty;
    @Column(name = "REMAIN_QTTY", nullable = false)
    private Long remainQtty;
    @Column(name = "CANCEL_QTTY", nullable = false)
    private Long cancelQtty;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Column(name = "UPDATED_AT")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
}
