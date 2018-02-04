package org.androidluckyguys.architecture.data.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Lakeba
 */

public class NetworkResponse<T> {

    public static final int SUCCESS = 1;
    public static final int FAILURE = 0;
    public static final int LOADING = 2;
    public static final int BAD_REQUEST = 3;
    public static final int NO_NETWORK = 4;
    public static final int UNAUTHORISED = 5;

    @NonNull
    public final int status;

    @Nullable
    public final T data;

    @Nullable
    public final String errorMessage;

    public NetworkResponse(@NonNull int status, @Nullable T data, String errorMessage) {
        this.status = status;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    @Nullable
    public static <T> NetworkResponse<T> noNetwork(@Nullable T data, String errorMessage) {
        return new NetworkResponse<>(NO_NETWORK,data,errorMessage);
    }

    @Nullable
    public static <T> NetworkResponse<T> success(@Nullable T data) {
        return new NetworkResponse<>(SUCCESS,data, null);
    }

    @Nullable
    public static <T> NetworkResponse<T> error(@Nullable T data, String errorMessage) {
        return new NetworkResponse<>(FAILURE,data,errorMessage);
    }

    @Nullable
    public static <T> NetworkResponse<T> badRequest(@Nullable T data, String errorMessage) {
        return new NetworkResponse<>(BAD_REQUEST,data,errorMessage);
    }

    @Nullable
    public static <T> NetworkResponse<T> unAuthorised(@Nullable T data) {
        return new NetworkResponse<>(UNAUTHORISED,data,null);
    }

    @Nullable
    public static <T> NetworkResponse<T> loading(@Nullable T data) {
        return new NetworkResponse<>(LOADING,data, null);
    }
}