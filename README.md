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
```
