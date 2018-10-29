package com.amostra.net.data.model;

public class Token {
    private String value;
    private String validate;
    private String create;

    public Token() {
        value = "";
        validate = "";
        create = "";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }
}
