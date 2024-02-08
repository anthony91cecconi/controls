package com.tests.cassandra;

import java.util.List;

public class MyResponse {

    private String _index;
    private String _type;
    private String _id;
    private Object _score;
    private MySource _source;
    private List<Long> sort;
    public Object hits;

    public static class MySource {

        private String date;
        private int sensor_id;
        private boolean realtime;
        private Double value;
        private double sampling_frequency;

        // Getter e setter per le proprietà di MySource

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getSensor_id() {
            return sensor_id;
        }

        public void setSensor_id(int sensor_id) {
            this.sensor_id = sensor_id;
        }

        public boolean isRealtime() {
            return realtime;
        }

        public void setRealtime(boolean realtime) {
            this.realtime = realtime;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public double getSampling_frequency() {
            return sampling_frequency;
        }

        public void setSampling_frequency(double sampling_frequency) {
            this.sampling_frequency = sampling_frequency;
        }
    }

    // Getter e setter per le proprietà di MyResponse

    public String get_index() {
        return _index;
    }

    public void set_index(String _index) {
        this._index = _index;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Object get_score() {
        return _score;
    }

    public void set_score(Object _score) {
        this._score = _score;
    }

    public MySource get_source() {
        return _source;
    }

    public void set_source(MySource _source) {
        this._source = _source;
    }

    public List<Long> getSort() {
        return sort;
    }

    public void setSort(List<Long> sort) {
        this.sort = sort;
    }
}