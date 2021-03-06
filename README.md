# hive-udtf [![Build Status](https://travis-ci.org/Hiroyuki-Nagata/hive-udtf.svg?branch=master)](https://travis-ci.org/Hiroyuki-Nagata/hive-udtf)

Create UDTF for Hive on Hadoop, I just refer the blog post: [RECURSION IN HIVE – PART 1](https://www.pythian.com/blog/recursion-in-hive/)

## Build & Package

```
$ sbt compile
$ sbt assembly
```

## Known Issues

* This user defined function may not work with Hive under 1.3.0
  * [HIVE-11892](https://issues.apache.org/jira/browse/HIVE-11892) - UDTF run in local fetch task does not return rows forwarded during GenericUDTF.close()

Please use Hive 1.3.0 or later...

## Testing

* Please upload & add jar for your Hadoop ENV

* Prepare Hive tables, like following:

```sql
CREATE TABLE t_state(
  state      STRING,
  next_state STRING)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n';
```

* Prepare test data with CSV files, like following:

```
$ cat t_state.csv
S1,
S2,S1
S3,S1
S4,S2

$ hadoop fs -put t_state.csv

hive> LOAD DATA INPATH 't_state.csv' OVERWRITE INTO TABLE t_state;

hive> SELECT * FROM t_state;
OK
S1
S2      S1
S3      S1
S4      S2

hive> add jar /path/to/hive-udtf.jar;
Added [/home/hadoop/hive-udtf.jar] to class path
Added resources: [/home/hadoop/hive-udtf.jar]

-- Drop function if it exists
hive> DROP FUNCTION expand_tree;

-- Create function with this class name
hive> CREATE FUNCTION expand_tree AS 'jp.gr.java_conf.hangedman.ExpandTree2UDTF';

-- Hive will return following records
hive> SELECT expand_tree(state, next_state) FROM t_state;
OK
S4      S4      0
S4      S2      1
S4      S1      2
S1      S1      0
S3      S3      0
S3      S1      1
S2      S2      0
S2      S1      1

-- In case of INSERT INTO another TABLE
hive> INSERT INTO TABLE xxx SELECT expand_tree(state, next_state) AS (x,y,z) FROM t_state;

-- You can limit records up to your use-case

```
