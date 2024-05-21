package ru.skypro.homework.serviceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.webjars.NotFoundException;
import ru.skypro.homework.dto.comment.CommentDTO;
import ru.skypro.homework.dto.comment.CommentsDTO;
import ru.skypro.homework.dto.comment.CreateOrUpdateComment;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.Listing;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.ListingRepository;
import ru.skypro.homework.service.impl.CommentsServiceImpl;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentsServiceImplTest {

    @Mock
    private ListingRepository listingRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentMapper commentMapper;
    @InjectMocks
    private CommentsServiceImpl commentsService;

    @Test
    public void testGetComments() {

        long listingId = 1L;
        List<Comment> comments = of(mock(Comment.class), mock(Comment.class));
        CommentDTO commentDTO = mock(CommentDTO.class);


        when(commentRepository.findCommentsByListingId(listingId)).thenReturn(comments);
        when(commentMapper.commentToCommentDto(any(Comment.class))).thenReturn(commentDTO);


        CommentsDTO result = commentsService.getComments(listingId);
        assertEquals(comments.size(), result.getCount());
        assertEquals(2, result.getResults().size());
        verify(commentMapper, times(2)).commentToCommentDto(any(Comment.class));
    }

    @Test
    public void testAddComment() {

        long listingId = 1L;
        CreateOrUpdateComment createComment = new CreateOrUpdateComment("Test comment");
        Authentication authentication = mock(Authentication.class);
        Listing listing = mock(Listing.class);
        Comment comment = mock(Comment.class);
        CommentDTO commentDTO = mock(CommentDTO.class);


        when(listingRepository.findById(listingId)).thenReturn(Optional.of(listing));
        when(authentication.getName()).thenReturn("testuser");
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentMapper.commentToCommentDto(comment)).thenReturn(commentDTO);


        CommentDTO result = commentsService.addComment(listingId, createComment, authentication);
        assertEquals(commentDTO, result);
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    public void testAddCommentListingNotFound() {

        long listingId = 1L;
        CreateOrUpdateComment createComment = new CreateOrUpdateComment("Test comment");
        Authentication authentication = mock(Authentication.class);


        when(listingRepository.findById(listingId)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () -> commentsService.addComment(listingId, createComment, authentication));
    }

    @Test
    public void testDeleteCommentSuccessAsAuthor() throws AccessDeniedException {

        long listingId = 1L;
        long commentId = 2L;
        Authentication authentication = mock(Authentication.class);
        Comment comment = mock(Comment.class);
        User author = mock(User.class);


        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(comment.getAuthor()).thenReturn(author);
        when(author.getEmail()).thenReturn("testuser");
        when(authentication.getName()).thenReturn("testuser");


        commentsService.deleteComment(listingId, commentId, authentication);


        verify(commentRepository).delete(comment);
    }

 //   @Test
 //   public void testDeleteCommentSuccessAsAdmin() throws AccessDeniedException {

 //       long listingId = 1L;
 //       long commentId = 2L;
 //       Authentication authentication = mock(Authentication.class);
 //       Comment comment = mock(Comment.class);
 //       User author = mock(User.class);


 //       when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
 //       when(comment.getAuthor()).thenReturn(author);
        // Имитировать пользователя с правами администратора
 //       when(authentication.getAuthorities()).thenReturn(List.of(new SimpleGrantedAuthority(ADMIN)));


 //       commentsService.deleteComment(listingId, commentId, authentication);


 //       verify(commentRepository).delete(comment);
 //   }

    @Test
    public void testDeleteCommentAccessDenied() {

        long listingId = 1L;
        long commentId = 2L;
        Authentication authentication = mock(Authentication.class);
        Comment comment = mock(Comment.class);
        User author = mock(User.class);


        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(comment.getAuthor()).thenReturn(author);
        when(author.getEmail()).thenReturn("anotheruser@email.com"); // Different email than authenticated user
        when(authentication.getName()).thenReturn("testuser@email.com");
        when(authentication.getAuthorities()).thenReturn(List.of()); // No admin authority


        assertThrows(AccessDeniedException.class, () -> commentsService.deleteComment(listingId, commentId, authentication));
    }

    @Test
    public void testDeleteCommentNotFound() {

        long listingId = 1L;
        long commentId = 2L;
        Authentication authentication = mock(Authentication.class);


        when(commentRepository.findById(commentId)).thenReturn(Optional.empty()); // Comment not found


        assertThrows(NotFoundException.class, () -> commentsService.deleteComment(listingId, commentId, authentication));
    }

    @Test
    public void testUpdateCommentSuccessAsAuthor() throws AccessDeniedException {

        long listingId = 1L;
        long commentId = 2L;
        Authentication authentication = mock(Authentication.class);
        Comment comment = mock(Comment.class);
        User author = mock(User.class);

        CreateOrUpdateComment updateComment = new CreateOrUpdateComment("Updated comment");
        CommentDTO commentDTO = mock(CommentDTO.class);


        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.commentToCommentDto(comment)).thenReturn(commentDTO);


        CommentDTO result = commentsService.updateComment(listingId, commentId, updateComment, authentication);
        assertEquals(commentDTO, result);
        verify(comment).setText(updateComment.getText());
        verify(commentRepository).save(comment);
    }

    @Test
    public void testUpdateCommentNotFound() {

        long listingId = 1L;
        long commentId = 2L;
        CreateOrUpdateComment updateComment = new CreateOrUpdateComment("Updated comment");
        Authentication authentication = mock(Authentication.class);


        when(commentRepository.findById(commentId)).thenReturn(Optional.empty()); // Comment not found


        assertThrows(NotFoundException.class, () -> commentsService.updateComment(listingId, commentId, updateComment, authentication));
    }

}