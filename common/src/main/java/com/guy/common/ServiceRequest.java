package com.guy.common;

public class ServiceRequest {
    private String id;
    private String clientName;
    private String traderName;
    private String description;
    private double profit;
    private double loss;
    private String status;

    public ServiceRequest(String id, String clientName, String traderName, String description, double profit, double loss, String status) {
        this.id = id;
        this.clientName = clientName;
        this.traderName = traderName;
        this.description = description;
        this.profit = profit;
        this.loss = loss;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public String getTraderName() {
        return traderName;
    }

    public String getDescription() {
        return description;
    }

    public double getProfit() {
        return profit;
    }

    public double getLoss() {
        return loss;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
