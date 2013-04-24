package analyser.charts;

import java.util.Map;

public class ChartUtils {

	public static void renderPieChart(String windowName,String chartTitle,Map<String,Integer>dataSet ){
		
		PieChart demo = null;
		demo = new PieChart(windowName,chartTitle,dataSet);
	    demo.pack();
	    demo.setVisible(true);
		
	}

}
