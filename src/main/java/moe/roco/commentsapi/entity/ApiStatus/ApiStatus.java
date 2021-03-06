package moe.roco.commentsapi.entity.ApiStatus;

import lombok.Getter;
import lombok.Setter;
import moe.roco.commentsapi.enums.STATUS;

@Getter
@Setter
public class ApiStatus<T> {
    private STATUS status;
    private T result;
    private String message;

    public ApiStatus() {
        this.status = STATUS.FAILURE;
    }

    public ApiStatus(T result) {
        setResult(result);
    }

    public void setResult(T result) {
        this.status = result != null ? STATUS.SUCCESS : STATUS.FAILURE;
        this.result = result;
    }
}
