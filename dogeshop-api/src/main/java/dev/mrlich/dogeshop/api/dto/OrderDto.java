package dev.mrlich.dogeshop.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@ApiModel("Выполненный заказ")
public class OrderDto {

    @ApiModelProperty(value = "Идентификатор", required = true)
    private Long id;

    @ApiModelProperty(value = "Наименование скина", required = true)
    private String skinName;

    @ApiModelProperty(value = "Дата покупки", required = true)
    private Instant date;

    @ApiModelProperty(value = "Стоимость покупки", required = true)
    private BigDecimal price;

    @ApiModelProperty(value = "Идентификатор аккаунта, к которому относится этот заказ", required = true)
    private Long accountId;

}
