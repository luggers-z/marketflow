package dev.luggers;

import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class ChartUpdater {
    private static final int MAX_POINTS = 5;
    LineChart<Number, Number> rightChart;
    LineChart<Number, Number> leftChart;
    Simulation simulation;
    public ChartUpdater(Simulation sim, Controller controller) {
        rightChart = controller.getRightChart();
        rightChart.setLegendVisible(false);
        leftChart = controller.getLeftChart();
        leftChart.setLegendVisible(false);
        simulation = sim;
        updateCharts();
    }
    public void updateCharts(){
        XYChart.Series<Number, Number> seriesInflow = new XYChart.Series<>();
        rightChart.getData().add(seriesInflow);
        NumberAxis yAxisInflow = (NumberAxis) rightChart.getYAxis();
        NumberAxis xAxisInflow = (NumberAxis) rightChart.getXAxis();

        yAxisInflow.setAutoRanging(false);
        xAxisInflow.setAutoRanging(false);
        xAxisInflow.setTickUnit(1);

        XYChart.Series<Number, Number> seriesEnergyPrice = new XYChart.Series<>();
        leftChart.getData().add(seriesEnergyPrice);
        NumberAxis yAxisEnergyPrice = (NumberAxis) leftChart.getYAxis();
        NumberAxis xAxisEnergyPrice = (NumberAxis) leftChart.getXAxis();

        yAxisEnergyPrice.setAutoRanging(false);
        xAxisEnergyPrice.setAutoRanging(false);
        xAxisEnergyPrice.setTickUnit(1);

        simulation.simulationClock.hour.addListener((observable, oldValue, newValue) -> {
            double fullTime = newValue.doubleValue()*3600;
            int dataSize = seriesInflow.getData().size();
            int limit = Math.min(10, dataSize);
            double inflow = simulation.inflowRepository.getInflow(fullTime);

            seriesInflow.getData().add(new XYChart.Data(newValue, inflow));
            xAxisInflow.setLowerBound(newValue.doubleValue()-limit);
            xAxisInflow.setUpperBound(newValue.doubleValue()+9-limit);



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
            xAxisEnergyPrice.setLowerBound(newValue.doubleValue()-limit);
            xAxisEnergyPrice.setUpperBound(newValue.doubleValue()+9-limit);

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

            yAxisEnergyPrice.setLowerBound(minimum-0.1*delta);
            yAxisEnergyPrice.setUpperBound(maximum+0.1*delta);

        });
    }

}

