package analyser.charts;
import java.util.Map;

import javax.print.DocFlavor.STRING;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.util.Rotation;


public class Histogram extends JFrame {

  private static final long serialVersionUID = 1L;

  private JFreeChart chart;
  public Histogram(String applicationTitle, String chartTitle,Map<String,Integer> data) {
        super(applicationTitle);
        // This will create the dataset 
        CategoryDataset dataset = createDataset(data);
        // based on the dataset we create the chart
        this.chart = createChart(dataset, chartTitle);
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(this.chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        setContentPane(chartPanel);

    }
    
    

    
    private  PieDataset createDataset(Map<String,Integer> data) {
    	HistogramDataset dataset = new HistogramDataset();
    	
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
        	dataset.addSeries("", values, bins)
        	dataset.setValue(entry.getKey() , entry.getValue());
        }
        
        return dataset;
        
    }
/** * Creates a chart */

    private JFreeChart createChart(HistogramDataset dataset, String title,String xAxisLabel,String yAxisLabel) {
    
        JFreeChart chart = ChartFactory.createHistogram(
        	title, 
        	xAxisLabel,
        	yAxisLabel,
        	dataset, 
        	PlotOrientation.VERTICAL,
        	true,
        	true, 
        	false
        );
        		
           

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        StandardPieSectionLabelGenerator labelGenerator
    	= new StandardPieSectionLabelGenerator("{0} {2}");
    	plot.setLabelGenerator(labelGenerator);
    	plot.setLegendLabelGenerator(labelGenerator);
        return chart;
        
    }

	public JFreeChart getChartObj() {
		// TODO Auto-generated method stub
		return this.chart;
	}
} 