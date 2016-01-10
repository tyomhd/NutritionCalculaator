import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;


class DataPieChart extends PieChart {

    public DataPieChart(String s1, double d1, String s2, double d2, String s3, double d3 ) {
        ObservableList<Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data(s1, d1),
                new PieChart.Data(s2, d2),
                new PieChart.Data(s3, d3)
        );
        setData(pieChartData);
    }

}// END
