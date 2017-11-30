
package com.scaleup.epoints.http.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignUp {

    @SerializedName("html_response")
    @Expose
    private String html_response;

    public String getHtml_response() {
        return html_response;
    }

    public void setHtml_response(String html_response) {
        this.html_response = html_response;
    }
}
