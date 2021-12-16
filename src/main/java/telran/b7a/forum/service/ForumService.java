package telran.b7a.forum.service;

import java.time.LocalDateTime;
import java.util.List;

import telran.b7a.forum.dto.DateRangeDto;
import telran.b7a.forum.dto.NewCommentDto;
import telran.b7a.forum.dto.NewPostDto;
import telran.b7a.forum.dto.PostDto;

public interface ForumService {
	PostDto addNewPost(NewPostDto newPost, String author);

	PostDto getPost(String id);

	PostDto removePost(String id);

	PostDto updatePost(NewPostDto postUpdateDto, String id);

	void addLike(String id);

	PostDto addComment(String id, String author, NewCommentDto newCommentDto);
	
	Iterable<PostDto> findPostsByAuthor(String author);
	
	Iterable<PostDto> findByTags(List<String> tags);
	
	Iterable<PostDto> findByDateCreated(DateRangeDto date);
}
