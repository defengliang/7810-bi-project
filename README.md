## Introduction

7810 BI group project.

## How to run

### Prerequisites 

Please make sure JRE or JDK 17 or above is installed.

To check this, open your cmd / terminal, run command:

```
java -version
```
If you see output as this, that means, you have install JRE/JDK 17.

```
openjdk version "17.0.9" 2023-10-17
OpenJDK Runtime Environment Homebrew (build 17.0.9+0)
OpenJDK 64-Bit Server VM Homebrew (build 17.0.9+0, mixed mode, sharing)
```

If you don't know how to install JRE / JDK 17, please ask ChatGPT or search in the internet.

### Clone repository

```
git clone https://github.com/defengliang/7810-bi-project.git
```

Enter the project.
```
cd 7810-bi-project
```

### Download CSV files.

Download the fraudTrain.csv and fraudTest.csv files from the URL below and put them in the input folder.

https://www.kaggle.com/datasets/kartik2112/fraud-detection

### Run 

```
java -jar 7810.jar
```

### Verify

Check the output folder to see if fraudTrain_balanced.arff and fraudTest.arff are generated.

## Weka

Open the fraudTrain_balanced.arff file in Weka.

## How each field is transferred when output the arff file.

column [0] - no - ignored, not output

column [1] - trans_date_trans_time - to epoch time output

column [2] - cc_num - to hashCode output

column [3] - merchant - to hashCode output

column [4] - category - to hashCode output

column [5] - amt - raw data output

column [6] - first - to hashCode output

column [7] - last - to hashCode output

column [8] - gender - raw data output

column [9] - street - to hashCode output

column [10] - city - to hashCode output

column [11] - state - special handling output

(ascii of the first letter) * 26  + (ascii of the second letter)

column [12] - zip - raw data output

column [13] - lat - raw data output

column [14] - long - raw data output

column [15] - city_pop - raw data output

column [16] - job - to hashCode output

column [17] - dob - transfer to age output

turn to the age in year up to today.

column [18] - trans_num - to hashCode output

column [19] - unix_time - raw data output

column [20] - merch_lat - raw data output

column [21] - merch_long - raw data output

column [22] - is_fault - raw data output