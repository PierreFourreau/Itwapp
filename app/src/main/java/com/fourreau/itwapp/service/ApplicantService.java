package com.fourreau.itwapp.service;


import java.util.Map;

import io.itwapp.exception.APIException;
import io.itwapp.exception.InvalidRequestError;
import io.itwapp.exception.ResourceNotFoundException;
import io.itwapp.exception.ServiceException;
import io.itwapp.exception.UnauthorizedException;
import io.itwapp.models.Applicant;

/**
 * Created by Pierre on 15/04/2015.
 */
public interface ApplicantService {
    public Applicant[] getAllApplicantsByInterview(String interviewId, Map<String, Object> param) throws UnauthorizedException, InvalidRequestError, ResourceNotFoundException, ServiceException, APIException;
    public Applicant findOne(String applicantId) throws UnauthorizedException, InvalidRequestError, ResourceNotFoundException, ServiceException, APIException ;
    public Applicant create(Map<String, Object> param) throws UnauthorizedException, InvalidRequestError, ResourceNotFoundException, ServiceException, APIException;
    public void delete(String applicantId) throws UnauthorizedException, InvalidRequestError, ResourceNotFoundException, ServiceException, APIException;
}
