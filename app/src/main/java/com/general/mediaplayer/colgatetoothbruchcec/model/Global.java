package com.general.mediaplayer.colgatetoothbruchcec.model;

import java.util.ArrayList;
import java.util.List;

public class Global {

    static public ProductModel currentProduct;
    static public List<ProductModel> products = new ArrayList<>();

    static public Boolean ischeckbox_clean = false;
    static public Boolean ischeckbox_whiter = false;
    static public Boolean ischeckbox_specialty = false;
    static public Boolean ischeckbox_extra_soft = false;
    static public Boolean ischeckbox_soft = false;
    static public Boolean ischeckbox_medium = false;
    static public Boolean ischeckbox_colgate = false;
    static public Boolean ischeckbox_oral = false;

    static public List<String> benefitFilter = new ArrayList<>();
    static public List<String> bristle_typeFilter = new ArrayList<>();
    static public List<String> brandFilter = new ArrayList<>();
}
