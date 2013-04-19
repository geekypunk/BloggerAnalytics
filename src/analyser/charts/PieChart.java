package analyser.charts;
import java.util.Map;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;


public class PieChart extends JFrame {

  private static final long serialVersionUID = 1L;

  private JFreeChart chart;
  public PieChart(String applicationTitle, String chartTitle,Map<String,Integer> data) {
        super(applicationTitle);
        // This will create the dataset 
        PieDataset dataset = createDataset(data);
        // based on the dataset we create the chart
        this.chart = createChart(dataset, chartTitle);
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(this.chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        setContentPane(chartPanel);

    }
    
    
/** * Creates a sample dataset */

    private  PieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Linux", 29);
        result.setValue("Mac", 20);
        result.setValue("Windows", 51);
        return result;
        
    }
    
    private  PieDataset createDataset(Map<String,Integer> data) {
        DefaultPieDataset result = new DefaultPieDataset();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
        	result.setValue(entry.getKey() , entry.getValue());
        }
        
        return result;
        
    }
/** * Creates a chart */

    private JFreeChart createChart(PieDataset dataset, String title) {
        
        JFreeChart chart = ChartFactory.createPieChart3D(title,          // chart title
            dataset,                // data
            true,                   // include legend
            true,
            false);

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