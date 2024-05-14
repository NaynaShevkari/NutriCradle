package org.example;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class ViewProgress extends JPanel {
  private Controller controller;
  private List<ArrayList<?>> data;

  public ViewProgress(Controller controller, List<ArrayList<?>> data) {
    this.controller = controller;
    this.data = data;
    initUI();
  }

  private void initUI() {
    XYSeriesCollection primaryDataset = new XYSeriesCollection();
    XYSeriesCollection secondaryDataset = new XYSeriesCollection();
    XYSeries caloriesSeries = new XYSeries("Calories(kcal)");
    XYSeries proteinSeries = new XYSeries("Protein(grams)");

    ArrayList<?> dates = data.get(0);
    ArrayList<?> calories = data.get(1);
    ArrayList<?> protein = data.get(2);

    for (int i = 0; i < dates.size(); i++) {
      Number xValue = i;
      caloriesSeries.add(xValue, (Number) calories.get(i));
      proteinSeries.add(xValue, (Number) protein.get(i));
    }

    primaryDataset.addSeries(caloriesSeries);
    secondaryDataset.addSeries(proteinSeries);

    JFreeChart chart = ChartFactory.createXYLineChart("Calorie/Protein Consumption ", "Time", "Calories",
        primaryDataset, PlotOrientation.VERTICAL, true, true, false);

    XYPlot plot = chart.getXYPlot();
    NumberAxis secondaryAxis = new NumberAxis("Protein");
    plot.setRangeAxis(1, secondaryAxis);
    plot.setDataset(1, secondaryDataset);
    plot.mapDatasetToRangeAxis(1, 1);

    XYLineAndShapeRenderer primaryRenderer = new XYLineAndShapeRenderer();
    XYLineAndShapeRenderer secondaryRenderer = new XYLineAndShapeRenderer();

    primaryRenderer.setSeriesPaint(0, Color.BLUE);
    primaryRenderer.setSeriesStroke(0, new BasicStroke(2.0f));
    plot.setRenderer(0, primaryRenderer);

    secondaryRenderer.setSeriesPaint(0, Color.GREEN);
    secondaryRenderer.setSeriesStroke(0, new BasicStroke(2.0f));
    plot.setRenderer(1, secondaryRenderer);

    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
    add(chartPanel);
  }
}

