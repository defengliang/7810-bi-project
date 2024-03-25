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

Check the output folder to see if fraudTrain.arff and fraudTest.arff are generated.

## Weka

Open the fraudTrain.arff file in Weka.