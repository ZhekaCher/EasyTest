package Data;

import java.util.ArrayList;

public class Question {

    private String question;
    private ArrayList <String> rightAnswers;
    private ArrayList <String> wrongAnswers;
    private boolean flag;

    public Question(){
        setQuestion("");
        rightAnswers = new ArrayList<>();
        wrongAnswers = new ArrayList<>();
        setFlag(false);
    }

    public Question(String question){
        setQuestion(question);
        rightAnswers = new ArrayList<>();
        wrongAnswers = new ArrayList<>();
        setFlag(false);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void addWrongAnswer(String wrongAnwser){
        this.wrongAnswers.add(wrongAnwser);
    }

    public void addRightAnswer(String rightAnwser){
        this.rightAnswers.add(rightAnwser);
    }

    public int getNumberOfAnswers(){
        return wrongAnswers.size() + 1;
    }

    public ArrayList<String> getWrongAnswers() {
        return wrongAnswers;
    }

    public ArrayList<String> getRightAnswers() {
        return rightAnswers;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }
}
