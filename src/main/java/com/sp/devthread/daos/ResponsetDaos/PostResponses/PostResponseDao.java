package com.sp.devthread.daos.ResponsetDaos.PostResponses;

import com.sp.devthread.daos.ResponsetDaos.CategoryResponses.CategoryResponseDao;
import com.sp.devthread.daos.ResponsetDaos.CommentResponses.CommentResponseDao;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<CategoryResponseDao> categoryResponseDaoSet = new HashSet<>();


}
