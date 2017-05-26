# hive-udtf

Create UDTF for Hive on Hadoop, I just refer the blog post: [RECURSION IN HIVE – PART 1](https://www.pythian.com/blog/recursion-in-hive/)

## Build & Package

```
$ sbt compile
$ sbt package
```

## Testing

* Add jar for your Hadoop ENV

```sh
hive> add jar /path/to/hive-udtf.jar;
```

* Prepare Hive tables, like following:

```sql

CREATE TABLE t_product(
  product string); 

CREATE TABLE t_state(
  STATE string, 
  next_state string); 

CREATE TABLE t_big_data(
  product string,
  STATE string, DATA string); 
  
-- Create function with this class name
CREATE FUNCTION expand_tree AS 'jp.gr.java_conf.hangedman.ExpandTree2UDTF'; 

-- Test
INSERT ovewrite TABLE t_expand_state(state1, state2, lvl) 
SELECT
  expand_tree(STATE, next_state) 
FROM
  t_state; 
```
