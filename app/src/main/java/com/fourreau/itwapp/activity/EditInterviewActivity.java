package com.fourreau.itwapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.model.InterviewOneResponse;
import com.fourreau.itwapp.model.UpdateInterviewResponse;
import com.fourreau.itwapp.service.InterviewService;
import com.fourreau.itwapp.task.CreateInterviewTask;
import com.fourreau.itwapp.task.OneInterviewTask;
import com.fourreau.itwapp.task.UpdateInterviewTask;
import com.gc.materialdesign.views.ButtonFloat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.itwapp.models.Interview;
import io.itwapp.models.Question;
import timber.log.Timber;

import static com.fourreau.itwapp.R.array.question_time;

public class EditInterviewActivity extends ActionBarActivity implements InterviewOneResponse, UpdateInterviewResponse{

    @Inject
    InterviewService interviewService;

    private String idInterview;

    private EditText editTextName, editTextDescription, editTextVideo, editTextCallback;
    private ButtonFloat buttonValidateEditInterview;
    private Button buttonOpenDialogAddQuestion;
    private Spinner spinnerQuestionReadingTime, spinnerQuestionAnswerTime;

    private LinearLayout container;

    private Map<String, Object> param = new HashMap<String, Object>();
    private List<Map<String, Object>> questions = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ItwApplication) getApplication()).inject(this);

        setContentView(R.layout.activity_edit_interview);

        idInterview = ((ItwApplication) this.getApplication()).getInterviewId();

        //get fields
        editTextName = (EditText)findViewById(R.id.editInterviewName);
        editTextDescription = (EditText)findViewById(R.id.editInterviewDescription);
        editTextVideo = (EditText)findViewById(R.id.editInterviewVideo);
        editTextCallback = (EditText)findViewById(R.id.editInterviewCallback);
        buttonOpenDialogAddQuestion = (Button)findViewById(R.id.openDialogAddQuestion);
        buttonValidateEditInterview = (ButtonFloat)findViewById(R.id.editApplicantButtonValidate);

        container = (LinearLayout)findViewById(R.id.containerQuestions);

        //get itw info
        OneInterviewTask mTask = new OneInterviewTask(EditInterviewActivity.this, interviewService, idInterview);
        mTask.delegate = this;
        mTask.execute();

        //button open dialog for new question
        buttonOpenDialogAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                LayoutInflater li = LayoutInflater.from(EditInterviewActivity.this);
                View promptsView = li.inflate(R.layout.dialog_add_question, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditInterviewActivity.this);
                alertDialogBuilder.setView(promptsView);

                final EditText editTextQuestionName = (EditText) promptsView.findViewById(R.id.editTextQuestionName);

                //get spinner
                spinnerQuestionReadingTime = (Spinner)promptsView.findViewById(R.id.spinnerReadingTime);
                spinnerQuestionAnswerTime = (Spinner)promptsView.findViewById(R.id.spinnerAnswerTime);

                //fill spinner question time
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(EditInterviewActivity.this, question_time, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerQuestionReadingTime.setAdapter(adapter);
                spinnerQuestionAnswerTime.setAdapter(adapter);

                //set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton(R.string.alert_dialog_ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //add row
                                        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View addView = layoutInflater.inflate(R.layout.question_row, null);

                                        //if content question is not null we can continue
                                        if (editTextQuestionName.getText().length() > 0) {
                                            //get fields
                                            TextView textViewQuestionName = (TextView) addView.findViewById(R.id.textViewQuestionName);
                                            TextView textViewQuestionReadingTime = (TextView) addView.findViewById(R.id.textViewQuestionReadingTime);
                                            TextView textViewQuestionAnswerTime = (TextView) addView.findViewById(R.id.textViewQuestionAnswerTime);
                                            //set fields
                                            textViewQuestionName.setText(editTextQuestionName.getText().toString());
                                            textViewQuestionReadingTime.setText(spinnerQuestionReadingTime.getSelectedItem().toString());
                                            textViewQuestionAnswerTime.setText(spinnerQuestionAnswerTime.getSelectedItem().toString());
                                            //remove button
                                            ImageButton buttonRemove = (ImageButton) addView.findViewById(R.id.remove);
                                            buttonRemove.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    ((LinearLayout) addView.getParent()).removeView(addView);
                                                }
                                            });
                                            dialog.dismiss();
                                            addView.setBackgroundResource(R.drawable.frame);
                                            addView.setPadding(5,5,5,5);
                                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            lp.setMargins(5, 5, 5, 2);
                                            addView.setLayoutParams(lp);
                                            //add row to container
                                            container.addView(addView);
                                        } else {
                                            showAlertDialog(R.string.dialog_title_generic_error, R.string.dialog_add_interview_question_content_error);
                                        }
                                    }
                                })
                        .setNegativeButton(R.string.alert_dialog_cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                ;

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                //hide keybord when dialog is close
                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        hideSoftKeyboard();
                    }
                });

                // show dialog
                alertDialog.show();
            }
        });

        //validate add
        buttonValidateEditInterview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                if(editTextName.getText().length() > 0) {
                    //get questions container and get childs
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.containerQuestions);
                    int childCount = linearLayout.getChildCount();
                    for (int i=0; i < childCount; i++){
                        View v = linearLayout.getChildAt(i);
                        TextView textViewQuestionName = (TextView) v.findViewById(R.id.textViewQuestionName);
                        TextView textViewQuestionReadingTime = (TextView) v.findViewById(R.id.textViewQuestionReadingTime);
                        TextView textViewQuestionAnswerTime = (TextView) v.findViewById(R.id.textViewQuestionAnswerTime);

                        final Map<String, Object> question = new HashMap<String, Object>();

                        //put question fields
                        question.put("content", textViewQuestionName.getText().toString());
                        question.put("readingTime", Integer.parseInt(textViewQuestionReadingTime.getText().toString().substring(0, 1)) * 60);
                        question.put("answerTime", Integer.parseInt(textViewQuestionAnswerTime.getText().toString().substring(0, 1)) * 60);
                        question.put("number", i + 1);

                        questions.add(question);
                    }

                    param.put("name", editTextName.getText().toString());
                    param.put("video", editTextVideo.getText().toString());
                    param.put("text", editTextDescription.getText().toString());
                    param.put("callback", editTextCallback.getText().toString());
                    param.put("questions", questions);

                    Timber.d("EditInterviewActivity:interview sent : " + param.toString());

                    //launch task which edit interview
                    UpdateInterviewTask mTask = new UpdateInterviewTask(EditInterviewActivity.this, interviewService, idInterview, param);
                    mTask.delegate = EditInterviewActivity.this;
                    mTask.execute();
                }
                else {
                    showAlertDialog(R.string.dialog_title_generic_error, R.string.activity_add_interview_name_error);
                }
            }});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * Process finish get one interview.
     *
     * @param interview
     */
    public void processFinish(Interview interview){
        Timber.d("InterviewActivity:interview retrieved : " + interview.name);
        editTextName.setText(interview.name);
        //description
        if(!interview.text.isEmpty()) {
            editTextDescription.setText(interview.text);
        }
        //video
        if(!interview.video.isEmpty()) {
            editTextVideo.setText(interview.video);
        }
        //callback
        if(!interview.callback.isEmpty()) {
            editTextCallback.setText(interview.callback);
        }
        if(interview.questions.length > 0) {
            for (Question q : interview.questions) {
                //add row
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.question_row, null);

                //get fields
                TextView textViewQuestionName = (TextView) addView.findViewById(R.id.textViewQuestionName);
                TextView textViewQuestionReadingTime = (TextView) addView.findViewById(R.id.textViewQuestionReadingTime);
                TextView textViewQuestionAnswerTime = (TextView) addView.findViewById(R.id.textViewQuestionAnswerTime);
                //set fields
                textViewQuestionName.setText(q.content);
                textViewQuestionReadingTime.setText(Integer.toString(q.readingTime/60));
                textViewQuestionAnswerTime.setText(Integer.toString(q.answerTime/60));
                //remove button
                ImageButton buttonRemove = (ImageButton) addView.findViewById(R.id.remove);
                buttonRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout) addView.getParent()).removeView(addView);
                    }
                });
                addView.setBackgroundResource(R.drawable.frame);
                addView.setPadding(5, 5, 5, 5);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5, 5, 5, 2);
                addView.setLayoutParams(lp);
                //add row to container
                container.addView(addView);
            }
        }
    }

    public void processFinishUpdate(Interview output) {
        if(output != null) {
            Toast.makeText(this, R.string.activity_update_interview_success, Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(EditInterviewActivity.this, InterviewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else {
            questions.clear();
        }
    }

    /**
     * Hide keyboard method.
     */
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void showAlertDialog(int title, int content) {
        new AlertDialog.Builder(EditInterviewActivity.this).setTitle(title).setMessage(content)
                .setIcon(R.drawable.ic_warning).setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                //do nothing
            }
        }).show();
    }
}
