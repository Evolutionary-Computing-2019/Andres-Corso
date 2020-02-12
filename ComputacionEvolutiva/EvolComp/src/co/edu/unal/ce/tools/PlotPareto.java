package co.edu.unal.ce.tools;


import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import co.edu.unal.ce.codeAC.Individual;
import co.edu.unal.ce.codeAC.TTPInd;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class PlotPareto  extends JFrame {
    public PlotPareto(String title, ArrayList<Individual<TTPInd>[]> paretoFront) {
        super(title);

        // Create dataset
        XYDataset dataset = createDataset(paretoFront);

        // Create chart
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Pareto Front",
                "Time", "Profit", dataset);


        //Changes background color
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint(new Color(255,255,255));


        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private XYDataset createDataset(ArrayList<Individual<TTPInd>[]> paretoFront) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        int i=0;
        for (Individual<TTPInd>[] F1: paretoFront){
            XYSeries series1 = new XYSeries(i);
            for (Individual<TTPInd> ind: F1){
                series1.add(ind.f(), ind.g());
            }
            dataset.addSeries(series1);
            i++;
        }

        return dataset;
    }

    public static void report(ArrayList<Individual<TTPInd>[]> paretoFront){
        PlotPareto example = new PlotPareto("Pareto Front", paretoFront);
        example.setSize(1200,800);
        example.setLocationRelativeTo(null);
        example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        example.setVisible(true);
    }
}
