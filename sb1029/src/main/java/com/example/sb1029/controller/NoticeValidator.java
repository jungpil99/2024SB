package com.example.sb1029.controller;

import com.example.sb1029.entity.Notice;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoticeValidator implements Validator {

    private static final String titleRegExp =
            "^[A-Za-z0-9가-힣\\s!@#$%^&*()\\-_=+]+$"; // 제목 형식 정규 표현식

    private Pattern pattern = Pattern.compile(titleRegExp);

    public NoticeValidator() {
        pattern = Pattern.compile(titleRegExp);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Notice.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Notice notice = (Notice) target;
        if(notice.getTitle() == null || notice.getTitle().isEmpty()) {
            errors.rejectValue("title", "required");
        }else{
            Matcher matcher = pattern.matcher(notice.getTitle());
            if(!matcher.matches()) {
                errors.rejectValue("title", "bad");
            }
        }

        if(notice.getContent() == null || notice.getContent().isEmpty()) {
            errors.rejectValue("content", "required");
        }

    }
}
