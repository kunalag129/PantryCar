package com.pantrycar.system.representations.menu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pantrycar.system.representations.BasicResponse;
import lombok.*;

import java.util.List;

/**
 * Created by kunal.agarwal on 09/07/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuResponse extends BasicResponse {

    @Singular
    @JsonIgnoreProperties({"restaurantId"})
    List<Category> categories;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Category {
        int id;
        String name;
        String restaurantId;

        @Singular
        @JsonIgnoreProperties({"categoryId"})
        List<Item> items;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Getter
        @Setter
        @ToString
        @EqualsAndHashCode
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class Item {
            int id;
            String name;
            double price;
            boolean active;
            int categoryId;
            String description;
        }
    }
}
