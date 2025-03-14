package com.harry.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    // 0 for success, 1 for fail
    public static <E> Result success(Object data) {
        return new Result(0, "success", data);
    }

    public static <E> Result success() {
        return new Result(0, "success", null);
    }

    public static <E> Result error(String message) {
        return new Result(1, message, null);
    }

}
