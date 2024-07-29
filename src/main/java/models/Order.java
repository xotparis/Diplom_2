package models;

import lombok.*;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class Order {
    private List<String> ingredients;
}