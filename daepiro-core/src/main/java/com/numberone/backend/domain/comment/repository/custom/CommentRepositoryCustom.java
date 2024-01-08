package com.numberone.backend.domain.comment.repository.custom;

import com.numberone.backend.domain.comment.dto.response.GetCommentDto;

import java.util.List;

public interface CommentRepositoryCustom {
    List<GetCommentDto> findAllByArticle(Long articleId);
    Long countAllByArticle(Long articleId);

}
