package dev.mrlich.dogeshop.api.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {

    private List<Skin> skins = new ArrayList<>();

}
