package Visuals;

import org.jfree.chart.renderer.category.BarRenderer; 
import java.awt.Color; 
import java.awt.Paint;
class CustomRenderer extends BarRenderer 
{ 
 private Paint[] colors;
 public CustomRenderer() 
 { 
    this.colors = new Paint[] {new Color(230, 219, 27),new Color(85, 144, 176),new Color(230, 90, 27),new Color(230, 27, 27)}; 
 }
 public Paint getItemPaint(final int row, final int column) 
 { 
    // returns color for each column 
    return (this.colors[column % this.colors.length]); 
 } 
}