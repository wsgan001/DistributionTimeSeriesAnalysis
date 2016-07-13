package cn.InstFS.wkr.NetworkMining.ResultDisplay.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

import cn.InstFS.wkr.NetworkMining.DataInputs.DataItems;
import cn.InstFS.wkr.NetworkMining.DataInputs.DataInputUtils;
import cn.InstFS.wkr.NetworkMining.Miner.*;
//import cn.InstFS.wkr.NetworkMining.Miner.NetworkMinerTSA;
import cn.InstFS.wkr.NetworkMining.TaskConfigure.TaskElement;

import java.awt.GridBagLayout;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.InstFS.wkr.NetworkMining.UIs.Utils.UtilsUI;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import ec.nbdemetra.ws.WorkspaceItem.InnerComparator;

import java.awt.GridLayout;
import java.beans.PropertyVetoException;
import java.util.Timer;

public class PanelShowResultsFM extends JPanel implements IPanelShowResults {
    private Date now;    // 指示现在时间，但有可能不是真正的现在时间
    private INetworkMiner miner;
    private Timer timer;
    JDesktopPane desktopPane;
    int count = 0;
    ChartPanelShowTs chart1;
    ChartPanelShowTs chart3;
    ChartPanelShowAb chart2;
    ChartPanelShowPre chart4;

    public PanelShowResultsFM(TaskElement task) {
        this();
        InitMiner(task);


    }

    private void InitMiner(TaskElement task) {
        this.miner = NetworkMinerFactory.getInstance().createMiner(task);
        miner.setResultsDisplayer(this);
    }

    /**
     * Create the panel.
     */
    private PanelShowResultsFM() {
        InitChartScheme();
        setLayout(new GridLayout(0, 1, 0, 0));
//		chart1 = new ChartPanelShowTs("原始值", "时间", "值", null);
//		chart2 = new ChartPanelShowTs("预测值", "时间", "", null);
//
//		add(chart1);
//		add(chart2);
        chart1 = new ChartPanelShowTs("原始值", "时间", "值", null);
//        chart2 = new ChartPanelShowAb("预测值", "时间", "", null);
        chart3= new ChartPanelShowTs("预测值", "时间", "", null);
        add(chart1);
        add(chart3);

    }

    private void InitChartScheme() {
        StandardChartTheme sct = new StandardChartTheme("");
        sct.setExtraLargeFont(new Font("宋体", 0, 20));
        sct.setLargeFont(new Font("宋体", 0, 15));
        sct.setRegularFont(new Font("宋体", 0, 12));
        sct.setSmallFont(new Font("宋体", 0, 8));
        ChartFactory.setChartTheme(sct);
    }

    @Override
    public boolean start() {
        return miner.start();
    }

    @Override
    public boolean stop() {
        return miner.stop();
    }


    @Override
    public void setData(DataItems data) {

    }

    @Override
    public TaskElement getTask() {
        return miner.getTask();
    }

    @Override
    public INetworkMiner getMiner() {
        return miner;
    }

    @Override
    public void displayMinerResults() {
        if (this.isVisible()) {
//			displayOrigTimeSeries();
        }
        displayMinerResults(miner.getResults());

    }

    private void displayOrigTimeSeries() {
        DataInputUtils diu = new DataInputUtils(miner.getTask());
        DataItems di;
        if (now == null)
            now = new Date(-1000, 1, 1);
        di = diu.readInputAfter(now);

        if (di != null) {
            List<Date> timeStr = di.getTime();
            List<String> dataStr = di.getData();

            int numData = di.getData().size();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            XYDataset dataset = chart1.getChart().getXYPlot().getDataset();
            if (dataset == null) {
                dataset = new TimeSeriesCollection();
                chart1.getChart().getXYPlot().setDataset(dataset);
            }
            TimeSeriesCollection tsc = (TimeSeriesCollection) dataset;
            TimeSeries ts = null;
            if (tsc.getSeriesCount() == 0) {
                ts = new TimeSeries(miner.getTask().getMiningObject());
                tsc.addSeries(ts);
            } else
                ts = tsc.getSeries(0);
            int numNewData = 0;
            for (int i = 0; i < numData; i++) {
                Date time = null;
                time = timeStr.get(i);
                if (now != null && time.after(now)) {
                    numNewData++;
                    ts.addOrUpdate(new Second(time), Double.parseDouble(dataStr.get(i)));
                }
            }
            if (ts.getItemCount() > 0) {
                now = ts.getTimePeriod(ts.getItemCount() - 1).getEnd();
            }
            System.out.println("共显示" + ts.getItemCount() + "个数据点！(新增" + numNewData + "个)。");
//			chart1.getXYPlot().setDataset(tsc);
        }
    }

    @Override
    public void displayMinerResults(MinerResults rslts) {
        if (rslts == null || rslts.getRetFM() == null ||
                !rslts.getMiner().getClass().equals(NetworkMinerFM.class))
            return;

        if (count == 0) {


//            DataItems  = rslts.getRetOM().getOutlies();
			DataItems predictItems = rslts.getRetFM().getPredictItems();
            DataItems oriItems = rslts.getInputData();
            chart1.displayDataItems(oriItems);

//			remove(chart1);
//			remove(chart2);

//			repaint();
//			validate();
//            if(outliesItems!=null)
//            {
////			HashMap<String,ArrayList<DataItems>> nor_data=new HashMap<>();
////			JFreeChart jf = ChartPanelShowFI.createChart(nor_data,oriItems);
////			ChartPanel chartpanel = new ChartPanel(jf);
////			XYDataset nor=ChartPanelShowAb.createNormalDataset(oriItems);
////			XYDataset abnor=ChartPanelShowAb.createAbnormalDataset(outliesItems);
//                JFreeChart jf=ChartPanelShowAbd.createChart(outliesItems);
//                ChartPanel chartpanel = new ChartPanel(jf);
//                remove(chart1);
//                remove(chart3);
//                add(chartpanel);
//                add(chart1);
//
//
////			add(chart1);
////			add(chartpanel);
//                count++;
//            }
			if(predictItems!=null)
			{
//				XYDataset nor=ChartPanelShowPre.createNormalDataset(oriItems);
//				XYDataset abnor=ChartPanelShowPre.createAbnormalDataset();
				JFreeChart jf=ChartPanelShowPre.createChart(oriItems,predictItems);
				ChartPanel chartpanel = new ChartPanel(jf);
				chart1.displayDataItems(oriItems);
				remove(chart1);
				remove(chart3);
				add(chart1);
				add(chartpanel);
				count++;

			}
//			异常度检测结果
//			if(XXXX!=null)
//			{
//				JFreeChart jf=ChartPanelShowAbd.createChart(oriItems,XXXX);
//				ChartPanel chartpanel = new ChartPanel(jf);
//				chart1.displayDataItems(oriItems);
//				remove(chart1);
//				remove(chart3);
//				add(chart1);
//				add(chartpanel);
//			count++;
//			}
            repaint();
            revalidate();

        }

    }

    class ShowTSA_TimerTask extends TimerTask {
        @Override
        public void run() {

        }
    }
}