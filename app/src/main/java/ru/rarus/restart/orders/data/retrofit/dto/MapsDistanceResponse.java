package ru.rarus.restart.orders.data.retrofit.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lysmik on 28.12.16.
 */

public class MapsDistanceResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("origin_addresses")
    private List<String> originAddresses;

    @SerializedName("destination_addresses")
    private List<String> destinationAddresses;

    @SerializedName("rows")
    private List<RowElement> rows;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getOriginAddresses() {
        return originAddresses;
    }

    public void setOriginAddresses(List<String> originAddresses) {
        this.originAddresses = originAddresses;
    }

    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    public void setDestinationAddresses(List<String> destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }

    public List<RowElement> getRows() {
        return rows;
    }

    public void setRows(List<RowElement> rows) {
        this.rows = rows;
    }

    public class RowElement {
        @SerializedName("elements")
        public List<Element> elements;

        public List<Element> getElements() {
            return elements;
        }

        public void setElements(List<Element> elements) {
            this.elements = elements;
        }
    }

    public class Element {
        @SerializedName("status")
        private String status;

        @SerializedName("duration")

        private TextValue duration;

        @SerializedName("distance")

        private TextValue distance;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public TextValue getDuration() {
            return duration;
        }

        public void setDuration(TextValue duration) {
            this.duration = duration;
        }

        public TextValue getDistance() {
            return distance;
        }

        public void setDistance(TextValue distance) {
            this.distance = distance;
        }
    }

    public class TextValue {
        @SerializedName("value")
        private String value;

        @SerializedName("text")
        private String text;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
