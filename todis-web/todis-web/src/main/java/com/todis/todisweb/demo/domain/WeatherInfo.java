package com.todis.todisweb.demo.domain;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Data
public class WeatherInfo {
    public Response response;

    @Data
    static class Response {

        public Header header;
        public Body body;


        @Data
        static class Header {
            public String resultCode;
            public String resultMsg;
        }
        @Data
        static class Body {

            public String dataType;
            public Items items;
            public Integer pageNo;
            public Integer numOfRows;
            public Integer totalCount;


            @Data
            static class Items {
                public List<Item> item;
            }

            @Data
            static class Item {

                public String baseDate;
                public String baseTime;
                public String category;
                public String fcstDate;
                public String fcstTime;
                public String fcstValue;
                public Integer nx;
                public Integer ny;

            }


        }

    }
    public String getItem(WeatherInfo weatherInfo, String category) {
        for (int i = 0; i < weatherInfo.getResponse().getBody().getItems().getItem().size(); i++) {
            if (weatherInfo.getResponse().getBody().getItems().getItem().get(i).getCategory().equals(category)) {
                return weatherInfo.getResponse().getBody().getItems().getItem().get(i).getFcstValue();
            }
        }
        return null;
    }
}