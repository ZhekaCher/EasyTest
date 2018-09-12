package Data;

import java.util.ArrayList;

public class Test {

    private String name;
    private ArrayList<Question> questions;
    private int size;

    public Test(String name, int size) {
        setName(name);
        questions = new ArrayList<>();
        setSize(size);
    }

    public Test(String name) {
        setName(name);
        questions = new ArrayList<>();
        setSize(size);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public int getNumberOfQuestions() {
        return questions.size();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
