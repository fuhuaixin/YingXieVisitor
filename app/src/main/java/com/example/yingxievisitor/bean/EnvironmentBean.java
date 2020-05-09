package com.example.yingxievisitor.bean;

public class EnvironmentBean {

    /**
     * code : 0000
     * data : {"monitor":{"winspeed":"2.10","pm25":"42.00","illumination":"79994.00","rainvalue":"45.00","temperature":"24.70","humidity":"24.90","windirection":"东北风"},"weather":{"date":"2020-04-28","air_level":"良","win_meter":"小于12km/h","createtime":{"date":28,"day":2,"hours":13,"minutes":0,"month":3,"nanos":10473000,"seconds":2,"time":1588050002010,"timezoneOffset":-480,"year":120},"week":"星期二","visibility":"17.43km","cityid":"101180112","air":"73","pressure":"1007","air_pm25":"73","update_time":"09:42","wea":"晴","dataid":"7e66a8ad-5525-4b4d-8ebc-f23530191337","air_tips":"空气好，可以外出活动，除极少数对污染物特别敏感的人群以外，对公众没有危害！","wea_img":"qing","humidity":"41%","win_speed":"3级","tem":"19","win":"南风"}}
     * message : 查询成功
     * status : true
     */

    private String code;
    private DataBean data;
    private String message;
    private boolean status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * monitor : {"winspeed":"2.10","pm25":"42.00","illumination":"79994.00","rainvalue":"45.00","temperature":"24.70","humidity":"24.90","windirection":"东北风"}
         * weather : {"date":"2020-04-28","air_level":"良","win_meter":"小于12km/h","createtime":{"date":28,"day":2,"hours":13,"minutes":0,"month":3,"nanos":10473000,"seconds":2,"time":1588050002010,"timezoneOffset":-480,"year":120},"week":"星期二","visibility":"17.43km","cityid":"101180112","air":"73","pressure":"1007","air_pm25":"73","update_time":"09:42","wea":"晴","dataid":"7e66a8ad-5525-4b4d-8ebc-f23530191337","air_tips":"空气好，可以外出活动，除极少数对污染物特别敏感的人群以外，对公众没有危害！","wea_img":"qing","humidity":"41%","win_speed":"3级","tem":"19","win":"南风"}
         */

        private MonitorBean monitor;
        private WeatherBean weather;

        public MonitorBean getMonitor() {
            return monitor;
        }

        public void setMonitor(MonitorBean monitor) {
            this.monitor = monitor;
        }

        public WeatherBean getWeather() {
            return weather;
        }

        public void setWeather(WeatherBean weather) {
            this.weather = weather;
        }

        public static class MonitorBean {
            /**
             * winspeed : 2.10
             * pm25 : 42.00
             * illumination : 79994.00
             * rainvalue : 45.00
             * temperature : 24.70
             * humidity : 24.90
             * windirection : 东北风
             */

            private String winspeed;
            private String pm25;
            private String illumination;
            private String rainvalue;
            private String temperature;
            private String humidity;
            private String windirection;

            public String getWinspeed() {
                return winspeed;
            }

            public void setWinspeed(String winspeed) {
                this.winspeed = winspeed;
            }

            public String getPm25() {
                return pm25;
            }

            public void setPm25(String pm25) {
                this.pm25 = pm25;
            }

            public String getIllumination() {
                return illumination;
            }

            public void setIllumination(String illumination) {
                this.illumination = illumination;
            }

            public String getRainvalue() {
                return rainvalue;
            }

            public void setRainvalue(String rainvalue) {
                this.rainvalue = rainvalue;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getWindirection() {
                return windirection;
            }

            public void setWindirection(String windirection) {
                this.windirection = windirection;
            }
        }

        public static class WeatherBean {
            /**
             * date : 2020-04-28
             * air_level : 良
             * win_meter : 小于12km/h
             * createtime : {"date":28,"day":2,"hours":13,"minutes":0,"month":3,"nanos":10473000,"seconds":2,"time":1588050002010,"timezoneOffset":-480,"year":120}
             * week : 星期二
             * visibility : 17.43km
             * cityid : 101180112
             * air : 73
             * pressure : 1007
             * air_pm25 : 73
             * update_time : 09:42
             * wea : 晴
             * dataid : 7e66a8ad-5525-4b4d-8ebc-f23530191337
             * air_tips : 空气好，可以外出活动，除极少数对污染物特别敏感的人群以外，对公众没有危害！
             * wea_img : qing
             * humidity : 41%
             * win_speed : 3级
             * tem : 19
             * win : 南风
             */

