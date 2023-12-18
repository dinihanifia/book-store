package com.test.bookstore.POJO.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ECategories {
    ROMANCE("Romance"),
    FANTASY("Fantasy"),
    MYSTERY("Mystery"),
    SCIENCE_FICTION("Science Fiction"),
    HORROR("Horror"),
    NON_FICTION("Non-Fiction"),
    HISTORY("History"),
    BIOGRAPHY("Biography"),
    SELF_HELP("Self-Help"),
    POETRY("Poetry"),
    FICTION("Fiction"),
    METROPOP("Metropop");

    private String name;

//    public static ECategories getName(String name) {
//        for(ECategories categories : values()){
//            if(categories.getName().equalsIgnoreCase(name)){
//                return categories;
//            }
//        }
//        throw new IllegalArgumentException("No constant with category name " + name + " found");
//    }
}
