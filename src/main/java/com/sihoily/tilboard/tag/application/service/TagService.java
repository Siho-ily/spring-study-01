package com.sihoily.tilboard.tag.application.service;

import com.sihoily.tilboard.post.application.port.out.LoadPostPort;
import com.sihoily.tilboard.post.exception.PostNotFoundException;
import com.sihoily.tilboard.tag.application.port.in.AddTagUseCase;
import com.sihoily.tilboard.tag.application.port.in.DeleteTagUseCase;
import com.sihoily.tilboard.tag.application.port.in.GetTagsUseCase;
import com.sihoily.tilboard.tag.application.port.out.DeleteTagPort;
import com.sihoily.tilboard.tag.application.port.out.LoadTagPort;
import com.sihoily.tilboard.tag.application.port.out.SaveTagPort;
import com.sihoily.tilboard.tag.domain.Tag;
import com.sihoily.tilboard.tag.exception.TagAccessDeniedException;
import com.sihoily.tilboard.tag.exception.TagNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService implements AddTagUseCase, DeleteTagUseCase, GetTagsUseCase {

    private final SaveTagPort saveTagPort;
    private final LoadTagPort loadTagPort;
    private final DeleteTagPort deleteTagPort;
    private final LoadPostPort loadPostPort;

    @Override
    @Transactional
    public Tag addTag(Long postId, String requesterId, String name) {
        var post = loadPostPort.loadPost(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        if (!post.authorId().equals(requesterId)) {
            throw new TagAccessDeniedException();
        }

        return saveTagPort.saveTag(new Tag(null, name, postId, null));
    }

    @Override
    @Transactional
    public void deleteTag(Long postId, Long tagId, String requesterId) {
        var post = loadPostPort.loadPost(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        if (!post.authorId().equals(requesterId)) {
            throw new TagAccessDeniedException();
        }

        loadTagPort.loadTag(tagId)
                .orElseThrow(() -> new TagNotFoundException(tagId));

        deleteTagPort.deleteTag(tagId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> getTags(Long postId) {
        return loadTagPort.loadTagsByPostId(postId);
    }
}