            private String date;
            private String air_level;
            private String win_meter;
            private CreatetimeBean createtime;
            private String week;
            private String visibility;
            private String cityid;
            private String air;
            private String pressure;
            private String air_pm25;
            private String update_time;
            private String wea;
            private String dataid;
            private String air_tips;
            private String wea_img;
            private String humidity;
            private String win_speed;
            private String tem;
            private String win;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getAir_level() {
                return air_level;
            }

            public void setAir_level(String air_level) {
                this.air_level = air_level;
            }

            public String getWin_meter() {
                return win_meter;
            }

            public void setWin_meter(String win_meter) {
                this.win_meter = win_meter;
            }

            public CreatetimeBean getCreatetime() {
                return createtime;
            }

            public void setCreatetime(CreatetimeBean createtime) {
                this.createtime = createtime;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getVisibility() {
                return visibility;
            }

            public void setVisibility(String visibility) {
                this.visibility = visibility;
            }

            public String getCityid() {
                return cityid;
            }

            public void setCityid(String cityid) {
                this.cityid = cityid;
            }

            public String getAir() {
                return air;
            }

            public void setAir(String air) {
                this.air = air;
            }

            public String getPressure() {
                return pressure;
            }

            public void setPressure(String pressure) {
                this.pressure = pressure;
            }

            public String getAir_pm25() {
                return air_pm25;
            }

            public void setAir_pm25(String air_pm25) {
                this.air_pm25 = air_pm25;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getWea() {
                return wea;
            }

            public void setWea(String wea) {
                this.wea = wea;
            }

            public String getDataid() {
                return dataid;
            }

            public void setDataid(String dataid) {
                this.dataid = dataid;
            }

            public String getAir_tips() {
                return air_tips;
            }

            public void setAir_tips(String air_tips) {
                this.air_tips = air_tips;
            }

            public String getWea_img() {
                return wea_img;
            }

            public void setWea_img(String wea_img) {
                this.wea_img = wea_img;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getWin_speed() {
                return win_speed;
            }

            public void setWin_speed(String win_speed) {
                this.win_speed = win_speed;
            }

            public String getTem() {
                return tem;
            }

            public void setTem(String tem) {
                this.tem = tem;
            }

            public String getWin() {
                return win;
            }

            public void setWin(String win) {
                this.win = win;
            }

            public static class CreatetimeBean {
                /**
                 * date : 28
                 * day : 2
                 * hours : 13
                 * minutes : 0
                 * month : 3
                 * nanos : 10473000
                 * seconds : 2
                 * time : 1588050002010
                 * timezoneOffset : -480
                 * year : 120
                 */

                private int date;
                private int day;
                private int hours;
                private int minutes;
                private int month;
                private int nanos;
                private int seconds;
                private long time;
                private int timezoneOffset;
                private int year;

                public int getDate() {
                    return date;
                }

                public void setDate(int date) {
                    this.date = date;
                }

                public int getDay() {
                    return day;
                }

                public void setDay(int day) {
                    this.day = day;
                }

                public int getHours() {
                    return hours;
                }

                public void setHours(int hours) {
                    this.hours = hours;
                }

                public int getMinutes() {
                    return minutes;
                }

                public void setMinutes(int minutes) {
                    this.minutes = minutes;
                }

                public int getMonth() {
                    return month;
                }

                public void setMonth(int month) {
                    this.month = month;
                }

                public int getNanos() {
                    return nanos;
                }

                public void setNanos(int nanos) {
                    this.nanos = nanos;
                }

                public int getSeconds() {
                    return seconds;
                }

                public void setSeconds(int seconds) {
                    this.seconds = seconds;
                }

                public long getTime() {
                    return time;
                }

                public void setTime(long time) {
                    this.time = time;
                }

                public int getTimezoneOffset() {
                    return timezoneOffset;
                }

                public void setTimezoneOffset(int timezoneOffset) {
                    this.timezoneOffset = timezoneOffset;
                }

                public int getYear() {
                    return year;
                }

                public void setYear(int year) {
                    this.year = year;
                }
            }
        }
    }
}
