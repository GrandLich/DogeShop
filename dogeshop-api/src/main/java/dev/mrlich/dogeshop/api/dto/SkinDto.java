package dev.mrlich.dogeshop.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("Скин, представленный в ассортименте магазина")
public class SkinDto {

    @ApiModelProperty(value = "Идентификатор", required = true)
    private Long id;

    @ApiModelProperty(value = "Наименование", required = true)
    private String name;

    @ApiModelProperty(value = "JSON-строка с локализациями", required = true)
    private String localizedName;

    @ApiModelProperty(value = "Ссылка на картинку")
    private String pictureUrl;

    @ApiModelProperty(value = "Описание")
    private String description;

    @ApiModelProperty(value = "Цена", required = true)
    private BigDecimal price;

}
