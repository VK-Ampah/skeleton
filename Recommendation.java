import java.util.Date;

public class Recommendation {
    private int id;
    private int userId;
    private String recommendation;
    private Date date;

    // Constructor, getters, and setters
    public Recommendation(int id, int userId, String recommendation, Date date) {
        this.id = id;
        this.userId = userId;
        this.recommendation = recommendation;
        this.date = date;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public String toString() {
        return "Recommendation{" +
                "id=" + id +
                ", userId=" + userId +
                ", recommendation='" + recommendation + '\'' +
                ", date=" + date +
                '}';
    }
}
