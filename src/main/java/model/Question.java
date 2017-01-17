package model;

public class Question {

    private byte num;
    private String question;
    private String code;
    String choiceType;
    private String[] choice;
    private String[] correctAnswers;
    private String[] answers;
    private boolean isCorrect;
    private boolean answered;

    public Question(String question, String code, String choiceType, String[] choice, String[] correctAnswers) {
        this.question = question;
        this.code = code;
        this.choiceType = choiceType;
        this.choice = choice;
        this.correctAnswers = correctAnswers;
        isCorrect = false;
        answered = false;
    }

    public void setNum(byte num) {
        this.num = num;
    }

    public byte getNum() {
        return num;
    }

    public String getQuestion() {
        return question;
    }

    public String getCode() {
        return code;
    }

    public String[] getChoice() {
        return choice;
    }

    public String[] getCorrectAnswers() {
        return correctAnswers;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answer) {
        this.answers = answer;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }
}
