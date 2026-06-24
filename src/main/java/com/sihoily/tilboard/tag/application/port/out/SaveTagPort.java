package com.sihoily.tilboard.tag.application.port.out;

import com.sihoily.tilboard.tag.domain.Tag;

public interface SaveTagPort {
    Tag saveTag(Tag tag);
}
