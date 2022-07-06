package com.abilitymap;

    public  class MapPoint {
        private String Name;
        private double latitude;
        private double longitude;

        public MapPoint(){
            super();
        }

        public MapPoint(String name, double latitude, double longitude){
            this.Name = Name;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public String getName(){
            return Name;
        }

        public void setName(String Name){
            this.Name = Name;
        }

        public Double getLatitude(){
            return latitude;
        }
        public void setLatitude(double latitude){
            this.latitude = latitude;
        }

        public Double getLongitude(){
            return longitude;
        }
        public void setLongitude(double longitude){
            this.longitude = longitude;
        }
    }

