package com.fourreau.itwapp.service;

import java.util.Map;

import io.itwapp.exception.APIException;
import io.itwapp.exception.InvalidRequestError;
import io.itwapp.exception.ResourceNotFoundException;
import io.itwapp.exception.ServiceException;
import io.itwapp.exception.UnauthorizedException;
import io.itwapp.models.Interview;

/**
 * Created by Pierre on 15/04/2015.
 */
public interface InterviewService {
    public Interview[] getAllInterviews() throws UnauthorizedException, InvalidRequestError, ResourceNotFoundException, ServiceException, APIException;
    public Interview findOne(String interviewId) throws UnauthorizedException, InvalidRequestError, ResourceNotFoundException, ServiceException, APIException;
    public Interview create(Map<String, Object> param) throws UnauthorizedException, InvalidRequestError, ResourceNotFoundException, ServiceException, APIException;
    public Interview update(String interviewId, Map<String, Object> param) throws UnauthorizedException, InvalidRequestError, ResourceNotFoundException, ServiceException, APIException ;
    public void delete(String interviewId, Boolean withApplicants) throws UnauthorizedException, InvalidRequestError, ResourceNotFoundException, ServiceException, APIException;
}
