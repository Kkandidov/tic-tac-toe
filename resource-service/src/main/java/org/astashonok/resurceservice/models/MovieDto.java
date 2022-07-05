package org.astashonok.resurceservice.models;

import lombok.Data;

@Data
public class MovieDto {

    private Integer id;
    private String title;
    private Integer year;
    private Double imdbRating;
}
