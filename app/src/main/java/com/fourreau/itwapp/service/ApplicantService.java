package com.fourreau.itwapp.service;


import java.util.Map;

import io.itwapp.exception.APIException;
import io.itwapp.models.Applicant;

/**
 * Created by Pierre on 15/04/2015.
 */
public interface ApplicantService {
    public Applicant[] getAllApplicantsByInterview(String interviewId, Map<String, Object> param) throws APIException;
    public Applicant findOne(String applicantId) throws APIException;
    public Applicant create(Map<String, Object> param) throws APIException;
}
