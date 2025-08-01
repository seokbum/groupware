package ldb.groupware.dto.board;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeFormDto {

    private Integer noticeId;
    @NotEmpty(message = "제목을 입력하세요")
    private String noticeTitle;
    @NotEmpty(message = "내용을 입력하세요")
    private String noticeContent;
    private String memId;
    private String memName;
    private Integer noticeCnt;
    private Character isPinned;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
