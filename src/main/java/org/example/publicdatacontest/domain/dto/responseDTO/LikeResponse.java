package org.example.publicdatacontest.domain.dto.responseDTO;

import lombok.Getter;
import lombok.Setter;
import org.example.publicdatacontest.domain.mentor.MentorClass;

@Getter
public class LikeResponse {

    private final Long classId;
    private final Long likeCount;

    public LikeResponse(MentorClass mentorClass) {
        this.classId = mentorClass.getClassId();
        this.likeCount = mentorClass.getLikeCount();
    }
}
