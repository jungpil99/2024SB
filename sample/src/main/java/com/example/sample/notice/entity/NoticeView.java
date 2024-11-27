package com.example.sample.notice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeView {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private Integer noticeId;
    private String viewDate;

    public NoticeView(String username, Integer noticeId, String viewDate) {
        this.username = username;
        this.noticeId = noticeId;
        this.viewDate = viewDate;
    }
}
