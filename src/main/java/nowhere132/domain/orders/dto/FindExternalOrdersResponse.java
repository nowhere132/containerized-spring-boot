package nowhere132.domain.orders.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FindExternalOrdersResponse {
    private int pageIndex;
    private int pageSize;
    private int total;
    private int totalPages;

    public static class Order {
        private String id;
        private String side;
        private String custodycd;
        private String acctno;
        private String exchange;
        private String board;
        private String symbol;
        private BigDecimal quotePrice;
        private Long quoteQtty;
    }
}
