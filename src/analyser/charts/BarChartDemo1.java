package analyser.charts;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class BarChartDemo1 extends ApplicationFrame {

  public BarChartDemo1(String title) {
  super(title);

  final CategoryDataset dataset = createDataset();
  final JFreeChart chart = createChart(dataset);

  final ChartPanel chartPanel = new ChartPanel(chart);
  chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
  setContentPane(chartPanel);
  }

  private CategoryDataset createDataset() { 
  final double[][] data = new double[][] {
  {210,300,320,265,299},
 
  };
 return DatasetUtilities.createCategoryDataset("Team ", 
   "Match ", data);
  }

  private JFreeChart createChart(final CategoryDataset dataset) {

  final JFreeChart chart = ChartFactory.createBarChart(
  "Bar Chart Demo", "Category", "Score", dataset,
  PlotOrientation.VERTICAL, true, true, false);
  return chart;
  }

  public static void main(final String[] args) {
 BarChartDemo1 chart = new BarChartDemo1("Vertical Bar Chart Demo");
  chart.pack();
  RefineryUtilities.centerFrameOnScreen(chart);
  chart.setVisible(true);
  }
}