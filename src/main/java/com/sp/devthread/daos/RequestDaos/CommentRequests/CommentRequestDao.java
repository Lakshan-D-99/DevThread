package com.sp.devthread.daos.RequestDaos.CommentRequests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDao {
    private String commentBody;
    private Long postId;
}
