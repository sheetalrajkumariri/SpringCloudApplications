package review.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import review.entitiy.Review;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    Optional<Review> findByIdAndIsDeletedFalse(String reviewId);

    List<Review> findAllByIsDeletedFalse();

    List<Review> findByBookIdAndIsDeletedFalse(int bookId);
}
