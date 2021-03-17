package moe.roco.commentsapi.entity.ApiStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiStatus<T> {
	private STATUS status;
	private T result;

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
