package ua.edu.ucu.tempseries;

import lombok.Getter;

@Getter
public class TemperatureSeriesAnalysis {
    private double[] temperatureSeries;
    private int size;
    private int capacity;

    static final int MIN_TEMP = -273;

    public TemperatureSeriesAnalysis() {
        this.temperatureSeries  = new double[]{};
        this.size = 0;
        this.capacity = 1;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        this.temperatureSeries = temperatureSeries.clone();
        size = temperatureSeries.length;
        capacity = temperatureSeries.length;
    }

    public double average() {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException("List of temperatures is empty");
        }
        double sum = 0;
        for (double temp : temperatureSeries) {
            sum += temp;
        }
        return sum / temperatureSeries.length;
    }

    public double deviation() {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException("List of temperatures is empty");
        }
        double average = average();
        double sumOfPows = 0;
        for (double temp : temperatureSeries) {
            sumOfPows += Math.pow((temp-average), 2);
        }

        if (sumOfPows == 0.0) {
            return sumOfPows;
        }

        return Math.sqrt(sumOfPows/(temperatureSeries.length-1));
    }

    public double min() {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException("List of temperatures is empty");
        }

        double minTemp = temperatureSeries[0];
        for (double temp : temperatureSeries) {
            if (temp < minTemp) {
                minTemp = temp;
            }
        }
        return minTemp;
    }

    public double max() {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException("List of temperatures is empty");
        }

        double minTemp = temperatureSeries[0];
        for (double temp : temperatureSeries) {
            if (temp > minTemp) {
                minTemp = temp;
            }
        }
        return minTemp;
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException("List of temperatures is empty");
        }

        double closestToValue = temperatureSeries[0];
        for (double temp : temperatureSeries) {
            if (Math.abs(temp - tempValue) < Math.abs(closestToValue - tempValue)) {
                closestToValue = temp;
            } else if (Math.abs(temp - tempValue) == Math.abs(closestToValue - tempValue)) {
                if (temp > 0) {
                    closestToValue = temp;
                }
            }
        }
        return closestToValue;
    }

    public double[] findTempsLessThen(double tempValue) {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException("List of temperatures is empty");
        }
        int sizeLessArr = 0;
        for (double temp : temperatureSeries) {
            if (temp < tempValue) {
                sizeLessArr += 1;
            }
        }

        double[] tempsLessThen = new double[sizeLessArr];
        int idx = 0;

        for (double temp : temperatureSeries) {
            if (temp < tempValue) {
                tempsLessThen[idx++] = temp;
            }
        }
        return tempsLessThen;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException("List of temperatures is empty");
        }
        int sizeGreaterArr = 0;
        for (double temp : temperatureSeries) {
            if (temp > tempValue) {
                sizeGreaterArr += 1;
            }
        }

        double[] tempsGreaterThen = new double[sizeGreaterArr];
        int idx = 0;

        for (double temp : temperatureSeries) {
            if (temp > tempValue) {
                tempsGreaterThen[idx++] = temp;
            }
        }
        return tempsGreaterThen;
    }

    public TempSummaryStatistics summaryStatistics() {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException("List of temperatures is empty");
        }
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        int sizeOfAdd = 0;

        for (double temp : temps) {
            if (temp < MIN_TEMP) {
                return size;
            }
            sizeOfAdd++;
        }

        if (size + sizeOfAdd > capacity) {
            while (size + sizeOfAdd > capacity) {
                capacity = 2 * capacity;
            }

            double[] newTempList = new double[capacity];
            for (int i = 0; i < size; i++) {
                newTempList[i] = temperatureSeries[i];
            }
            temperatureSeries = newTempList;
        }

        for (double temp : temps) {
            temperatureSeries[size++] = temp;
        }
        return size;
    }
}
