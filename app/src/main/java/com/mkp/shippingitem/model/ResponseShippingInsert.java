package com.mkp.shippingitem.model;

public class ResponseShippingInsert {
    private String status;
    private String message;
    private String id;
    private String created_at;
    private String updated_at;
    private String delivery_number;
    private String creator;
    private String status2;
    private String note;


    public ResponseShippingInsert() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDelivery_number() {
        return delivery_number;
    }

    public void setDelivery_number(String delivery_number) {
        this.delivery_number = delivery_number;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "ShippingInsertPresenterApi{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", id='" + id + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", delivery_number='" + delivery_number + '\'' +
                ", creator='" + creator + '\'' +
                ", status2='" + status2 + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
