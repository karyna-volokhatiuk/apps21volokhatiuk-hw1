package ua.edu.ucu.tempseries;

import lombok.Getter;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.abs;

@Getter
public class TemperatureSeriesAnalysis {
    private double[] temperatureSeries;
    private int size = 0;
    private int capacity = 1;

    public TemperatureSeriesAnalysis() {
        double[] temperatureSeries  = {};
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        this.temperatureSeries = temperatureSeries.clone();
        size = temperatureSeries.length;
        capacity = temperatureSeries.length;
    }

    public double average(){
        if (temperatureSeries.length == 0){
            throw new IllegalArgumentException("List of temperatures is empty.");
        }
        double sum = 0;
        for (double temp : temperatureSeries){
            sum += temp;
        }
        return sum / temperatureSeries.length;
    }

    public double deviation() {
        if (temperatureSeries.length == 0){
            throw new IllegalArgumentException("List of temperatures is empty.");
        }
        double average = average();
        double sum_of_pows = 0;
        for (double temp : temperatureSeries){
            sum_of_pows += pow((temp-average), 2);
        }

        if (sum_of_pows == 0.0){
            return sum_of_pows;
        }

        return sqrt(sum_of_pows/(temperatureSeries.length-1));
    }

    public double min() {
        if (temperatureSeries.length == 0){
            throw new IllegalArgumentException("List of temperatures is empty.");
        }

        double min_temp = temperatureSeries[0];
        for (double temp : temperatureSeries){
            if (temp < min_temp){
                min_temp = temp;
            }
        }
        return min_temp;
    }

    public double max() {
        if (temperatureSeries.length == 0){
            throw new IllegalArgumentException("List of temperatures is empty.");
        }

        double min_temp = temperatureSeries[0];
        for (double temp : temperatureSeries){
            if (temp > min_temp){
                min_temp = temp;
            }
        }
        return min_temp;
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        if (temperatureSeries.length == 0){
            throw new IllegalArgumentException("List of temperatures is empty.");
        }

        double closest_to_value = temperatureSeries[0];
        for (double temp : temperatureSeries){
            if (abs(temp - tempValue) < abs(closest_to_value - tempValue)){
                closest_to_value = temp;
            } else if (abs(temp - tempValue) == abs(closest_to_value - tempValue)){
                if (temp > 0){
                    closest_to_value = temp;
                }
            }
        }
        return closest_to_value;
    }

    public double[] findTempsLessThen(double tempValue) {
        if (temperatureSeries.length == 0){
            throw new IllegalArgumentException("List of temperatures is empty.");
        }
        int size = 0;
        for (double temp : temperatureSeries) {
            if (temp < tempValue) {
                size += 1;
            }
        }

        double[] temps_less_then = new double[size];
        int idx = 0;

        for (double temp : temperatureSeries) {
            if (temp < tempValue) {
                temps_less_then[idx++] = temp;
            }
        }
        return temps_less_then;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        if (temperatureSeries.length == 0){
            throw new IllegalArgumentException("List of temperatures is empty.");
        }
        int size = 0;
        for (double temp : temperatureSeries) {
            if (temp > tempValue) {
                size += 1;
            }
        }

        double[] temps_greater_then = new double[size];
        int idx = 0;

        for (double temp : temperatureSeries) {
            if (temp > tempValue) {
                temps_greater_then[idx++] = temp;
            }
        }
        return temps_greater_then;
    }

    public TempSummaryStatistics summaryStatistics() {
        if (temperatureSeries.length == 0){
            throw new IllegalArgumentException("List of temperatures is empty.");
        }
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        int size_of_add = 0;

        for (double temp : temps) {
            if (temp < -273) {
                return size;
            }
            size_of_add++;
        }

        if (size + size_of_add > capacity){
            while (size + size_of_add > capacity){
                capacity = 2 * capacity;
            }

            double[] new_temp_list = new double[capacity];
            for (int i = 0; i < size; i++){
                new_temp_list[i] = temperatureSeries[i];
            }
            temperatureSeries = new_temp_list;
        }

        for (double temp : temps){
            temperatureSeries[size++] = temp;
        }
        return size;
    }
}
