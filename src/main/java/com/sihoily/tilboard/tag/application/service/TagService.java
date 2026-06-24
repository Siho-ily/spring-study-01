package com.sihoily.tilboard.tag.application.service;

import com.sihoily.tilboard.tag.application.port.in.GetAllTagsUseCase;
import com.sihoily.tilboard.tag.application.port.in.GetTagsUseCase;
import com.sihoily.tilboard.tag.application.port.out.LoadTagPort;
import com.sihoily.tilboard.tag.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TagService implements GetTagsUseCase, GetAllTagsUseCase {

    private final LoadTagPort loadTagPort;

    @Override
    @Transactional(readOnly = true)
    public List<Tag> getTags(Long postId) {
        return loadTagPort.loadTagsByPostId(postId);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, List<Tag>> getTagsGroupedByPostIds(List<Long> postIds) {
        return loadTagPort.loadTagsGroupedByPostIds(postIds);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> getAllTags() {
        return loadTagPort.loadAllByUsageCount();
    }
}
