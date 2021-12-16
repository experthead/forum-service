package telran.b7a.forum.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import telran.b7a.forum.model.Post;

public interface PostRepository extends MongoRepository<Post, String> {

	Stream<Post> findByAuthor(String author);
	
	@Query(value = "{ tags: {$in: ?0}}")
	Stream<Post> findByTags(List<String> tags);
	
	//first solution
//	Stream<Post> findByDateCreatedBetween(LocalDateTime from, LocalDateTime to);
	
	//second solution
	@Query(value = "{ dateCreated: {$gte: ?0, $lt: ?1}}")
	Stream<Post> findByDateCreated(LocalDateTime from, LocalDateTime to);
}
