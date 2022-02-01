package recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private User user;

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @UpdateTimestamp
    private LocalDateTime date = LocalDateTime.now();

    @NotBlank
    @Size(min = 1)
    private String description;

    @NotEmpty
    @Size(min = 1)
    @ElementCollection
    private List<String> ingredients;

    @NotEmpty
    @Size(min = 1)
    @ElementCollection
    private List<String> directions;
}

