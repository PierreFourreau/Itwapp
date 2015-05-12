package com.fourreau.itwapp.service;

import java.util.Map;

import io.itwapp.exception.APIException;
import io.itwapp.models.Interview;

/**
 * Created by Pierre on 15/04/2015.
 */
public interface InterviewService {
    public Interview[] getAllInterviews() throws APIException;
    public Interview findOne(String interviewId) throws APIException;
    public Interview update(String interviewId, Map<String, Object> param) throws APIException;
    public void delete(String interviewId, Boolean withApplicants) throws APIException;
}
