package com.sp.devthread.daos.ResponsetDaos.CommentResponses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDao {

    private long commentId;
    private String commentBody;
    private LocalDate publishedDate;
    private Long postId;
    private Long userId;
}
