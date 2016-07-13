package cn.InstFS.wkr.NetworkMining.ResultDisplay.UI;

/**
 * Created by hidebumi on 2016/3/30.
 */
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JPanel;
import javax.swing.text.SimpleAttributeSet;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.AbstractXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

import cn.InstFS.wkr.NetworkMining.DataInputs.DataItem;
import cn.InstFS.wkr.NetworkMining.DataInputs.DataItems;
public class ChartPanelShowPP extends JPanel {
    JFreeChart chart;
    Shape itemShape; // = new Ellipse2D.Double(-2,-2, 4, 4);
    private static Map<Object, Color> colorTable = new HashMap<>();
    private static int colorCount;
    private static Random random = new Random();


    private ChartPanelShowPP() {
        // 创建主题样式
        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
        // 设置标题字体
        standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 15));
        // 设置图例的字体
        standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 10));
        // 设置轴向的字体
        standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 10));
        // 应用主题样式
        ChartFactory.setChartTheme(standardChartTheme);

        setLayout(new BorderLayout());

    }

    ChartPanelShowPP(String title, String timeAxisLabel, String valueAxisLabel,
                     XYDataset dataset/*, boolean legend, boolean tooltips, boolean urls*/) {
        this();
//        chart = ChartFactory.createTimeSeriesChart(title, timeAxisLabel, valueAxisLabel, dataset);
//        ChartPanel p = new ChartPanel(chart);
//        add(p, BorderLayout.CENTER);
//
//
//        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) chart.getXYPlot().getRenderer();
//
//        renderer.setBaseShapesVisible(false);
////		renderer.setBaseShape(itemShape);	// 好像不管用，必须用setSeriesShape
//        renderer.setBaseLinesVisible(true);
////		renderer.setBasePaint(new Color(0));	// 好像不管用，必须用setSeriesPaint
//
//        itemShape = ShapeUtilities.createDiamond((float) 3);
//        renderer.setSeriesShape(0, itemShape);
//        renderer.setSeriesPaint(0, new Color(255,0,0));
//
//        renderer.setSeriesShape(1, itemShape);
//        renderer.setSeriesPaint(1, new Color(0,255,0));
//
//        renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator("{0}:({1} , {2})", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), new DecimalFormat("#.00")));
    }


    public void setTitle(String title) {
        chart.setTitle(title);
    }

    public void setAxisXLabel(String x) {
        chart.getXYPlot().getDomainAxis().setLabel(x);
    }

    public void setAxisYLabel(String y) {
        chart.getXYPlot().getRangeAxis().setLabel(y);
    }

    public JFreeChart getChart() {
        return chart;
    }

    public void displayDataItems(DataItems items) {
        if (items == null)
            return;
        TimeSeriesCollection tsc = new TimeSeriesCollection();

        TimeSeries ts = new TimeSeries("序列值");

        int len = items.getLength();
        for (int i = 0; i < len; i++) {
            DataItem item = items.getElementAt(i);
            Date date = item.getTime();
            double val = Double.parseDouble(item.getData());
            ts.addOrUpdate(items.getTimePeriodOfElement(i), val);
        }
        tsc.addSeries(ts);
        chart.getXYPlot().setDataset(tsc);
    }

    public static XYDataset createNormalDataset(DataItems normal) {
        //获取正常数据的长度、
        int length = normal.getLength();
        int time[] = new int[length];
        XYSeries xyseries = new XYSeries("");

        XYSeriesCollection xyseriescollection = new XYSeriesCollection();

        //为数据集添加数据

        for (int i = 0; i < length; i++) {
            DataItem temp = new DataItem();
            temp = normal.getElementAt(i);
            xyseries.add((double) temp.getTime().getTime(), Double.parseDouble(temp.getData())); // 对应的横轴

        }
        xyseriescollection.addSeries(xyseries);
        return xyseriescollection;
    }

    //对异常点进行初始化
    public static XYDataset createAbnormalDataset(DataItems abnor) {  // 统计异常点的长度
        int length = abnor.getLength();
        XYSeries xyseries = new XYSeries("");

        XYSeriesCollection xyseriescollection = new XYSeriesCollection();


        //添加数据值

        for (int i = 0; i < length; i++) {

            DataItem temp = new DataItem();
            temp = abnor.getElementAt(i);
            xyseries.add((double) temp.getTime().getTime(), Double.parseDouble(temp.getData()));
            xyseries.add((double) temp.getTime().getTime(), Double.parseDouble(temp.getData()));

        }
        xyseriescollection.addSeries(xyseries);
        return xyseriescollection;
    }

    public static XYDataset createmodeDataset(DataItems normal,String name) {
        //获取正常数据的长度、
        int length = normal.getLength();
        int time[] = new int[length];
        XYSeries xyseries = new XYSeries(name);

        XYSeriesCollection xyseriescollection = new XYSeriesCollection();

        //为数据集添加数据

        for (int i = 0; i < length; i++) {
            DataItem temp = new DataItem();
            temp = normal.getElementAt(i);
            xyseries.add((double) temp.getTime().getTime(), Double.parseDouble(temp.getData())); // 对应的横轴

        }
        xyseriescollection.addSeries(xyseries);
        return xyseriescollection;
    }

    public static JFreeChart createChart(ArrayList<DataItems> _nor_model,ArrayList<String> name, DataItems nor, DataItems abnor,int period,int firstperiod) {
        XYDataset xydataset = createNormalDataset(nor);
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("周期值: "+period+"    "+"最可能子周期值 : "+firstperiod, "时间", "值", xydataset);
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
        numberaxis.setAutoRangeIncludesZero(false);
        java.awt.geom.Ellipse2D.Double double1 = new java.awt.geom.Ellipse2D.Double(-4D, -4D, 6D, 6D);
        //设置异常点提示红点大小
        /*XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
        //设置不可看到点。
        xylineandshaperenderer.setSeriesLinesVisible(0, true);
        xylineandshaperenderer.setBaseShapesVisible(false);
        xylineandshaperenderer.setSeriesShape(0, double1);
        xylineandshaperenderer.setSeriesPaint(0, Color.white);
        xylineandshaperenderer.setSeriesFillPaint(0, Color.white);
        xylineandshaperenderer.setSeriesOutlinePaint(0, Color.white);
        xylineandshaperenderer.setSeriesStroke(0, new BasicStroke(0.5F));
        //设置显示数据点
//        xylineandshaperenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
//        xylineandshaperenderer.setBaseItemLabelsVisible(true);

        XYDataset xydataset1 = createAbnormalDataset(abnor);
        XYLineAndShapeRenderer xylineandshaperenderer1 = new XYLineAndShapeRenderer();
        xyplot.setDataset(1, xydataset1);
        xyplot.setRenderer(1, xylineandshaperenderer1);
        //设置不可见到点。
        xylineandshaperenderer1.setBaseShapesVisible(false);
        //设置可以看见线。
        xylineandshaperenderer1.setSeriesLinesVisible(0, true);
        xylineandshaperenderer1.setSeriesShape(0, double1);
        //设置线和点的颜色。
        xylineandshaperenderer1.setSeriesPaint(0, Color.white);
        xylineandshaperenderer1.setSeriesFillPaint(0, Color.white);
        xylineandshaperenderer1.setSeriesOutlinePaint(0, Color.white);
        xylineandshaperenderer1.setUseFillPaint(true);
        xylineandshaperenderer1.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
        xylineandshaperenderer1.setSeriesStroke(0, new BasicStroke(0.5F));*/

        //xylineandshaperenderer1.setBaseItemLabelsVisible(true);
        for (int i = 0; i < _nor_model.size(); i++) {
            XYDataset xydataset2 = createmodeDataset(_nor_model.get(i),name.get(i));
            XYLineAndShapeRenderer xylineandshaperenderer2 = new XYLineAndShapeRenderer();
            xyplot.setDataset( i, xydataset2);
            xyplot.setRenderer(i , xylineandshaperenderer2);
            //设置不可见到点。
            xylineandshaperenderer2.setBaseShapesVisible(false);
            //设置可以看见线。
            xylineandshaperenderer2.setSeriesLinesVisible(0, true);
            xylineandshaperenderer2.setSeriesShape(0, double1);
            //设置线和点的颜色。
            Color lineColor = getColor(xydataset2);
            xylineandshaperenderer2.setSeriesPaint(0, lineColor);
            xylineandshaperenderer2.setSeriesFillPaint(0, lineColor);
            xylineandshaperenderer2.setSeriesOutlinePaint(0, lineColor);
            xylineandshaperenderer2.setUseFillPaint(true);
            xylineandshaperenderer2.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
            xylineandshaperenderer2.setSeriesStroke(0, new BasicStroke(1.5F));

        }

//        jfreechart.getLegend().setVisible(false);
        return jfreechart;
    }

    /**
     * 根据item对象获取其对应的颜色; 若有必要就新建一种颜色赋予该item.
     * @param item item对象
     * @return item对应的颜色
     */
    private static Color getColor (Object item) {
        if (colorTable.containsKey(item)) return colorTable.get(item);
        //Color color = new Color(Color.HSBtoRGB(random.nextFloat() * 19, 1.0f, 1.0f));   // 第一个参数为0-1的浮点数*360，若超过1则取小数位，乘上19扩大颜色变动范围
        int remainder = colorCount%3;
        Color color = remainder == 0? Color.BLUE :(remainder == 1? Color.GREEN: Color.ORANGE);
        colorCount++;
        colorTable.put(item, color);
        return color;
    }
}


