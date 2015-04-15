package com.fourreau.itwapp.service.impl;

import com.fourreau.itwapp.service.InterviewService;
import java.util.HashMap;
import io.itwapp.models.Interview;

/**
 * Created by Pierre on 15/04/2015.
 */
public class InterviewServiceImpl implements InterviewService{

    @Override
    public Interview[] getAllInterviews() {
        return Interview.findAll(new HashMap<String, Object>());
    }

}
