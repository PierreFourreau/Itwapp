package com.fourreau.itwapp.service.impl;

import com.fourreau.itwapp.service.ApplicantService;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.itwapp.models.Applicant;
import io.itwapp.models.Interview;

/**
 * Created by Pierre on 15/04/2015.
 */
public class ApplicantServiceImpl implements ApplicantService {

    @Inject
    public ApplicantServiceImpl() {}

    @Override
    public Applicant[] getAllApplicantsByInterview(String interviewId, Map<String, Object> param) {
        return Interview.findAllApplicant(interviewId, new HashMap<String, Object>());
    }

    @Override
    public Applicant findOne(String applicantId){
        return Applicant.findOne(applicantId);
    }

    @Override
    public Applicant create(Map<String, Object> param) {
        return Applicant.create(param);
    }

}
