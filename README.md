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
  product    STRING);

CREATE TABLE t_state(
  state      STRING,
  next_state STRING);

CREATE TABLE t_big_data(
  product    STRING,
  state      STRING,
  data       STRING);

-- Create function with this class name
CREATE FUNCTION expand_tree AS 'jp.gr.java_conf.hangedman.ExpandTree2UDTF';

-- Test
SELECT
  expand_tree(state, next_state)
FROM
  t_state;
```
