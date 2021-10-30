package dev.mrlich.dogeshop.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;

@Data
@Embeddable
public class CartEntity {

    private List<SkinEntity> skins = new ArrayList<>();

}
