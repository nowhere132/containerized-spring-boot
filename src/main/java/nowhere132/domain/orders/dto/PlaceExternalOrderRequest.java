package nowhere132.domain.orders.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import nowhere132.annotations.OneOfStrings;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PlaceExternalOrderRequest {
    private String custodycd;

    private String acctno;

    @OneOfStrings(values = { "B", "S" }, message = "Side must be either buy (B) or sell (S)")
    private String side;

    @NotBlank(message = "Symbol must be not null")
    private String symbol;

    @NotNull(message = "Quantity must be not null")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Long qtty;

    @NotNull(message = "Price must be not null")
    private BigDecimal price;
}
