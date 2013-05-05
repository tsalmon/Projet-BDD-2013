
import  org.jfree.chart.ChartFactory;
import  org.jfree.chart.ChartFrame;
import  org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import  org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;


public class SqlChart {

	private String[][] dessinable;
	
	
	  public SqlChart(){
		  this.dessinable = new String[3][2];
		  this.dessinable[0][0]="get_developpeurTop";this.dessinable[0][1]="barChart";
		  this.dessinable[1][0]="get_nbAppPeriphType";this.dessinable[1][1]="pieChart";
		  this.dessinable[2][0]="get_nbAppPeriphTypeMe";this.dessinable[2][1]="pieChart";
		  this.dessinable[3][0]="get_nbAppPeriphTypeID";this.dessinable[3][1]="pieChart";
		  this.dessinable[4][0]="get_nbAppCategorie";this.dessinable[4][1]="pieChart";
		  this.dessinable[5][0]="get_appTop";this.dessinable[5][1]="barChart";
		  this.dessinable[6][0]="get_appTopPayante";this.dessinable[6][1]="barChart";
	  }
	  
	  @SuppressWarnings("deprecation")
	public void print(String nom,SqlData s) {
		//create a dataset...*
		int estDessinable=-1;  
		  
		for(int i=0;i<this.dessinable.length;i++){
			if(this.dessinable[i][0].equals(nom)){estDessinable=i;break;}
		}
		
		
		if(estDessinable==-1)return ;
		
		if(this.dessinable[estDessinable][1].equals("pieChart")){
			DefaultPieDataset dataset=new DefaultPieDataset();
			
			for(int i=0;i<s.getNbLigne();i++)
			   dataset.setValue(s.getString(i, 0),s.getInt(i, 1));
	
			//create a chart...
			JFreeChart chart=ChartFactory.createPieChart3D( 
					   nom,
					   dataset,
					   true,
					   true,
					   false
		       );
			//create and display a frame...
			
			final PiePlot3D plot = (PiePlot3D) chart.getPlot();
	        plot.setStartAngle(290);
	        plot.setDirection(Rotation.CLOCKWISE);
	        plot.setForegroundAlpha(0.5f);
	        plot.setNoDataMessage("No data to display");
	        
	        ChartFrame frame=new ChartFrame("First",chart);
			frame.pack();
			frame.setVisible(true);
		}
		
		
		else if(this.dessinable[estDessinable][1].equals("barChart")){
			
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	        
			for(int i=0;i<s.getNbLigne();i++)
			    dataset.addValue(s.getInt(i,1), s.getString(i,0), s.getNomCol(1));   
	        
	        JFreeChart chart = ChartFactory.createBarChart3D(
	                nom,      // chart title
	                s.getNomCol(0),               // domain axis label
	                s.getNomCol(1),                  // range axis label
	                dataset,                  // data
	                PlotOrientation.VERTICAL, // orientation
	                true,                     // include legend
	                true,                     // tooltips
	                false                     // urls
	            );
	        
	        final CategoryPlot plot = chart.getCategoryPlot();
	        final CategoryAxis axis = plot.getDomainAxis();
	        axis.setCategoryLabelPositions(
	            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 8.0)
	        );
	        
	        final CategoryItemRenderer renderer = plot.getRenderer();
	        renderer.setItemLabelsVisible(true);
	        final BarRenderer r = (BarRenderer) renderer;
	        r.setMaximumBarWidth(0.05);
	        
			ChartFrame frame=new ChartFrame("First",chart);
			frame.pack();
			frame.setVisible(true);
		}
        
        
	
	  }
}
