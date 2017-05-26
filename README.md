# hive-udtf

Create UDTF for Hive on Hadoop, I just refer the blog post: [RECURSION IN HIVE – PART 1](https://www.pythian.com/blog/recursion-in-hive/)

## Build & Package

```
$ sbt compile
$ sbt assembly
```

## Testing

* Upload & add jar for your Hadoop ENV

```sh
hive> add jar /path/to/hive-udtf.jar;
Added [/home/hadoop/hive-udtf.jar] to class path
Added resources: [/home/hadoop/hive-udtf.jar]
```

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

-- Create function with this class name
CREATE FUNCTION expand_tree AS 'jp.gr.java_conf.hangedman.ExpandTree2UDTF';

-- Test
SELECT
  expand_tree(state, next_state)
FROM
  t_state;
```

* Prepare test data with CSV files, like following:

```
$ cat t_product.csv
Lepirudin
Cetuximab
Dornase

$ cat t_state.csv
1,2
2,3
3,4
4,4
5,5
6,6
7,7
8,8
9,9

$ hadoop fs -put t_product.csv
$ hadoop fs -put t_state.csv

hive> LOAD DATA INPATH 't_product.csv' OVERWRITE INTO TABLE t_product;
hive> LOAD DATA INPATH 't_state.csv' OVERWRITE INTO TABLE t_state;

hive> SELECT
  expand_tree(state, next_state)
FROM
  t_state;

```