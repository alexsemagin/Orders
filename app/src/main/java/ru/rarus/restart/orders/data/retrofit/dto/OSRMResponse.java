package ru.rarus.restart.orders.data.retrofit.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class OSRMResponse {
    @SerializedName("code")
    private String code;

    @SerializedName("routes")
    private List<Route> routes;

    @SerializedName("waypoints")
    private List<Waypoint> waypoints;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public class Route{
        @SerializedName("duration")
        private Double duration;

        @SerializedName("distance")
        private Double distance;

        @SerializedName("legs")
        private List<Leg> legs;

        public List<Leg> getLegs() {
            return legs;
        }

        public void setLegs(List<Leg> legs) {
            this.legs = legs;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public Double getDuration() {
            return duration;
        }

        public void setDuration(Double duration) {
            this.duration = duration;
        }
    }

    public class Leg {
        @SerializedName("steps")
        private List<String> steps;

        @SerializedName("summary")
        private String summary;

        @SerializedName("duration")
        private Double duration;

        @SerializedName("distance")
        private Double distance;

        public List<String> getSteps() {
            return steps;
        }

        public void setSteps(List<String> steps) {
            this.steps = steps;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public Double getDuration() {
            return duration;
        }

        public void setDuration(Double duration) {
            this.duration = duration;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }
    }

    public class Waypoint {
        @SerializedName("hint")
        private String hint;

        @SerializedName("name")
        private String name;

        @SerializedName("location")
        private List<Double> location;

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Double> getLocation() {
            return location;
        }

        public void setLocation(List<Double> location) {
            this.location = location;
        }
    }
}
