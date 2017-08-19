package com.project.uwm.mydiabitiestracker.Objects;

/**
 * Created by Anitha on 8/3/2017.
 */

public class ReminderObject {

        private long id;
        private String alarmTime;
        private boolean oneTime;
        private boolean active;
        private String label;
        private String metric;

        public ReminderObject() {
        }

        public ReminderObject(long id, String alarmTime, String label, String metric, boolean oneTime, boolean active) {
            this.id = id;
            this.label = label;
            this.alarmTime = alarmTime;
            this.metric = metric;
            this.oneTime = oneTime;
            this.active = active;
        }

        public String getAlarmTime() {
            return alarmTime;
        }

        public void setAlarmTime(String alarmTime) {
            this.alarmTime = alarmTime;
        }

        public boolean isOneTime() {
            return oneTime;
        }

        public void setOneTime(boolean oneTime) {
            this.oneTime = oneTime;
        }

        public String getMetric() {
            return metric;
        }

        public void setMetric(String metric) {
            this.metric = metric;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public void setLabel(String label){
            this.label = label;
        }

        public String getLabel(){
            return label;
        }
}
