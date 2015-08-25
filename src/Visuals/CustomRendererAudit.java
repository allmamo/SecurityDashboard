package Visuals;

import org.jfree.chart.renderer.category.BarRenderer; 
import java.awt.Color; 
import java.awt.Paint;
class CustomRendererAudit extends BarRenderer 
{ 
 private Paint[] colors;
 public CustomRendererAudit() 
 { 
    this.colors = new Paint[] {new Color(47, 196, 6),new Color(230, 27, 27)}; 
 }
 public Paint getItemPaint(final int row, final int column) 
 { 
    // returns color for each column 
    return (this.colors[column % this.colors.length]); 
 } 
}