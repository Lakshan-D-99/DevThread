package com.sp.devthread.daos.RequestDaos.PostRequests;

import com.sp.devthread.daos.ResponsetDaos.CommentResponses.CommentResponseDao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDao {
    private String postTitle;
    private String postImage;
    private String postBody;
    private List<String> categoriesList = new ArrayList<>();
}
