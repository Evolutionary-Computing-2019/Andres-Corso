package co.edu.unal.ce.tools;

import javax.swing.*;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class Plot extends JFrame {
    JPanel panel;
    DefaultCategoryDataset line_chart_dataset;
    public Plot(ArrayList<ArrayList<Double>> seriesData, ArrayList<String> seriesNames){
        setTitle("Report");
        setSize(1200,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        line_chart_dataset = new DefaultCategoryDataset();
        for (int i=0;i<seriesData.size();i++) {
            putInfoDataSet(seriesData.get(i),seriesNames.get(i));
        }
        init();
    }

    private void init() {
        panel = new JPanel();
        getContentPane().add(panel);

        // Creando el Grafico
        JFreeChart chart= ChartFactory.createLineChart("Report",
                "Generation","Fitness",line_chart_dataset, PlotOrientation.VERTICAL,
                true,true,false);

        chart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        // Mostrar Grafico
        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(chartPanel);
    }
    private void putInfoDataSet(ArrayList<Double> data, String name){
        for (int i=0;i<data.size();i++){
            line_chart_dataset.addValue(data.get(i), name, Integer.toString(i));
        }

    }
    public static void report(ArrayList<ArrayList<Double>> bests) {
        double mean = 0;
        ArrayList<Double> means = new ArrayList<Double>();
        double max = 0;
        ArrayList<Double> maxs = new ArrayList<Double>();
        double min = 0;
        ArrayList<Double> mins = new ArrayList<Double>();

        int iter = bests.get(0).size();
        int ejec = bests.size();
        for (int i=0;i<iter;i++){
            mean = bests.get(0).get(i);
            min = bests.get(0).get(i);
            max = bests.get(0).get(i);
            for (int j=1;j<ejec;j++){
                double fitness = bests.get(j).get(i);
                mean += fitness;
                if (fitness < min){min = fitness;}
                if (fitness > max){max = fitness;}
            }
            means.add(mean/ejec);
            maxs.add(max);
            mins.add(min);
        }
        ArrayList<ArrayList<Double>> toPlot = new ArrayList<ArrayList<Double>>();
        ArrayList<String> toPlotNames = new ArrayList<String>();
        toPlot.add(maxs);
        toPlotNames.add("Maximum");
        toPlot.add(mins);
        toPlotNames.add("Minimum");
        toPlot.add(means);
        toPlotNames.add("Average");

        new Plot(toPlot,toPlotNames).setVisible(true);
    }

}