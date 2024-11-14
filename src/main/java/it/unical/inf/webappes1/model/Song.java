package it.unical.inf.webappes1.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Song {

    private String titolo;  // Titolo della canzone
    private String durata;  // Durata della canzone (es. "3:45")
}