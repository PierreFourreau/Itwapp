package com.fourreau.itwapp.service;

import io.itwapp.exception.APIException;
import io.itwapp.models.Interview;

/**
 * Created by Pierre on 15/04/2015.
 */
public interface InterviewService {
    public Interview[] getAllInterviews() throws APIException;
    public Interview findOne(String interviewId) throws APIException;
}
