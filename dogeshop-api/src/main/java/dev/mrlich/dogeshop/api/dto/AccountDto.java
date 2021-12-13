package dev.mrlich.dogeshop.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@ApiModel("Учётная запись")
public class AccountDto {

    @ApiModelProperty(value = "Идентификатор", required = true)
    private Long id;

    @ApiModelProperty(value = "Наименование", required = true)
    private String name;

    @ApiModelProperty(value = "Баланс кошелька", required = true)
    private BigDecimal balance;

    @ApiModelProperty(value = "Список предметов в корзине")
    private Set<SkinDto> cartItems;

    @ApiModelProperty(value = "Список выполненных заказов")
    private List<OrderDto> orderDtoHistory;

}
