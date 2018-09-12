package Data;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class GlobalFunc {

    public static void removeTest(String testName) {
        try {
            File file = new File(Global.getTestsDir().toURL().getPath() + testName + ".test");
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Question> getQuestions(File file) {
        Scanner in = null;
        try {
            //  in = new Scanner(new File(GlobalFunc.class.getResource("/test.txt").toURI()));
            in = new Scanner(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Question> questions = new ArrayList<>();
        int i = 1;
        Question question = new Question();
        boolean text = false;
        boolean rightAnswer = false;
        boolean wrongAnswer = false;
        String answer = "";
        while (in.hasNext()) {
            String str = in.next();
            if (str.equals(String.valueOf(i)) && !text) {
                in.next();
                if (rightAnswer) {
                    question.addRightAnswer(answer);
                    answer = "";
                } else if (wrongAnswer) {
                    question.addWrongAnswer(answer);
                    answer = "";
                }
                rightAnswer = false;
                wrongAnswer = false;
                if (i != 1)
                    questions.add(question);
                question = new Question();
                text = true;
                ++i;
            } else if (str.equals("*")) {
                text = false;
                String s = in.next();
                if (rightAnswer) {
                    question.addRightAnswer(answer);
                    answer = "";
                } else if (wrongAnswer) {
                    question.addWrongAnswer(answer);
                    answer = "";
                }
                if (s.equals("+")) {
                    rightAnswer = true;
                    answer += in.next();
                    wrongAnswer = false;
                } else {
                    wrongAnswer = true;
                    rightAnswer = false;
                    answer += s;
                }
            } else if (text) {
                question.setQuestion(question.getQuestion() + " " + str);
            } else {
                answer += " " + str;
            }


        }
        questions.add(question);
        return questions;
    }

}
