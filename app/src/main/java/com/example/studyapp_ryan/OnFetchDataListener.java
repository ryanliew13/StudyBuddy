package com.example.studyapp_ryan;

import com.example.studyapp_ryan.Model.APIResponse;

public interface OnFetchDataListener {
    void onFetchData(APIResponse apiResponse, String message);
    void onError(String message);
}
