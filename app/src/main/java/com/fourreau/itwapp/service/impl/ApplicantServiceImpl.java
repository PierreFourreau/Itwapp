package com.fourreau.itwapp.service.impl;

import com.fourreau.itwapp.service.ApplicantService;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.itwapp.exception.APIException;
import io.itwapp.exception.InvalidRequestError;
import io.itwapp.exception.ResourceNotFoundException;
import io.itwapp.exception.ServiceException;
import io.itwapp.exception.UnauthorizedException;
import io.itwapp.models.Applicant;
import io.itwapp.models.Interview;

/**
 * Created by Pierre on 15/04/2015.
 */
public class ApplicantServiceImpl implements ApplicantService {

    @Inject
    public ApplicantServiceImpl() {}

    @Override
    public Applicant[] getAllApplicantsByInterview(String interviewId, Map<String, Object> param) throws UnauthorizedException, InvalidRequestError, ResourceNotFoundException, ServiceException, APIException{
        return Interview.findAllApplicant(interviewId, new HashMap<String, Object>());
    }

    @Override
    public Applicant findOne(String applicantId) throws UnauthorizedException, InvalidRequestError, ResourceNotFoundException, ServiceException, APIException {
        return Applicant.findOne(applicantId);
    }

    @Override
    public Applicant create(Map<String, Object> param) throws UnauthorizedException, InvalidRequestError, ResourceNotFoundException, ServiceException, APIException {
        return Applicant.create(param);
    }

    @Override
    public void delete(String applicantId) throws UnauthorizedException, InvalidRequestError, ResourceNotFoundException, ServiceException, APIException {
        Applicant.delete(applicantId);
    }
}
