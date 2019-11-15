package com.unsw.valerie;

import java.io.Serializable;

public class CatApi implements Serializable {
    public String id;
    public String name;
    public String country_code;
    public String description;
    public String life_span;
    public String wikipedia_url;
    public String temperament;
    public String shedding_level;
    public String dog_friendly;
    public String origin;
    public Weight weight;
    static class Weight implements Serializable {
        public String metric;
        public String imperial;


    }

}
