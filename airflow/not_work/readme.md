# Why it doesn't work:
I really don't know how to send configs from file to SparkSubmitOperator.
I tried:
1. Save it in variable
2. Send it in conf via xcom like a string and a dictionary
3. Write it in format --conf key=value in "application" before app jar (in the hope that since it is launched via cmd, it will simply substitute them in the spark-submit line)
I have no ideas anymore...
