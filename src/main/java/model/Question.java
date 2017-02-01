package model;

public class Question {

    private int num;
    private String question;
    private String code;
    private String choiceType;
    private String[] choice;
    private String[] correctAnswers;
    private String[] answers;
    private boolean correct;
    private boolean answered;
    private boolean active;

    public Question(String question, String code, String choiceType, String[] choice, String[] correctAnswers) {
        this.question = question;
        this.code = code;
        this.choiceType = choiceType;
        this.choice = choice;
        this.correctAnswers = correctAnswers;
        correct = false;
        answered = false;
        active = false;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
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
        this.correct = correct;
    }

    public boolean isCorrect() {
        return correct;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getChoiceType() {
        return choiceType;
    }
}
