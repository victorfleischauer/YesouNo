package br.ifsul.yesouno;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YesNo {

    @SerializedName("answer")
    @Expose
    private String answer;

    @SerializedName("forced")
    @Expose
    private Boolean forced;

    @SerializedName("image")
    @Expose
    private String image;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getForced() {
        return forced;
    }

    public void setForced(Boolean forced) {
        this.forced = forced;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
