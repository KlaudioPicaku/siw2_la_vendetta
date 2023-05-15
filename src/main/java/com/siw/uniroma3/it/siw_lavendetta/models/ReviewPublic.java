package com.siw.uniroma3.it.siw_lavendetta.models;

public class ReviewPublic {
    String reviewTitle;
    String reviewBody;

    String reviewAuthor;

    int reviewRating;

    String reviewPic;


    public ReviewPublic(String reviewTitle, String reviewBody, String reviewAuthor, int reviewRating,String reviewPic) {
        this.reviewTitle = reviewTitle;
        this.reviewBody = reviewBody;
        this.reviewAuthor = reviewAuthor;
        this.reviewRating = reviewRating;
        this.reviewPic=reviewPic;

    }

    public String getReviewPic() {
        return reviewPic;
    }

    public void setReviewPic(String reviewPic) {
        this.reviewPic = reviewPic;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewBody() {
        return reviewBody;
    }

    public void setReviewBody(String reviewBody) {
        this.reviewBody = reviewBody;
    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }
}
