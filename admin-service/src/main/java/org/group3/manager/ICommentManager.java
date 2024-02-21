package org.group3.manager;

import org.group3.dto.response.CommentFindAllResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.group3.constant.EndPoints.FIND_ALL;

@FeignClient(name = "comment-manager", url = "http://localhost:9102/comment" )
public interface ICommentManager {
    @GetMapping(FIND_ALL)
    ResponseEntity<List<CommentFindAllResponseDto>> findAll();
}
