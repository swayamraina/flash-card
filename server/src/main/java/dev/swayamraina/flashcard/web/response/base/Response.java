package dev.swayamraina.flashcard.web.response.base;

public class Response<T> {

    private T data;
    public T getData() { return data; }



    private Response () {}

    public Response (T data) { this.data = data; }

}
