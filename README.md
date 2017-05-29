# hive-udtf

Create UDTF for Hive on Hadoop, I just refer the blog post: [RECURSION IN HIVE – PART 1](https://www.pythian.com/blog/recursion-in-hive/)

## Build & Package

```
$ sbt compile
$ sbt assembly
```

## Testing

* Please upload & add jar for your Hadoop ENV

* Prepare Hive tables, like following:

```sql
CREATE TABLE t_product(
  product    STRING)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n';

CREATE TABLE t_state(
  state      STRING,
  next_state STRING)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n';

CREATE TABLE t_big_data(
  product    STRING,
  state      STRING,
  data       STRING)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n';
```

* Prepare test data with CSV files, like following:

```
$ cat t_product.csv
Lepirudin
Cetuximab
Dornase

$ cat t_state.csv
S1,
S2,S1
S3,S1
S4,S2

$ hadoop fs -put t_product.csv
$ hadoop fs -put t_state.csv

hive> LOAD DATA INPATH 't_product.csv' OVERWRITE INTO TABLE t_product;
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

hive> SELECT
  expand_tree(state, next_state)
FROM
  t_state;

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

-- You can limit records up to your use-case

```
