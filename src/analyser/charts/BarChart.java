package analyser.charts;
import java.awt.Color;
import java.util.Map;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class BarChart extends JFrame {

  private static final long serialVersionUID = 1L;

  private JFreeChart chart;
  public BarChart(String applicationTitle, String chartTitle,String xAsisLabel,String yAxisLabel,Map<String,Integer> data) {
        super(applicationTitle);
        // This will create the dataset 
        DefaultCategoryDataset dataset  = createDataset(data);
        // based on the dataset we create the chart
        this.chart = createChart(dataset, chartTitle,xAsisLabel,yAxisLabel);
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(this.chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        setContentPane(chartPanel);

    }
    
    

    
    private  DefaultCategoryDataset createDataset(Map<String,Integer> data) {
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
        	
        	dataset.setValue(entry.getValue() ,"Count", entry.getKey());
        }
        
        return dataset;
        
    }
/** * Creates a chart */

    private JFreeChart createChart(DefaultCategoryDataset dataset, String title,String xAxisLabel,String yAxisLabel) {
     
    	JFreeChart chart = ChartFactory.createBarChart
    			  (title,xAxisLabel, yAxisLabel, dataset, 
    			   PlotOrientation.VERTICAL, false,true, false);	
    	chart.setBackgroundPaint(Color.WHITE);
    	chart.getTitle().setPaint(Color.blue); 
    	CategoryPlot p = chart.getCategoryPlot(); 
    	p.setRangeGridlinePaint(Color.BLUE); 
    	
        return chart;
        
    }

	public JFreeChart getChartObj() {
		// TODO Auto-generated method stub
		return this.chart;
	}
} 