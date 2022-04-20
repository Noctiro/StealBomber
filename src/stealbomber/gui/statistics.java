package stealbomber.gui;

import javax.swing.BorderFactory;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class statistics {
    public static void basic() {
        stealbomber.gui.main.statistics.setBorder(BorderFactory.createTitledBorder("统计区"));

        double[] xData = new double[] { 0.0, 1.0, 2.0 };
        double[] yData = new double[] { 2.0, 1.0, 0.0 };

        // Create Chart
        XYChart chart = QuickChart.getChart("统计", "时间", "次数", "成功", xData, yData);

        // Show it
        new SwingWrapper(chart).displayChart();
    }
}
