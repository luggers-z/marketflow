package dev.luggers;

import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public class UIUpdater {
    private static final int MAX_POINTS = 5;
    LineChart<Number, Number> rightChart;
    NumberAxis yAxisInflow;
    NumberAxis xAxisInflow;
    LineChart<Number, Number> leftChart;
    NumberAxis yAxisEnergyPrice;
    NumberAxis xAxisEnergyPrice;
    Label timeLabel;
    Simulation simulation;
    XYChart.Series<Number, Number> seriesInflow = new XYChart.Series<>();
    XYChart.Series<Number, Number> seriesEnergyPrice = new XYChart.Series<>();
    public UIUpdater(Simulation sim, Controller controller) {
        assign(sim, controller);

        chartSettings();
        hourlyListener();

    }
    private void assign(Simulation sim, Controller controller) {
        simulation = sim;
        rightChart = controller.getRightChart();
        leftChart = controller.getLeftChart();
        timeLabel = controller.getTimeLabel();
        rightChart.getData().add(seriesInflow);
        leftChart.getData().add(seriesEnergyPrice);

        yAxisEnergyPrice = (NumberAxis) leftChart.getYAxis();
        xAxisEnergyPrice = (NumberAxis) leftChart.getXAxis();
        yAxisInflow = (NumberAxis) rightChart.getYAxis();
        xAxisInflow = (NumberAxis) rightChart.getXAxis();
    }
    private void hourlyListener() {
        simulation.simulationClock.hourProperty().addListener((observable, oldValue, newValue) -> {

                chartUpdater(newValue);

                timeUpdater(newValue);

        });
    }
    private void chartSettings() {

        rightChart.setLegendVisible(false);

        leftChart.setLegendVisible(false);

        yAxisInflow.setAutoRanging(false);
        xAxisInflow.setAutoRanging(false);
        xAxisInflow.setTickUnit(1);

        yAxisEnergyPrice.setAutoRanging(false);
        xAxisEnergyPrice.setAutoRanging(false);
        xAxisEnergyPrice.setTickUnit(1);

    }

    private void chartUpdater(Number newValue){
            double fullTime = newValue.doubleValue()*3600;
            int dataSize = seriesInflow.getData().size();
            int limit = Math.min(10, dataSize);
            double inflow = simulation.inflowRepository.getInflow(fullTime);

            seriesInflow.getData().add(new XYChart.Data(newValue, inflow));
            xAxisInflow.setLowerBound(newValue.doubleValue()-limit);
            xAxisInflow.setUpperBound(newValue.doubleValue()+11-limit);



            double maximum = Double.NEGATIVE_INFINITY;
            double minimum = Double.POSITIVE_INFINITY;

            for(int i=1; i<=limit; i++){
                double lastY = seriesInflow.getData()
                        .get(seriesInflow
                                .getData().size() - i)
                        .getYValue().doubleValue();
                if (lastY > maximum) maximum = lastY;
                if (lastY < minimum) minimum = lastY;
            }
            double delta=maximum-minimum;
            yAxisInflow.setLowerBound(minimum-0.1*delta);
            yAxisInflow.setUpperBound(maximum+0.1*delta);

            double Price = simulation.energyPriceRepository.getPrice(fullTime);

            seriesEnergyPrice.getData().add(new XYChart.Data(newValue, Price));
            xAxisEnergyPrice.setLowerBound(Math.floor(newValue.doubleValue()-limit));
            xAxisEnergyPrice.setUpperBound(Math.floor(newValue.doubleValue()+11-limit));

            dataSize = seriesEnergyPrice.getData().size();
            limit = Math.min(10, dataSize);
            maximum = Double.NEGATIVE_INFINITY;
            minimum = Double.POSITIVE_INFINITY;

            for(int i=1; i<=limit; i++){
                double lastY = seriesEnergyPrice.getData()
                        .get(seriesEnergyPrice
                                .getData().size() - i)
                        .getYValue().doubleValue();
                if (lastY > maximum) maximum = lastY;
                if (lastY < minimum) minimum = lastY;
            }
            delta=maximum-minimum;

            yAxisEnergyPrice.setLowerBound(Math.floor(minimum-0.1*delta));
            yAxisEnergyPrice.setUpperBound(Math.floor(maximum+0.1*delta));
        if (seriesInflow.getData().size() > 10)
            seriesInflow.getData().remove(0);
        if (seriesEnergyPrice.getData().size() > 10)
            seriesEnergyPrice.getData().remove(0);

    }
    public void timeUpdater(Number newValue){

        int totalHours= newValue.intValue();
        int hours= totalHours%24;
        int days= totalHours/24%7+1;
        int weeks=totalHours/24/7;


        String weekdayString= "Monday";

        switch (days) {
            case 1 -> weekdayString = "Monday";
            case 2 -> weekdayString = "Tuesday";
            case 3 -> weekdayString = "Wednesday";
            case 4 -> weekdayString = "Thursday";
            case 5 -> weekdayString = "Friday";
            case 6 -> weekdayString = "Saturday";
            case 7 -> weekdayString = "Sunday";
        }
        timeLabel.setText(String.format(weekdayString + ": Hour:%02d Day:%02d Week:%02d", (hours%24), days, weeks));
    }

}

