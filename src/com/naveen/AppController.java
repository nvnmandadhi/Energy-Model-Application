package com.naveen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AppController {

	@FXML
	Label total_energy_result, downstream_energy_result, upstream_energy_result, error;
	@FXML
	TextField temperature;
	@FXML
	TextField moisture;
	@FXML
	TextField natural_gas;
	@FXML
	TextField diesel;
	@FXML
	TextField light_fuel_gas;
	@FXML
	TextField lpg;

	@FXML
	PieChart chart;

	@FXML
	NumberAxis yAxis;

	@FXML
	CategoryAxis xAxis;

	@FXML
	BarChart<String, Double> bar_chart;

	@FXML
	Button calculate_total_energy, generate_chart;

	XYChart.Series<String, Double> series1 = new XYChart.Series<>();
	XYChart.Series<String, Double> series2 = new XYChart.Series<>();
	XYChart.Series<String, Double> series3 = new XYChart.Series<>();
	XYChart.Series<String, Double> series4 = new XYChart.Series<>();

	private double downstream_energy;
	private double natural_gas_percent;
	private double diesel_percent;
	private double light_fuel_gas_percent;
	private double lpg_percent;
	private double upstream_energy;
	private double total_energy;

	public void doStuff() {

		calculate_total_energy.setOnAction(event -> {
			downstream_energy = (26352.77 * Double.parseDouble(moisture.getText()))
					+ (537.98 * Double.parseDouble(temperature.getText())) - 32598.9;
			natural_gas_percent = (Double.parseDouble(natural_gas.getText()) * 0.111868434193638) / 100;
			diesel_percent = (Double.parseDouble(diesel.getText()) * 0.16015875221474) / 100;
			light_fuel_gas_percent = (Double.parseDouble(light_fuel_gas.getText()) * 0.155217414842077) / 100;
			lpg_percent = (Double.parseDouble(lpg.getText()) * 0.0890627449471606) / 100;

			upstream_energy = downstream_energy
					* (natural_gas_percent + diesel_percent + light_fuel_gas_percent + lpg_percent);

			total_energy = downstream_energy + upstream_energy;

			total_energy_result.setText(Math.round(total_energy) + " BTU");
			downstream_energy_result.setText(Math.round(downstream_energy) + " BTU");
			upstream_energy_result.setText(Math.round(upstream_energy) + " BTU");

			chart.setData(getData());
			chart.setClockwise(false);
			chart.setLegendSide(Side.BOTTOM);
			chart.setLabelsVisible(true);
			chart.setLabelLineLength(10);

			xAxis.setLabel("Fuel Type");
			yAxis.setLabel("%");

			series1.getData()
					.add(new XYChart.Data<String, Double>("Natural", Double.parseDouble(natural_gas.getText())));
			series2.getData().add(new XYChart.Data<String, Double>("Diesel", Double.parseDouble(diesel.getText())));
			series3.getData()
					.add(new XYChart.Data<String, Double>("Light Fuel", Double.parseDouble(light_fuel_gas.getText())));
			series4.getData().add(new XYChart.Data<String, Double>("LPG", Double.parseDouble(lpg.getText())));

			bar_chart.getData().add(series1);
			bar_chart.getData().add(series2);
			bar_chart.getData().add(series3);
			bar_chart.getData().add(series4);

			bar_chart.setLegendVisible(false);

		});
	}

	private ObservableList<Data> getData() {
		ObservableList<Data> observableList = FXCollections.observableArrayList();
		observableList.addAll(new PieChart.Data("DownStream", downstream_energy),
				new PieChart.Data("UpStream", upstream_energy));
		return observableList;
	}

}
