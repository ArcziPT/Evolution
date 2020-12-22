package com.arczipt.ewolucja.simulation.models;

public class Config {
    private Integer moveEnergy = 1;
    private Integer defaultEnergy = 10;
    private Integer defaultAnimalNumber = 10;
    private Integer defaultPlantEnergy = 4;

    private Double jungleRatio = 0.1;

    private Integer defaultJunglePlantNumber = 5;
    private Integer defaultPlantNumber = 7;

    private Integer x = 20;
    private Integer y = 20;

    private Integer mapNumber = 1;
    private Double refreshTime = 0.1;

    private Integer saveStatistics = 40;

    public Integer getDefaultJunglePlantNumber() {
        return defaultJunglePlantNumber;
    }

    public void setDefaultJunglePlantNumber(Integer defaultJunglePlantNumber) {
        this.defaultJunglePlantNumber = defaultJunglePlantNumber;
    }

    public Integer getDefaultPlantNumber() {
        return defaultPlantNumber;
    }

    public void setDefaultPlantNumber(Integer defaultPlantNumber) {
        this.defaultPlantNumber = defaultPlantNumber;
    }

    public Integer getMoveEnergy() {
        return moveEnergy;
    }

    public void setMoveEnergy(Integer moveEnergy) {
        this.moveEnergy = moveEnergy;
    }

    public Integer getDefaultEnergy() {
        return defaultEnergy;
    }

    public void setDefaultEnergy(Integer defaultEnergy) {
        this.defaultEnergy = defaultEnergy;
    }

    public Integer getDefaultAnimalNumber() {
        return defaultAnimalNumber;
    }

    public void setDefaultAnimalNumber(Integer defaultAnimalNumber) {
        this.defaultAnimalNumber = defaultAnimalNumber;
    }

    public Integer getDefaultPlantEnergy() {
        return defaultPlantEnergy;
    }

    public void setDefaultPlantEnergy(Integer defaultPlantEnergy) {
        this.defaultPlantEnergy = defaultPlantEnergy;
    }

    public Double getJungleRatio(){
        return jungleRatio;
    }

    public void setJungleRatio(Double jungleRatio){
        this.jungleRatio = jungleRatio;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(Integer mapNumber) {
        this.mapNumber = mapNumber;
    }

    public Double getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Double refreshTime) {
        this.refreshTime = refreshTime;
    }

    public Integer getSaveStatistics() {
        return saveStatistics;
    }

    public void setSaveStatistics(Integer saveStatistics) {
        this.saveStatistics = saveStatistics;
    }
}
