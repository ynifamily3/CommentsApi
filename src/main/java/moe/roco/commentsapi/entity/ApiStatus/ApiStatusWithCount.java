package moe.roco.commentsapi.entity.ApiStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiStatusWithCount<T> extends ApiStatus<T> {
	long count;
}
