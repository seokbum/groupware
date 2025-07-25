package ldb.groupware.dto.draft;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ldb.groupware.domain.FormAnnualLeave;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class DraftFormDto {

    private Integer docId;

    @NotBlank(message = "제목은 필수 입력입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력입니다.")
    private String content;

    @NotBlank(message = "결재양식을 선택하세요.")
    private String formType;

    @NotBlank(message = "1차 결재자를 선택하세요.")
    private String approver1;

    @NotBlank(message = "2차 결재자를 선택하세요.")
    private String approver2;

    @NotNull(message = "문서종료일을 입력하세요.")
    private LocalDate deadline;

    private String referrers;

    private String attachType = "D"; // 전자결재 첨부파일 타입

    // 휴가신청서
    private String leaveType;
    private LocalDate leaveStart;
    private LocalDate leaveEnd;

    // 프로젝트 제안서
    private String projectTitle;
    private String expectedDuration;
    private String projectGoal;

    // 지출결의서
    private String expenseItem;
    private Integer amount;
    private LocalDate usedDate;

    // 사직서
    private LocalDate resignDate;
    private String resignReason;

    public FormAnnualLeave createFormAnnualLeave() {

        if (leaveStart == null || leaveEnd == null) {
            throw new IllegalArgumentException("휴가 시작일과 종료일은 필수입니다.");
        }

        FormAnnualLeave formAnnualLeave = new FormAnnualLeave();
        formAnnualLeave.setDocId(docId);
        formAnnualLeave.setFormCode(formType);
        formAnnualLeave.setLeaveCode(leaveType);
        formAnnualLeave.setStartDate(leaveStart);
        formAnnualLeave.setEndDate(leaveEnd);
        formAnnualLeave.setTotalDays((double) (ChronoUnit.DAYS.between(leaveStart, leaveEnd) + 1));
        formAnnualLeave.setAnnualContent(content);

        return formAnnualLeave;
    }

    public double getTotalDays() {
        return (double) ChronoUnit.DAYS.between(leaveStart, leaveEnd) + 1;
    }

    public List<String> getReferrerList() {
        if (StringUtils.isNotBlank(referrers)) {
            return Arrays.stream(referrers.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}


