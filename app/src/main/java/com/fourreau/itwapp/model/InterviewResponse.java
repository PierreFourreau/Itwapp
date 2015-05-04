package com.fourreau.itwapp.model;

import io.itwapp.models.Interview;

/**
 * Created by Pierre on 04/05/2015.
 */
public interface InterviewResponse {
    public void processFinish(Interview[] output);
}