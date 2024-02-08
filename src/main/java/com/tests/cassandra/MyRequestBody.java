package com.tests.cassandra;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MyRequestBody {

    @SerializedName("from")
    private int from;

    @SerializedName("size")
    private int size;

    @SerializedName("query")
    private Query query;

    @SerializedName("sort")
    private Sort sort;

    // Getter e setter per le proprietà

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    // Getter e setter per Query

    public static class Query {

        @SerializedName("bool")
        private BoolQuery boolQuery;

        public BoolQuery getBoolQuery() {
            return boolQuery;
        }

        public void setBoolQuery(BoolQuery boolQuery) {
            this.boolQuery = boolQuery;
        }

        // Getter e setter per BoolQuery

        public static class BoolQuery {

            @SerializedName("must")
            private List<MustQuery> must;

            public List<MustQuery> getMust() {
                return must;
            }

            public void setMust(List<MustQuery> must) {
                this.must = must;
            }

            // Getter e setter per MustQuery

            public static class MustQuery {

                @SerializedName("terms")
                private Terms terms;

                public Terms getTerms() {
                    return terms;
                }

                public void setTerms(Terms terms) {
                    this.terms = terms;
                }

                // Getter e setter per Terms

                public static class Terms {

                    @SerializedName("sensor_id")
                    private List<Integer> sensorIds;

                    public List<Integer> getSensorIds() {
                        return sensorIds;
                    }

                    public void setSensorIds(List<Integer> sensorIds) {
                        this.sensorIds = sensorIds;
                    }
                }
            }
        }
    }

    // Getter e setter per Sort

    public static class Sort {

        @SerializedName("date")
        private String date;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}