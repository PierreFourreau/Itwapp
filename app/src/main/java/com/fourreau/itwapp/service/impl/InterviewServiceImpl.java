package com.fourreau.itwapp.service.impl;

import com.fourreau.itwapp.service.InterviewService;
import java.util.HashMap;

import javax.inject.Inject;

import io.itwapp.Itwapp;
import io.itwapp.models.Interview;

/**
 * Created by Pierre on 15/04/2015.
 */
public class InterviewServiceImpl implements InterviewService{

    @Inject
    public InterviewServiceImpl() {}

    @Override
    public Interview[] getAllInterviews() {
//        Itwapp.apiKey = "3bf8bce4b8b0ac18a0a4669ec58ed03f";
//        Itwapp.secretKey = "450425436db428e7d04288d592c1e771c82f9747";

        Itwapp.apiKey = "1965936758968ade03c70da2c21ad7c6";
        Itwapp.secretKey = "4fde9994f78b789f23941837566278cd7d21c8af";

        return Interview.findAll(new HashMap<String, Object>());
    }

}
