package com.sp.devthread.daos.ResponsetDaos.PostResponses;

import com.sp.devthread.daos.ResponsetDaos.CategoryResponses.CategoryResponse;
import com.sp.devthread.daos.ResponsetDaos.CommentResponses.CommentResponseDao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDao {
    private Long id;
    private String postTitle;
    private String postImage;
    private String postBody;
    private LocalDate createdDate;
    private long userId;
    private List<CommentResponseDao> commentResponseDaoList = new ArrayList<>();
    private List<CategoryResponse> categoryResponseList = new ArrayList<>();
}
