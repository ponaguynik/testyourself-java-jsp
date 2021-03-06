package model;

public class TestResult {

    private String result;
    private String unansweredQnsCount;
    private String duration;
    private String date;
    private String time;
    private Question question;

    public TestResult(String date, String time, String result, String duration) {
        this.date = date;
        this.time = time;
        this.result = result;
        this.duration = duration;
    }

    public TestResult(String date, String time, String result, String duration,
                      String unansweredQnsCount, Question question) {
        this.date = date;
        this.time = time;
        this.result = result;
        this.duration = duration;
        this.unansweredQnsCount = unansweredQnsCount;
        this.question = question;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUnansweredQnsCount() {
        return unansweredQnsCount;
    }

    public void setUnansweredQnsCount(String unansweredQnsCount) {
        this.unansweredQnsCount = unansweredQnsCount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
