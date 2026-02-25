package review.entitiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Builder
@Document(collection = "review")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Review {
    @Id
    private String id;
    private int bookId;
    private int rating;
    private String comment;
    private boolean isDeleted = false;
}
