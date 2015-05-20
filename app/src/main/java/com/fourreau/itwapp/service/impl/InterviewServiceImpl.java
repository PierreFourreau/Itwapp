package com.fourreau.itwapp.service.impl;

import com.fourreau.itwapp.service.InterviewService;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.itwapp.Itwapp;
import io.itwapp.exception.APIException;
import io.itwapp.exception.InvalidRequestError;
import io.itwapp.exception.ResourceNotFoundException;
import io.itwapp.exception.ServiceException;
import io.itwapp.exception.UnauthorizedException;
import io.itwapp.models.Interview;
import timber.log.Timber;

/**
 * Created by Pierre on 15/04/2015.
 */
public class InterviewServiceImpl implements InterviewService{

    @Inject
    public InterviewServiceImpl() {}

    @Override
    public Interview[] getAllInterviews() throws UnauthorizedException, InvalidRequestError, ResourceNotFoundException, ServiceException, APIException {
        return Interview.findAll(new HashMap<String, Object>());
    }

    @Override
    public Interview findOne(String interviewId) throws UnauthorizedException, InvalidRequestError, ResourceNotFoundException, ServiceException, APIException {
        return Interview.findOne(interviewId);
    }

    @Override
    public Interview create(Map<String, Object> param) throws UnauthorizedException, InvalidRequestError, ResourceNotFoundException, ServiceException, APIException {
        return Interview.create(param);
    }

    @Override
    public Interview update(String interviewId, Map<String, Object> param) throws UnauthorizedException, InvalidRequestError, ResourceNotFoundException, ServiceException, APIException  {
        return Interview.update(interviewId, param);
    }

    @Override
    public void delete(String interviewId, Boolean withApplicants) throws UnauthorizedException, InvalidRequestError, ResourceNotFoundException, ServiceException, APIException {
        Map<String, Object> param = new HashMap<String, Object>();

        if(withApplicants) {
            param.put("withApplicant", true);
        }
        Interview.delete(interviewId, param);
    }
}
